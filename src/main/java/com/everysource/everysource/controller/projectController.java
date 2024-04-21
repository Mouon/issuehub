package com.everysource.everysource.controller;

import com.everysource.everysource.dto.ProReadmeListDTO;
import com.everysource.everysource.dto.ProjectReadmeDTO;
import com.everysource.everysource.dto.ProjectReadmeDetailDTO;
import com.everysource.everysource.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class projectController {

    @Autowired
    private final ProjectService projectService;
    @GetMapping("/readme")
    public ResponseEntity<ProReadmeListDTO> getRepoReadme(@RequestParam String repo) {
        ProReadmeListDTO project = projectService.findReadmeByRepo(repo);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/readme/detail")
    public ResponseEntity<ProjectReadmeDetailDTO> getRepoReadmeDetail(@RequestParam String repo) {
        ProjectReadmeDetailDTO project = projectService.findReadmeDetail(repo);
        return ResponseEntity.ok(project);
    }
}
