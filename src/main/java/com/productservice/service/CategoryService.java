package com.productservice.service;

import com.productservice.dto.request.CategoryDto;
import com.productservice.dto.request.CategoryRequest;
import com.productservice.persistence.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryRequest category);
    List<Category> getCategory();

    CategoryDto getCategoryById(Long id);
}
