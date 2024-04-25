package com.everysource.everysource.service;

import com.everysource.everysource.domain.Issue;
import com.everysource.everysource.domain.IssueSearch;
import com.everysource.everysource.domain.Project;
import com.everysource.everysource.dto.IssueDTO;
import com.everysource.everysource.dto.IssueListDTO;
import com.everysource.everysource.repository.IssueRepository;
import com.everysource.everysource.repository.IssueSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

    @Autowired
    private final IssueRepository issueRepository;
    @Autowired
    private final IssueSearchRepository issueSearchRepository;

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

    public List<IssueSearch> searchByKeyword(String keyword) {
        return issueSearchRepository.findByKeyword(keyword);
    }

    public Issue saveIssue(Issue issue) {
        issue = issueRepository.save(issue);
        IssueSearch issueSearch = convertToIssueSearch(issue);
        issueSearchRepository.save(issueSearch);
        return issue;
    }

    private IssueSearch convertToIssueSearch(Issue issue) {
        return IssueSearch.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .owner(issue.getOwner())
                .repo(issue.getRepo())
                .state(issue.getState())
                .since(issue.getSince())
                .body(issue.getBody())
                .build();
    }


}
