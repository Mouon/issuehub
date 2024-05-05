package com.everysource.everysource.service;

import com.everysource.everysource.domain.api.Project;
import com.everysource.everysource.dto.api.ProReadmeListDTO;
import com.everysource.everysource.dto.api.ProjectReadmeDetailDTO;
import com.everysource.everysource.repository.api.IssueRepository;
import com.everysource.everysource.repository.api.ProjectRepository;
import com.everysource.everysource.service.api.GitHubDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private GitHubDataService gitHubDataService;

    @Autowired
    private IssueRepository issueRepository;

    public ProReadmeListDTO findReadmeByRepo(String repo) {
        Project project = projectRepository.getContentByRepo(repo);
        return new ProReadmeListDTO(Optional.ofNullable(project));
    }


    /**
     * Read Through 동작
     * 캐싱 부분
     * */
    @Cacheable(value = "readme", key = "#repo")
    public ProjectReadmeDetailDTO findReadmeDetail(String repo) {
        Project project = projectRepository.getContentByRepo(repo);
        return new ProjectReadmeDetailDTO(Optional.ofNullable(project));
    }

}
