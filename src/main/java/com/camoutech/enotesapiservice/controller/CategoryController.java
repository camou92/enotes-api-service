package com.camoutech.enotesapiservice.controller;

import com.camoutech.enotesapiservice.dto.CategoryDto;
import com.camoutech.enotesapiservice.dto.CategoryResponse;
import com.camoutech.enotesapiservice.entity.Category;
import com.camoutech.enotesapiservice.exception.ResourceNotFoundException;
import com.camoutech.enotesapiservice.service.CategoryService;
import com.camoutech.enotesapiservice.util.CommonUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {

        Boolean saveCategory = categoryService.saveCategory(categoryDto);
        if (saveCategory) {
            return CommonUtil.createBuildResponseMessage("saved success", HttpStatus.CREATED);
            //return new ResponseEntity<>("saved success", HttpStatus.CREATED);
        } else {
            return CommonUtil.createErrorResponseMessage("Category not saved", HttpStatus.INTERNAL_SERVER_ERROR);
            //return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDto> allCategory = categoryService.getAllCategory();

        if (CollectionUtils.isEmpty(allCategory)) {
            return ResponseEntity.noContent().build();
        } else {
            return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
            //return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryResponse> allCategory = categoryService.getActiveCategory();
        if (CollectionUtils.isEmpty(allCategory)) {
            return ResponseEntity.noContent().build();
        } else {
            return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
            //return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws ResourceNotFoundException {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if (ObjectUtils.isEmpty(categoryDto)) {
            return CommonUtil.createErrorResponseMessage("Internal Server Error", HttpStatus.NOT_FOUND);
            //return new ResponseEntity<>("Category not found with Id=" + id, HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);
        //return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        Boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            return CommonUtil.createBuildResponse("category deleted success", HttpStatus.OK);
            //return new ResponseEntity<>("Category deleted success", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Categroy Not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        //return new ResponseEntity<>("Category not deleted ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
