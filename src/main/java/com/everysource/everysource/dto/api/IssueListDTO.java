package com.everysource.everysource.dto.api;

import com.everysource.everysource.domain.api.Issue;
import com.everysource.everysource.domain.api.IssueSearch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter @Setter
public class IssueListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String status;
    private String owner;
    private String repo;
    private int searchCount;



    public IssueListDTO(Issue issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.status = issue.getState();
        this.owner=issue.getOwner();
        this.repo=issue.getRepo();
        this.searchCount= issue.getSearchCount();
    }

    public IssueListDTO(IssueSearch issue) {
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.status = issue.getState();
        this.owner=issue.getOwner();
        this.repo=issue.getRepo();
        this.searchCount= issue.getSearchCount();
    }

}
