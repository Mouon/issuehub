package com.everysource.everysource.service;

import com.everysource.everysource.domain.api.Issue;
import com.everysource.everysource.domain.api.IssueSearch;
import com.everysource.everysource.dto.api.IssueDTO;
import com.everysource.everysource.dto.api.IssueListDTO;
import com.everysource.everysource.repository.api.IssueRepository;
import com.everysource.everysource.repository.api.IssueSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /** 엘라스틱용 객체 생성 */
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
