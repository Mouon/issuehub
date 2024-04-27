package com.everysource.everysource.dto;

import com.everysource.everysource.domain.api.Project;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter
@RequiredArgsConstructor
public class ProReadmeListDTO {
    private Long id;

    private String owner;
    private String repo;


    public ProReadmeListDTO(Optional<Project> project) {
        project.ifPresent(i -> {
            this.id = i.getId();
            this.owner = i.getOwner();
            this.repo = i.getRepo();
        });
    }
}
