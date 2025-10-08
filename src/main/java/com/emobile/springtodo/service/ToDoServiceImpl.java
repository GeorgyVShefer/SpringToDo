package com.emobile.springtodo.service;

import com.emobile.springtodo.dto.PaginatedDto;
import com.emobile.springtodo.dto.ToDoDto;
import com.emobile.springtodo.exception.TaskNotFoundException;
import com.emobile.springtodo.mapper.ToDoMapper;
import com.emobile.springtodo.metrics.TaskMetrics;
import com.emobile.springtodo.model.ToDo;
import com.emobile.springtodo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final TaskMetrics taskMetrics;
    private final ToDoRepository toDoRepository;
    private final ToDoMapper mapper;

    @Override
    public PaginatedDto getAllPaginated(int limit, int offset) {

        List<ToDoDto> todos = toDoRepository.findTodos(limit, offset);
        boolean hasNext = todos.size() == limit;
        return new PaginatedDto(todos, limit, offset, hasNext);
    }

    @Override
    @Cacheable(value = "todos", key = "#id")
    public ToDoDto getById(Long id) {
        try {
            ToDo entityById = toDoRepository.findById(id);

            return mapper.toDto(entityById);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new TaskNotFoundException("Task with id " + id + " not found");
        }

    }


    @Override
    @CachePut(value = "todos", key = "#result.id")
    public ToDoDto create(ToDoDto dto) {

        ToDo entityToCreate = mapper.toEntity(dto);

        ToDo createdEntity = toDoRepository.create(entityToCreate);

        ToDoDto result = mapper.toDto(createdEntity);

        if (result.getId() == null) {
            result.setId(createdEntity.getId());
        }

        return result;
    }

    @Override
    @CachePut(value = "todos", key = "#id")
    public ToDoDto update(Long id, ToDoDto dto) {

        ToDo entity = mapper.toEntity(dto);

        ToDo updated = toDoRepository.update(id, entity);

        return mapper.toDto(updated);
    }


    @Override
    @CacheEvict(value = "todos", key = "#id")
    public void delete(Long id) {

        toDoRepository.delete(id);
    }

    @Override
    public ToDoDto completeTask(Long id) {

        ToDo task = toDoRepository.findById(id);
        if (!Boolean.TRUE.equals(task.getCompleted())) {
            task.setCompleted(true);
            toDoRepository.create(task);

            // Увеличиваем кастомную метрику
            taskMetrics.incrementCompletedTasks();
        }

        return new ToDoDto(task.getId(), task.getTitle(), task.getDescription(), task.getCompleted());
    }

}
