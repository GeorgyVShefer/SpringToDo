package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.ToDoDto;

import java.util.List;


public interface ToDoService {

    ToDoDto create(ToDoDto toDoDto);

    ToDoDto update(Long id, ToDoDto toDoDto);

    ToDoDto getById(Long id);

    List<ToDoDto> getAll(int limit, int offset);

    void delete(Long id);

    ToDoDto completeTask(Long id);
}