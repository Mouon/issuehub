package com.everysource.everysource.dto.api;

import com.everysource.everysource.domain.api.Issue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
