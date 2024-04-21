package com.everysource.everysource.dto;

import com.everysource.everysource.domain.Issue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@RequiredArgsConstructor
@Getter @Setter
public class IssueListDTO {

    private Long id;
    private String title;
    private String status;
    private String owner;
    private String repo;

    public IssueListDTO(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.status = issue.getState();
        this.owner=issue.getOwner();
        this.repo=issue.getRepo();
    }

}
