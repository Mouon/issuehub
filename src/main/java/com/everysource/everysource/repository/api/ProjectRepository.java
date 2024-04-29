package com.everysource.everysource.repository.api;

import com.everysource.everysource.domain.api.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.repo = :repo")
    Project getContentByRepo(String repo);

    Optional<Project> findByOwnerAndRepo(String owner, String repo);
}
