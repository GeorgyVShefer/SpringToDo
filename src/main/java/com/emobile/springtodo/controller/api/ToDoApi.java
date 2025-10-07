package com.emobile.springtodo.controller.api;


import com.emobile.springtodo.dto.ToDoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "ToDo API", description = "Операции для управления задачами")
@RequestMapping("/api/todos")
public interface ToDoApi {

    @Operation(summary = "Получить все задачи", description = "Возвращает список задач с пагинацией")
    @ApiResponse(responseCode = "200", description = "Успешно получено",
            content = @Content(schema = @Schema(implementation = ToDoDto.class)))
    @GetMapping
    List<ToDoDto> getAll(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    );

    @Operation(summary = "Получить задачу по ID")
    @ApiResponse(responseCode = "200", description = "Задача найдена",
            content = @Content(schema = @Schema(implementation = ToDoDto.class)))
    @GetMapping("/{id}")
    ToDoDto getById(@PathVariable Long id);

    @Operation(summary = "Создать задачу")
    @ApiResponse(responseCode = "201", description = "Задача создана",
            content = @Content(schema = @Schema(implementation = ToDoDto.class)))
    @PostMapping
    ToDoDto create( @RequestBody ToDoDto dto);

    @Operation(summary = "Обновить задачу")
    @ApiResponse(responseCode = "200", description = "Задача обновлена",
            content = @Content(schema = @Schema(implementation = ToDoDto.class)))
    @PutMapping("/{id}")
    ToDoDto update(@PathVariable Long id, @RequestBody ToDoDto dto);

    @Operation(summary = "Удалить задачу")
    @ApiResponse(responseCode = "204", description = "Задача удалена")
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

    @Operation(summary = "Отметить задачу как выполненную")
    @ApiResponse(responseCode = "200", description = "Задача выполнена")
    @PostMapping("/{id}/complete")
    ToDoDto completeTask(@PathVariable Long id);
}
