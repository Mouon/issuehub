package com.everysource.everysource.repository.api;

import com.everysource.everysource.domain.api.MemberIssueActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberIssueActivityRepository extends JpaRepository<MemberIssueActivity, Long> {

    @Query("SELECT mia FROM MemberIssueActivity mia WHERE mia.member.id = :memberId AND mia.issue.id = :issueId")
    Optional<MemberIssueActivity> findByMemberIdAndIssueId(@Param("memberId") Long memberId, @Param("issueId") Long issueId);

    List<MemberIssueActivity> findByMemberId(Long memberId);

    @Query("SELECT mia.issue.id FROM MemberIssueActivity mia WHERE mia.member.id = :memberId ORDER BY mia.viewCount DESC")
    List<Long> findTopIssueIdsByMemberId(@Param("memberId") Long memberId);

}
