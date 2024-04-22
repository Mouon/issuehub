package com.everysource.everysource.dto;

import com.everysource.everysource.domain.Issue;
import com.everysource.everysource.domain.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Getter @Setter
@NoArgsConstructor
public class ProjectReadmeDetailDTO {
    private Long id;

    private String title;
    private String owner;
    private String repo;
    private String content;

    public ProjectReadmeDetailDTO(Optional<Project> project) {
        project.ifPresent(i -> {
            this.id = i.getId();
            this.owner = i.getOwner();
            this.repo = i.getRepo();
            this.content=i.getContent();
        });
    }

    public ProjectReadmeDetailDTO(Project project) {
        if (project != null) {
            this.id = project.getId();
            this.owner = project.getOwner();
            this.repo = project.getRepo();
            this.content = project.getContent();
        }
    }




}
