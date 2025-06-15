package com.camoutech.enotesapiservice.service.impl;

import com.camoutech.enotesapiservice.dto.TodoDto;
import com.camoutech.enotesapiservice.dto.TodoDto.StatusDto;
import com.camoutech.enotesapiservice.entity.Todo;
import com.camoutech.enotesapiservice.enums.TodoStatus;
import com.camoutech.enotesapiservice.exception.ResourceNotFoundException;
import com.camoutech.enotesapiservice.repository.TodoRepository;
import com.camoutech.enotesapiservice.service.TodoService;
import com.camoutech.enotesapiservice.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;

    @Override
    public Boolean saveTodo(TodoDto todoDto) throws Exception {
        // validate todo status
        validation.todoValidation(todoDto);

        Todo todo = mapper.map(todoDto, Todo.class);
        todo.setStatusId(todoDto.getStatus().getId());
        Todo saveTodo = todoRepository.save(todo);
        if (!ObjectUtils.isEmpty(saveTodo)) {
            return true;
        }
        return false;
    }

    @Override
    public TodoDto getTodoById(Integer id) throws Exception {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Todo Not Found ! id invalid"));
        TodoDto todoDto = mapper.map(todo, TodoDto.class);
        setStatus(todoDto,todo);
        return todoDto;
    }

    private void setStatus(TodoDto todoDto, Todo todo) {

        for(TodoStatus st:TodoStatus.values())
        {
            if(st.getId().equals(todo.getStatusId()))
            {
                StatusDto statusDto=StatusDto.builder()
                        .id(st.getId())
                        .name(st.getName())
                        .build();
                todoDto.setStatus(statusDto);
            }
        }

    }


    @Override
    public List<TodoDto> getTodoByUser() {
        Integer userId = 2;
        List<Todo> todos = todoRepository.findByCreatedBy(userId);
        return todos.stream().map(td -> mapper.map(td, TodoDto.class)).toList();
    }
}
