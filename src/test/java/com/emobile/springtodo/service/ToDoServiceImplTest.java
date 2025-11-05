package com.emobile.springtodo.service;


import com.emobile.springtodo.dto.ToDoDto;
import com.emobile.springtodo.mapper.ToDoMapper;
import com.emobile.springtodo.metrics.TaskMetrics;
import com.emobile.springtodo.model.ToDo;
import com.emobile.springtodo.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Unit-тесты для ToDoServiceImpl")
class ToDoServiceImplTest {

    private ToDoRepository  toDoRepository;
    private ToDoMapper mapper;
    private ToDoServiceImpl service;

    @Mock
    private TaskMetrics taskMetrics;

    @BeforeEach
    void setUp() {
        toDoRepository = mock(ToDoRepository.class);
        mapper = Mappers.getMapper(ToDoMapper.class);
        service = new ToDoServiceImpl(taskMetrics, toDoRepository, mapper);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Создание новой задачи!")
    void create_ShouldReturnDtoWithId() {

        ToDoDto inputDto = new ToDoDto();
        inputDto.setTitle("Test Task");
        inputDto.setDescription("Test description");
        inputDto.setCompleted(false);

        ToDo entityFromDb = new ToDo(
                1L,
                inputDto.getTitle(),
                inputDto.getDescription(),
                inputDto.getCompleted(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(toDoRepository.create(any(ToDo.class))).thenReturn(entityFromDb);

        ToDoDto result = service.create(inputDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Task");
        assertThat(result.getDescription()).isEqualTo("Test description");
        assertThat(result.getCompleted()).isFalse();

        verify(toDoRepository, times(1)).create(any(ToDo.class));
    }





    @Test
    @DisplayName("Метод getById должен вернуть DTO по id!")
    void testGetById() {
        ToDo entity = new ToDo(1L, "Task", "Desc", true, LocalDateTime.now(), LocalDateTime.now());
        when(toDoRepository.findById(1L)).thenReturn(entity);

        ToDoDto result = service.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCompleted()).isTrue();

        verify(toDoRepository, times(1)).findById(1L);
    }


    @Test
    @DisplayName("Метод update должен вернуть обновленный DTO!")
    void testUpdate() {
        Long id = 1L;
        ToDoDto dto = new ToDoDto(null, "Updated Task", "Updated Desc", true);
        ToDo updated = new ToDo(id, "Updated Task", "Updated Desc", true, LocalDateTime.now(), LocalDateTime.now());

        when(toDoRepository.update(eq(id), any(ToDo.class))).thenReturn(updated);

        ToDoDto result = service.update(id, dto);

        assertThat(result.getTitle()).isEqualTo("Updated Task");
        assertThat(result.getCompleted()).isTrue();

        verify(toDoRepository, times(1)).update(eq(id), any(ToDo.class));
    }


    @Test
    @DisplayName("Метод delete должен удалить задачу!")
    void testDelete() {
        Long id = 1L;
        doNothing().when(toDoRepository).delete(id);

        service.delete(id);

        verify(toDoRepository, times(1)).delete(id);
    }

}