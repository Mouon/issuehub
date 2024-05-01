package com.everysource.everysource.repository.api;

import com.everysource.everysource.domain.api.Issue;
import com.everysource.everysource.domain.api.IssueSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IssueRepository extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {
    List<Issue> findByOwner(String owner);
    List<Issue> findByRepo(String repo);

    @Query("UPDATE Issue i SET i.searchCount = i.searchCount + 1 WHERE i.id = :id")
    @Modifying
    void incrementSearchCount(Long id);

    List<Issue> findTop10ByOrderBySearchCountDesc();

}
