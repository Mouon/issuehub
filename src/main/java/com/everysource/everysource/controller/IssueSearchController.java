package com.everysource.everysource.controller;

import com.everysource.everysource.domain.api.IssueSearch;
import com.everysource.everysource.dto.api.IssueListDTO;
import com.everysource.everysource.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<IssueListDTO>> searchIssues(@RequestParam String keyword) {
        List<IssueListDTO> issues = issueService.searchByKeyword(keyword);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/top-searched")
    public ResponseEntity<List<IssueListDTO>> getTopSearchedIssues() {
        List<IssueListDTO> issues =issueService.getTopSearchedIssues();
        return ResponseEntity.ok(issues);
    }

}
