package com.everysource.everysource.controller;

import com.everysource.everysource.dto.api.IssueDTO;
import com.everysource.everysource.dto.api.IssueListDTO;
import com.everysource.everysource.repository.api.IssueRepository;
import com.everysource.everysource.service.RecommendService;
import com.everysource.everysource.service.api.GitHubDataService;
import com.everysource.everysource.service.IssueService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private GitHubDataService gitHubDataService;
    @Autowired
    private IssueService issueService;

    @Autowired
    private RecommendService recommendService;
    @Autowired
    private IssueRepository issueRepository;

    @GetMapping("/{owner}/{repo}")
    public ResponseEntity<List<IssueDTO>> getIssues(@PathVariable String owner, @PathVariable String repo) {
        gitHubDataService.fetchIssues(owner, repo);
        return (ResponseEntity<List<IssueDTO>>) ResponseEntity.ok();
    }


    @GetMapping("/list")
    public ResponseEntity<List<IssueListDTO>> getAllIssues() {
        List<IssueListDTO> issues = issueService.findAllIssues();
        return ResponseEntity.ok(issues);
    }
    @GetMapping("/list/owner")
    public ResponseEntity<List<IssueListDTO>> getOwnerIssues(@RequestParam String owner) {
        List<IssueListDTO> issues = issueService.findIssuesByOwner(owner);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/list/repo")
    public ResponseEntity<List<IssueListDTO>> getRepoIssues(@RequestParam String repo) {
        List<IssueListDTO> issues = issueService.findIssuesByRepo(repo);
        return ResponseEntity.ok(issues);
    }



    @GetMapping("/detail")
    public ResponseEntity<IssueDTO> getIssuesDetail(@RequestParam Long id, HttpSession session) {
        Long memberId = null;
        Object memberIdObj = session.getAttribute("memberId");
        if (memberIdObj != null) {
            memberId = (Long) memberIdObj;
        }
        IssueDTO issue = issueService.findIssuesDetail(memberId, id);
        return ResponseEntity.ok(issue);
    }

    @GetMapping("/list/recommend")
    public ResponseEntity<List<IssueListDTO>> getRecommendIssue(@RequestParam Long memberId,HttpSession session) {
        List<IssueListDTO> issues = recommendService.recommendIssues(memberId);

        return ResponseEntity.ok(issues);
    }

}
