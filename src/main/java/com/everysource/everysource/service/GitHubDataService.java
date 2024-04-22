package com.everysource.everysource.service;

import com.everysource.everysource.domain.Issue;
import com.everysource.everysource.domain.Project;
import com.everysource.everysource.dto.ProjectReadmeDetailDTO;
import com.everysource.everysource.repository.IssueRepository;
import com.everysource.everysource.repository.ProjectReactiveRepository;
import com.everysource.everysource.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GitHubDataService {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final ProjectReactiveRepository projectReactiveRepository;

    private final WebClient webClient = WebClient.create();

    @Value("${github.api.token}")
    private String token;

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Transactional("transactionManager")
    public Mono<Project> findByRepo(String repo) {
        return Mono.justOrEmpty(projectRepository.getContentByRepo(repo));
    }

    @Transactional("jpaTransactionManager")
    public Flux<Issue> fetchIssues(String owner, String repo) {
        String issuesUrl = "https://api.github.com/repos/" + owner + "/" + repo + "/issues";
        return webClient.get()
                .uri(issuesUrl)
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToFlux(Issue.class)
                .doOnNext(issue -> {
                    issue.setOwner(owner);
                    issue.setRepo(repo);
                    issueRepository.save(issue);
                    log.info("Issue saved and sent to internal processing (if necessary)");
                })
                .doOnError(error -> log.error("Failed to fetch issues for {}/{}", owner, repo, error));
    }

    @Transactional("transactionManager")
    public Mono<ProjectReadmeDetailDTO> fetchReadme(String owner, String repo) {
        String readmeUrl = "https://api.github.com/repos/" + owner + "/" + repo + "/readme";
        return fetchReadmeContent(readmeUrl)
                .flatMap(readmeContent ->
                        projectReactiveRepository.findByRepo(repo)
                                .map(project -> {
                                    project.setContent(readmeContent);
                                    project.setOwner(owner);
                                    return project;
                                })
                                .switchIfEmpty(Mono.defer(() -> {
                                    Project newProject = new Project();
                                    newProject.setRepo(repo);
                                    newProject.setOwner(owner);
                                    newProject.setContent(readmeContent);
                                    return Mono.just(newProject);
                                }))
                                .flatMap(projectReactiveRepository::save) // Ensure this save method is available in reactive repository
                                .map(ProjectReadmeDetailDTO::new)
                )
                .doOnError(error -> log.error("Failed to fetch README for {}/{}", owner, repo, error));
    }



    private Mono<String> fetchReadmeContent(String readmeUrl) {
        return webClient.get()
                .uri(readmeUrl)
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> new String(Base64.getMimeDecoder().decode(((String) response.get("content")).replace("\n", "").trim()), StandardCharsets.UTF_8))
                .doOnError(error -> log.error("Error fetching README content", error));
    }
}
