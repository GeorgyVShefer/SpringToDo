package com.emobile.springtodo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedDto {

    private List<ToDoDto> todos;
    private int limit;
    private int offset;
    private boolean hasNext; // есть ли следующая страница

}
