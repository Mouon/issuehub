package com.everysource.everysource.service;

import com.everysource.everysource.domain.Issue;
import com.everysource.everysource.domain.Project;
import com.everysource.everysource.dto.*;
import com.everysource.everysource.repository.IssueRepository;
import com.everysource.everysource.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.everysource.everysource.dto.ProjectReadmeDTO;

import java.util.List;
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
    private void updateIssuesForProject(String owner, String repo) {
        gitHubDataService.fetchIssues(owner, repo);
    }

    private void updateOrCreateIssue(IssueDTO issueDTO) {
        Optional<Issue> existingIssue = issueRepository.findById(issueDTO.getId());
        if (existingIssue.isPresent()) {
            Issue issueToUpdate = existingIssue.get();
            issueToUpdate.updateIssue(issueDTO.getStatus(), issueDTO.getUpdateDate(), issueDTO.getDetail());
            issueRepository.save(issueToUpdate);
        } else {
            Issue newIssue = convertDTOToEntity(issueDTO);
            issueRepository.save(newIssue);
        }
    }

    private Issue convertDTOToEntity(IssueDTO dto) {
        Issue issue = new Issue();
        issue.updateIssue(dto.getStatus(), dto.getUpdateDate(), dto.getDetail());  // Using the update method to set initial values
        return issue;
    }
}
