package com.everysource.everysource.service;

import com.everysource.everysource.domain.api.Project;
import com.everysource.everysource.dto.api.ProReadmeListDTO;
import com.everysource.everysource.dto.api.ProjectReadmeDetailDTO;
import com.everysource.everysource.repository.api.IssueRepository;
import com.everysource.everysource.repository.api.ProjectRepository;
import com.everysource.everysource.service.api.GitHubDataService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ProjectReadmeDetailDTO findReadmeDetail(String repo) {
        Project project = projectRepository.getContentByRepo(repo);
        return new ProjectReadmeDetailDTO(Optional.ofNullable(project));
    }

}
