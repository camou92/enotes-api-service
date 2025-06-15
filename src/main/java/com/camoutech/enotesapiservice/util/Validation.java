package com.camoutech.enotesapiservice.util;

import com.camoutech.enotesapiservice.dto.CategoryDto;
import java.util.LinkedHashMap;
import java.util.Map;

import com.camoutech.enotesapiservice.dto.TodoDto;
import com.camoutech.enotesapiservice.dto.TodoDto.StatusDto;
import com.camoutech.enotesapiservice.enums.TodoStatus;
import com.camoutech.enotesapiservice.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class Validation {

    public void categoryValidation(CategoryDto categoryDto) {

        Map<String, Object> error = new LinkedHashMap<>();

        if (ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException("category Object/JSON shouldn't be null or empty");
        } else {

            // validation name field
            if (ObjectUtils.isEmpty(categoryDto.getName())) {
                error.put("name", "name field is empty or null");
            } else {
                if (categoryDto.getName().length() < 2) {
                    error.put("name", "name length min 2");
                }
                if (categoryDto.getName().length() > 100) {
                    error.put("name", "name length max 100");
                }
            }

            // validation dscription
            if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
                error.put("description", "description field is empty or null");
            }

            // validation isActive
            if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
                error.put("isActive", "isActive field is empty or null");
            } else {
                if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue()
                        && categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
                    error.put("isActive", "invalid value isActive field ");
                }
            }
        }

        if (!error.isEmpty()) {
            throw new ValidationException(error);
        }

    }

    public void todoValidation(TodoDto todo) throws Exception {
        StatusDto reqStatus = todo.getStatus();
        Boolean statusFound = false;
        for (TodoStatus st : TodoStatus.values()) {
            if (st.getId().equals(reqStatus.getId())) {
                statusFound = true;
            }
        }
        if (!statusFound) {
            throw new ResourceNotFoundException("invalid status");
        }

    }

}
