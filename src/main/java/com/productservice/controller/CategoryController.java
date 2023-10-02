package com.productservice.controller;

import com.productservice.core.constants.AppConstant;
import com.productservice.dto.request.CategoryDto;
import com.productservice.dto.request.CategoryRequest;
import com.productservice.dto.response.CategoryResponse;
import com.productservice.persistence.entity.Category;
import com.productservice.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AppConstant.APP_CONTEXT)
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryRequest category){
        Map<String, Object> map = new LinkedHashMap<>();
        CategoryDto category1 = categoryService.create(category);
        map.put("status","201");
        map.put("message","Category Created Successfully");
        map.put("data",category1);
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @GetMapping("/getCategory")
    public ResponseEntity<Category> getAllCategory(){
        Map<String,Object> map = new LinkedHashMap<>();
        List<Category> category =  categoryService.getCategory();
        map.put("status","200");
        map.put("message","Category Successfully Retrieved");
        map.put("data",category);
        return new ResponseEntity(map,HttpStatus.FOUND);
    }
}
