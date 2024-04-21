package com.everysource.everysource.repository;

import com.everysource.everysource.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional("transactionManager")
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByOwner(String owner);
    List<Issue> findByRepo(String repo);

}
