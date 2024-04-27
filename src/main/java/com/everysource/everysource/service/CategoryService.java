package com.everysource.everysource.service;

import com.everysource.everysource.domain.api.Category;
import com.everysource.everysource.dto.CategoryPoolDTO;
import com.everysource.everysource.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private  final CategoryRepository categoryRepository;

    public List<CategoryPoolDTO> findAllCategorise() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryPoolDTO::new)
                .collect(Collectors.toList());
    }

}
