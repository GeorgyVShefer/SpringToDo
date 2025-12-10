package com.emobile.springtodo.controller.api;


import com.emobile.springtodo.dto.ToDoDto;
import com.emobile.springtodo.service.ToDoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
public class ToDoController implements ToDoApi {

    private ToDoService toDoService;



    @Override
    public ToDoDto getById(@PathVariable Long id) {
        return toDoService.getById(id);
    }

    @Override
    public ToDoDto create(@RequestBody ToDoDto toDoDto) {

        return toDoService.create(toDoDto);
    }

    @Override
    public ToDoDto update(Long id, ToDoDto dto) {

        return toDoService.update(id, dto);
    }

    @Override
    public void delete(Long id) {

        toDoService.delete(id);
    }


}
