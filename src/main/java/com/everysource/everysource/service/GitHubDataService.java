package com.everysource.everysource.service;

import com.everysource.everysource.domain.api.Issue;
import com.everysource.everysource.domain.api.Project;
import com.everysource.everysource.dto.ProjectReadmeDetailDTO;
import com.everysource.everysource.repository.IssueRepository;
import com.everysource.everysource.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GitHubDataService {
    private final IssueRepository issueRepository;
    private final IssueService issueService;
    private final ProjectRepository projectRepository;
    private final RestTemplate restTemplate;

    @Value("${github.api.token}")
    private String token;

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }



    @Transactional
    public void fetchIssues(String owner, String repo) {
        try {
            log.info("{}/{}에 대한 이슈를 가져오는 중입니다.", owner, repo);
            String issuesUrl = "https://api.github.com/repos/" + owner + "/" + repo + "/issues";
            ResponseEntity<Issue[]> response = restTemplate.exchange(issuesUrl, HttpMethod.GET, new HttpEntity<>(createHeaders()), Issue[].class);
            Arrays.stream(response.getBody()).forEach(issue -> {
                issue.setOwner(owner);
                issue.setRepo(repo);
                issueService.saveIssue(issue);
            });
            log.debug("Transaction completed for fetchIssues");
        } catch (Exception e) {
            log.error("{}/{} 이슈 가져오기 중 에러 발생", owner, repo, e);
            throw e;
        }
    }

    @Transactional
    public ProjectReadmeDetailDTO fetchReadme(String owner, String repo) {
        try {
            log.info("{}/{}에 대한 README를 가져오는 중입니다.", owner, repo);
            String readmeUrl = "https://api.github.com/repos/" + owner + "/" + repo + "/readme";
            String readmeContent = fetchReadmeContent(readmeUrl);

            Optional<Project> existingProjectOptional = projectRepository.findByOwnerAndRepo(owner, repo);
            if (existingProjectOptional.isPresent()) {
                Project existingProject = existingProjectOptional.get();/** Optional 의 get 메서드는 값이 있으면 객체 추출 */
                existingProject.setContent(readmeContent);
                projectRepository.save(existingProject);
                log.info("{}/{} 프로젝트 내용이 업데이트되었습니다.", owner, repo);
                return new ProjectReadmeDetailDTO(existingProjectOptional);
            }else{
                Project project = new Project();
                project.setContent(readmeContent);
                project.setRepo(repo);
                project.setOwner(owner);
                projectRepository.save(project);
                log.info("{}/{} 프로젝트가 생성되었습니다.", owner, repo);
                return new ProjectReadmeDetailDTO(Optional.of(project));
            }
        } catch (Exception e) {
            log.error("{}/{} README 가져오기 실패", owner, repo, e);
            return null;
        }
    }

    public String fetchReadmeContent(String readmeUrl) throws Exception {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                readmeUrl, HttpMethod.GET, new HttpEntity<>(createHeaders()), new ParameterizedTypeReference<>() {}
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            String contentBase64 = (String) response.getBody().get("content");
            byte[] decodedBytes = Base64.getMimeDecoder().decode(contentBase64.replace("\n", "").trim());
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } else {
            throw new HttpClientErrorException(response.getStatusCode(), "GitHub API error: " + response.getStatusCode().toString());
        }
    }

}
