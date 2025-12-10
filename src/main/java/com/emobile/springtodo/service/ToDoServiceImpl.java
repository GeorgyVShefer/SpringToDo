package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.ToDoDto;
import com.emobile.springtodo.exception.TaskNotFoundException;
import com.emobile.springtodo.mapper.ToDoMapper;
import com.emobile.springtodo.model.ToDo;
import com.emobile.springtodo.repository.ToDoDao;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {


    private final ToDoDao toDoDao;
    private final ToDoMapper mapper;
    private final ToDoMapper toDoMapper;

    public ToDoServiceImpl(SessionFactory sessionFactory, ToDoMapper mapper,
                           ToDoMapper toDoMapper) {
        this.toDoDao = new ToDoDao(sessionFactory);
        this.mapper = mapper;
        this.toDoMapper = toDoMapper;
    }

    public ToDoDto create(ToDoDto dto) {

        ToDo entity = mapper.toEntity(dto);
        Long id = toDoDao.save(entity);
        ToDo byId = toDoDao.getById(id);
        return mapper.toDto(byId);
    }

    @Override
    public ToDoDto update(Long id, ToDoDto toDoDto) {

        ToDo existingTodo = toDoDao.getById(id);
        if (existingTodo == null) {
            throw new TaskNotFoundException("Задача с id: " + id + " не найдена");
        }

        if (toDoDto.getId() != null && !existingTodo.getId().equals(toDoDto.getId())) {
            throw new TaskNotFoundException("ID в пути и в теле запроса не совпадают");
        }


        existingTodo.setTitle(toDoDto.getTitle());
        existingTodo.setDescription(toDoDto.getDescription());
        existingTodo.setUpdatedAt(LocalDateTime.now());


        toDoDao.update(existingTodo);


        return mapper.toDto(existingTodo);
    }

    @Override
    public ToDoDto getById(Long id) {

        ToDo byId = toDoDao.getById(id);
        ToDoDto dto = mapper.toDto(byId);

        return dto;
    }

    @Override
    public void delete(Long id) {

        toDoDao.deleteById(id);
    }
}
