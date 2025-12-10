package com.emobile.springtodo.mapper;

import com.emobile.springtodo.dto.ToDoDto;
import com.emobile.springtodo.model.ToDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoMapper {

    ToDoDto toDto(ToDo entity);
    ToDo toEntity(ToDoDto dto);
}