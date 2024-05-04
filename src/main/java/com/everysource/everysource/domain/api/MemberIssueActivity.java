package com.everysource.everysource.domain.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class MemberIssueActivity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "issueId", nullable = false)
    private Issue issue;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private LocalDateTime lastViewed;

    public MemberIssueActivity(Member member, Issue issue, int viewCount, LocalDateTime lastViewed) {
        this.member = member;
        this.issue = issue;
        this.viewCount = viewCount;
        this.lastViewed = lastViewed;
    }

    public void incrementViewCount() {
        this.viewCount += 1;
        this.lastViewed = LocalDateTime.now();
    }

}
