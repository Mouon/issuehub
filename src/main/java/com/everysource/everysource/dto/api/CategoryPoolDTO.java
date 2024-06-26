package com.everysource.everysource.dto.api;

import com.everysource.everysource.domain.api.Category;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryPoolDTO {

    private Long id;
    private String name;
    private String stack;

    public CategoryPoolDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.stack=category.getStack();
    }
}
