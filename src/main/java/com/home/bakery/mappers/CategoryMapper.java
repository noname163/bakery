package com.home.bakery.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.home.bakery.data.dto.response.CategoryResponse;
import com.home.bakery.data.entities.Category;

@Component
public class CategoryMapper {
    public CategoryResponse mapCategoryToCategoryResponse(Category category) {
        return CategoryResponse
                .builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public List<CategoryResponse> mapCategoriesToCategoryResponses(List<Category> categories) {
        return categories.stream().map(this::mapCategoryToCategoryResponse).collect(Collectors.toList());
    }
}
