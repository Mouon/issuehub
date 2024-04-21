package com.everysource.everysource.dto;


import com.everysource.everysource.domain.Issue;
import com.everysource.everysource.domain.Project;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter
@RequiredArgsConstructor
public class ProjectReadmeDTO {
    private Long id;

    private String content;
    private String owner;
    private String repo;


    public ProjectReadmeDTO(Optional<Project> project) {
        project.ifPresent(i -> {
            this.id = i.getId();
            this.owner = i.getOwner();
            this.repo = i.getRepo();
            this.content=i.getContent();
        });
    }

}
