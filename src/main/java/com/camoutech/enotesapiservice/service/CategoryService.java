package com.camoutech.enotesapiservice.service;

import com.camoutech.enotesapiservice.dto.CategoryDto;
import com.camoutech.enotesapiservice.dto.CategoryResponse;
import com.camoutech.enotesapiservice.entity.Category;

import java.util.List;

public interface CategoryService {
    public Boolean saveCategory(CategoryDto categoryDto);
    public List<CategoryDto> getAllCategory();
    public List<CategoryResponse> getActiveCategory();
}
