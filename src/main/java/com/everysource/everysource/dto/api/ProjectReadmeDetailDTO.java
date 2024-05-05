package com.everysource.everysource.dto.api;

import com.everysource.everysource.domain.api.Project;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;

@Getter @Setter
@RequiredArgsConstructor
public class ProjectReadmeDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
}
