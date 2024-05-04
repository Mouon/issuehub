package com.everysource.everysource.service;

import com.everysource.everysource.domain.api.Issue;
import com.everysource.everysource.domain.api.IssueSearch;
import com.everysource.everysource.domain.api.Member;
import com.everysource.everysource.domain.api.MemberIssueActivity;
import com.everysource.everysource.dto.api.IssueListDTO;
import com.everysource.everysource.repository.api.IssueRepository;
import com.everysource.everysource.repository.api.IssueSearchRepository;
import com.everysource.everysource.repository.api.MemberIssueActivityRepository;
import com.everysource.everysource.dto.api.IssueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecommendService {

    @Autowired
    private MemberIssueActivityRepository memberIssueActivityRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private IssueSearchRepository issueSearchRepository;


    public List<IssueListDTO> recommendIssues(Long memberId) {
        List<Long> topIssueIds = memberIssueActivityRepository.findTopIssueIdsByMemberId(memberId);

        /** 유사 이슈 검색 */
        Pageable pageable = PageRequest.of(0, 20);

        List<IssueSearch> similarIssues = issueSearchRepository.findSimilarIssues(topIssueIds,topIssueIds,pageable);

        return similarIssues.stream()
                .map(issue -> new IssueListDTO(issue))
                .collect(Collectors.toList());
    }

}
