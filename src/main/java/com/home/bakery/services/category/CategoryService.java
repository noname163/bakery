package com.home.bakery.services.category;

import com.home.bakery.data.dto.request.CategoryRequest;
import com.home.bakery.data.dto.response.CategoryResponse;

public interface CategoryService {
    public void createCategory(CategoryRequest categoryRequest);
    public CategoryResponse getListCategory();
}
