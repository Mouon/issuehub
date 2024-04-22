package com.everysource.everysource.repository;

import com.everysource.everysource.domain.Project;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProjectReactiveRepository extends ReactiveCrudRepository<Project, Long> {
    Mono<Project> findByRepo(String repo);
}
