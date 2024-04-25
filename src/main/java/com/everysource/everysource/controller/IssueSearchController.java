package com.everysource.everysource.controller;

import com.everysource.everysource.domain.IssueSearch;
import com.everysource.everysource.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueSearchController {

    private final IssueService issueService;

    @GetMapping("/search")
    public ResponseEntity<List<IssueSearch>> searchIssues(@RequestParam String keyword) {
        List<IssueSearch> issues = issueService.searchByKeyword(keyword);
        return ResponseEntity.ok(issues);
    }
}
