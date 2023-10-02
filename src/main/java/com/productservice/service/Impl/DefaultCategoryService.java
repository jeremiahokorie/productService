package com.productservice.service.Impl;

import com.productservice.dto.request.CategoryDto;
import com.productservice.dto.request.CategoryRequest;
import com.productservice.persistence.entity.Category;
import com.productservice.persistence.repository.CategoryRepository;
import com.productservice.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto create(CategoryRequest category) {
        Category category1 = new Category();
        category1.setName(category.getName());
        Category category2 =  categoryRepository.save(category1);
         CategoryDto categoryDto = new CategoryDto();
         categoryDto.setName(category2.getName());
         return categoryDto;
    }

    @Override
    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }
}