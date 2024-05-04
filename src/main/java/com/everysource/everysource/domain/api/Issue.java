package com.everysource.everysource.domain.api;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
public class Issue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String title;
    @Setter
    private String owner;
    @Setter
    private String repo;

    private String state;
    private String since;

    @Column(columnDefinition = "TEXT")
    private String body;

    private int searchCount;

    @OneToMany(mappedBy = "issue")
    private List<MemberIssueActivity> activities;

    public void updateIssue(String newStatus, String newUpdateDate, String newDetail) {
        if (newStatus != null) {
            this.state = newStatus;
        }
        if (newUpdateDate != null) {
            this.since = newUpdateDate;
        }
        if (newDetail != null) {
            this.body = newDetail;
        }
    }
}
