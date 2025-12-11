package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.ToDoDto;
import com.emobile.springtodo.exception.TaskNotFoundException;
import com.emobile.springtodo.mapper.ToDoMapper;
import com.emobile.springtodo.model.ToDo;
import com.emobile.springtodo.repository.ToDoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class ToDoServiceImpl implements ToDoService {


    private ToDoRepository toDoRepository;
    private ToDoMapper mapper;

    public ToDoServiceImpl(ToDoRepository toDoRepository, ToDoMapper mapper) {
        this.toDoRepository = toDoRepository;
        this.mapper = mapper;
    }

    public ToDoDto create(ToDoDto dto) {

        ToDo entity = mapper.toEntity(dto);
        ToDo saveEntity = toDoRepository.save(entity);

        return mapper.toDto(saveEntity);
    }

    @Override
    public ToDoDto update(Long id, ToDoDto toDoDto) {

        ToDo existingTodo = toDoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Нет пользователя с таким id"));


        if (toDoDto.getId() != null && !existingTodo.getId().equals(toDoDto.getId())) {
            throw new TaskNotFoundException("ID в пути и в теле запроса не совпадают");
        }

        toDoRepository.save(existingTodo);
        return mapper.toDto(existingTodo);
    }

    @Override
    public ToDoDto getById(Long id) {

        ToDo entity = toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("Нет пользователя с таким id!"));
        ToDoDto dto = mapper.toDto(entity);

        return dto;
    }

    @Override
    public void delete(Long id) {

        toDoRepository.deleteById(id);
    }
}
