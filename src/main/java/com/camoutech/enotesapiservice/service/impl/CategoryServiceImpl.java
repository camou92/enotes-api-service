package com.camoutech.enotesapiservice.service.impl;

import com.camoutech.enotesapiservice.dto.CategoryDto;
import com.camoutech.enotesapiservice.dto.CategoryResponse;
import com.camoutech.enotesapiservice.entity.Category;
import com.camoutech.enotesapiservice.exception.ResourceNotFoundException;
import com.camoutech.enotesapiservice.repository.CategoryRepository;
import com.camoutech.enotesapiservice.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setDescription(categoryDto.getDescription());
//        category.setIsActive(categoryDto.getIsActive());

        Category category = mapper.map(categoryDto, Category.class);

        if (ObjectUtils.isEmpty(category.getId())) {
            category.setIsDeleted(false);
            category.setCreatedBy(1);
            category.setCreatedOn(new Date());
        } else {
            updateCategory(category);
        }

        Category saveCategory = categoryRepository.save(category);
        if (ObjectUtils.isEmpty(saveCategory)) {
            return false;
        }
        return true;
    }

    private void updateCategory(Category category) {
        Optional<Category> findById = categoryRepository.findById(category.getId());
        if (findById.isPresent()) {
            Category existCategory = findById.get();
            category.setCreatedBy(existCategory.getCreatedBy());
            category.setCreatedOn(existCategory.getCreatedOn());
            category.setIsDeleted(existCategory.getIsDeleted());

            category.setUpdatedBy(1);
            category.setUpdatedOn(new Date());
        }
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        return categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();

        return categories.stream().map(cat-> mapper.map(cat, CategoryResponse.class)).toList();
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id=" + id));

        if (!ObjectUtils.isEmpty(category)) {
            return mapper.map(category, CategoryDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Optional<Category> findByCategory = categoryRepository.findById(id);

        if (findByCategory.isPresent()) {
            Category category = findByCategory.get();
            category.setIsDeleted(true);
            categoryRepository.save(category);
            return true;
        }
        return false;
    }
}
