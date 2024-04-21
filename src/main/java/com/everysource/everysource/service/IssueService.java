package com.everysource.everysource.service;

import com.everysource.everysource.domain.Issue;
import com.everysource.everysource.domain.Project;
import com.everysource.everysource.dto.IssueDTO;
import com.everysource.everysource.dto.IssueListDTO;
import com.everysource.everysource.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public List<IssueListDTO> findAllIssues() {
        List<Issue> issues = issueRepository.findAll();
        return issues.stream()
                .map(IssueListDTO::new)
                .collect(Collectors.toList());
    }
    public List<IssueListDTO> findIssuesByOwner(String owner) {
        List<Issue> issues = issueRepository.findByOwner(owner);
        return issues.stream()
                .map(issue -> new IssueListDTO(issue))
                .collect(Collectors.toList());
    }

    public List<IssueListDTO> findIssuesByRepo(String repo) {
        List<Issue> issues = issueRepository.findByRepo(repo);
        return issues.stream()
                .map(issue -> new IssueListDTO(issue))
                .collect(Collectors.toList());
    }

    public IssueDTO findIssuesDetail(Long id) {
        Optional<Issue> issue = issueRepository.findById(id);
        return new IssueDTO(issue);
    }

}
