package com.home.bakery.services.category.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.bakery.data.dto.request.CategoryRequest;
import com.home.bakery.data.dto.response.CategoryResponse;
import com.home.bakery.data.entities.Category;
import com.home.bakery.data.repositories.CategoryRepository;
import com.home.bakery.services.category.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder().name(categoryRequest.getName())
                .description(categoryRequest.getDescription()).build();
        categoryRepository.save(category);
    }

    @Override
    public CategoryResponse getListCategory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListCategory'");
    }

}
