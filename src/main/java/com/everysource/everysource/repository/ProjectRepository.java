package com.everysource.everysource.repository;

import com.everysource.everysource.domain.Issue;
import com.everysource.everysource.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
@Transactional("transactionManager")
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.repo = :repo")
    Project getContentByRepo(String repo);
    @Query("SELECT * FROM project WHERE repo = :repo")
    Mono<Project> findByRepo(String repo);


}
