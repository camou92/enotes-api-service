package com.camoutech.enotesapiservice.service;

import com.camoutech.enotesapiservice.entity.Category;

import java.util.List;

public interface CategoryService {
    public Boolean saveCategory(Category category);
    public List<Category> getAllCategory();
}
