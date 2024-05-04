package com.everysource.everysource.service;

import com.everysource.everysource.domain.api.Issue;
import com.everysource.everysource.domain.api.IssueSearch;
import com.everysource.everysource.domain.api.Member;
import com.everysource.everysource.domain.api.MemberIssueActivity;
import com.everysource.everysource.dto.api.IssueDTO;
import com.everysource.everysource.dto.api.IssueListDTO;
import com.everysource.everysource.repository.api.IssueRepository;
import com.everysource.everysource.repository.api.IssueSearchRepository;
import com.everysource.everysource.repository.api.MemberIssueActivityRepository;
import com.everysource.everysource.repository.api.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final MemberIssueActivityRepository memberIssueActivityRepository;
    public List<IssueListDTO> findAllIssues() {
        List<Issue> issues = issueRepository.findAll();
        return issues.stream()
                .map(IssueListDTO::new)
                .collect(Collectors.toList());
    }
    public List<IssueListDTO> findIssuesByOwner(String owner) {
        List<IssueSearch> issues = issueSearchRepository.findByOwner(owner);
        return issues.stream()
                .map(issue -> new IssueListDTO(issue))
                .collect(Collectors.toList());
    }

    public List<IssueListDTO> findIssuesByRepo(String repo) {
        List<IssueSearch> issues = issueSearchRepository.findByRepo(repo);
        return issues.stream()
                .map(issue -> new IssueListDTO(issue))
                .collect(Collectors.toList());
    }

    /** 검색 순위 서비스 */
    public List<IssueListDTO> getTopSearchedIssues() {
        List<Issue> issues = issueRepository.findTop10ByOrderBySearchCountDesc();
        return issues.stream()
                .map(issue -> new IssueListDTO(issue))
                .collect(Collectors.toList());
    }

    public IssueDTO findIssuesDetail(Long memberId, Long issueId) {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if (!issue.isPresent()) {
            throw new IllegalArgumentException("Issue not found");
        }
        issueRepository.incrementSearchCount(issueId);
        if (memberId != null) {
            Optional<Member> member = memberRepository.findById(memberId);
            member.ifPresent(m -> updateMemberIssueActivity(m, issue.get()));
        }

        return new IssueDTO(issue);
    }

    private void updateMemberIssueActivity(Member member, Issue issue) {
        MemberIssueActivity activity = memberIssueActivityRepository.findByMemberIdAndIssueId(member.getId(), issue.getId())
                .orElseGet(() -> createAndSaveActivity(member, issue));
        incrementViewCount(activity);
    }

    private MemberIssueActivity createAndSaveActivity(Member member, Issue issue) {
        MemberIssueActivity activity = new MemberIssueActivity(member, issue, 0, LocalDateTime.now());
        return memberIssueActivityRepository.save(activity);
    }

    private void incrementViewCount(MemberIssueActivity activity) {
        activity.incrementViewCount();
        memberIssueActivityRepository.save(activity);
    }

    public List<IssueListDTO> searchByKeyword(String keyword) {
        List<IssueSearch> issues = issueSearchRepository.findByKeyword(keyword);
        return issues.stream()
                .map(issue -> new IssueListDTO(issue))
                .collect(Collectors.toList());
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
