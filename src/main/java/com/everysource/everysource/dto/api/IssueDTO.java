package com.everysource.everysource.dto.api;

import com.everysource.everysource.domain.api.Issue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@RequiredArgsConstructor
@Getter @Setter
public class IssueDTO {
    private Long id;
    private String title;
    private String status;
    private String owner;
    private String repo;
    private String updateDate;
    private String detail;

    public IssueDTO(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.status = issue.getState();
        this.detail = issue.getBody();
        this.owner=issue.getOwner();
        this.repo=issue.getRepo();
    }

    public IssueDTO(Optional<Issue> issue) {
        issue.ifPresent(i -> {
            this.id = i.getId();
            this.title = i.getTitle();
            this.status = i.getState();
            this.detail = i.getBody();
            this.owner = i.getOwner();
            this.repo = i.getRepo();
        });
    }


    public IssueDTO(Long id, String title, String detail, String state) {
        this.id = id;
        this.title =title;
        this.detail=detail;
        this.status=state;
    }
}
