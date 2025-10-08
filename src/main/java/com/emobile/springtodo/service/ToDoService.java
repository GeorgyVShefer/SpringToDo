package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.PaginatedDto;
import com.emobile.springtodo.dto.ToDoDto;

import java.util.List;


public interface ToDoService {

    ToDoDto create(ToDoDto toDoDto);

    ToDoDto update(Long id, ToDoDto toDoDto);

    ToDoDto getById(Long id);

    PaginatedDto getAllPaginated(int limit, int offset);


    void delete(Long id);

    ToDoDto completeTask(Long id);
}