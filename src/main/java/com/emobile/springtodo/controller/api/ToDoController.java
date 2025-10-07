package com.emobile.springtodo.controller.api;


import com.emobile.springtodo.dto.ToDoDto;
import com.emobile.springtodo.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class ToDoController implements ToDoApi{

    private final ToDoService toDoService;

    @Override
    public List<ToDoDto> getAll(int limit, int offset) {
        return toDoService.getAll(limit, offset);
    }

    @Override
    public ToDoDto getById(Long id) {
        return toDoService.getById(id);
    }

    @Override
    public ToDoDto create(ToDoDto dto) {
        return toDoService.create(dto);
    }

    @Override
    public ToDoDto update(Long id, ToDoDto dto) {
        return toDoService.update(id, dto);
    }

    @Override
    public void delete(Long id) {
        toDoService.delete(id);
    }

    @Override
    public ToDoDto completeTask(Long id) {
        return toDoService.completeTask(id);
    }
}
