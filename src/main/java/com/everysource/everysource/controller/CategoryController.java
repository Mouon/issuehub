package com.everysource.everysource.controller;

import com.everysource.everysource.dto.api.CategoryPoolDTO;
import com.everysource.everysource.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<List<CategoryPoolDTO>> getAllCategorise() {
        List<CategoryPoolDTO> categorise = categoryService.findAllCategorise();
        return ResponseEntity.ok(categorise);
    }



}
