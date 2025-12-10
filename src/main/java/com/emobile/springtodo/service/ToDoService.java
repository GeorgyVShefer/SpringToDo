package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.ToDoDto;


public interface ToDoService {

    ToDoDto create(ToDoDto toDoDto);

    ToDoDto update(Long id, ToDoDto toDoDto);

    ToDoDto getById(Long id);


    void delete(Long id);

}