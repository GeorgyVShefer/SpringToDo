package com.emobile.springtodo.api;

import com.emobile.springtodo.dto.ToDoDto;
import com.emobile.springtodo.service.ToDoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers

class ToDoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ToDoService toDoService;

    @Test
    @DisplayName("Проверяет контроллер на получения объекта по id!")
    public void testGetById() throws Exception {
        // Подготовка мок-объекта
        ToDoDto todo = new ToDoDto();
        todo.setId(1L);
        todo.setDescription("Test description");
        todo.setCompleted(false);

        when(toDoService.getById(1L)).thenReturn(todo);

        // Выполнение запроса
        MvcResult result = mockMvc.perform(get("/api/todos/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Вывод тела ответа для отладки
        System.out.println(result.getResponse().getContentAsString());

        // Проверка JSON с помощью JSONAssert
        String expectedJson = """
            {
                "id": 1,
                "title": null,
                "description": "Test description",
                "completed": false
            }
            """;

        JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), true);
    }



    @Test
    @DisplayName("Проверяет контроллер на создание задачи!")
    public void testCreate() throws Exception {
        ToDoDto inputDto = new ToDoDto();
        inputDto.setTitle("New Todo");
        inputDto.setDescription("New description");
        inputDto.setCompleted(false);

        ToDoDto createdDto = new ToDoDto();
        createdDto.setId(1L);
        createdDto.setTitle("New Todo");
        createdDto.setDescription("New description");
        createdDto.setCompleted(false);

        when(toDoService.create(any(ToDoDto.class))).thenReturn(createdDto);

        String requestJson = """
        {
          "title": "New Todo",
          "description": "New description",
          "completed": false
        }
        """;

        String expectedJson = """
        {
          "id": 1,
          "title": "New Todo",
          "description": "New description",
          "completed": false
        }
        """;

        MvcResult result = mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Если хочешь 201, настрой контроллер
                .andReturn();

        JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), true);
    }

    @Test
    @DisplayName("Проверяет контроллер на изменение задачи по id!")
    public void testUpdate() throws Exception {
        ToDoDto inputDto = new ToDoDto();
        inputDto.setTitle("Updated Todo");
        inputDto.setDescription("Updated description");
        inputDto.setCompleted(true);

        ToDoDto updatedDto = new ToDoDto();
        updatedDto.setId(1L);
        updatedDto.setTitle("Updated Todo");
        updatedDto.setDescription("Updated description");
        updatedDto.setCompleted(true);

        when(toDoService.update(eq(1L), any(ToDoDto.class))).thenReturn(updatedDto);

        String requestJson = """
        {
          "title": "Updated Todo",
          "description": "Updated description",
          "completed": true
        }
        """;

        String expectedJson = """
        {
          "id": 1,
          "title": "Updated Todo",
          "description": "Updated description",
          "completed": true
        }
        """;

        MvcResult result = mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), true);
    }

    // ---------------------- delete ----------------------
    @DisplayName("Проверяет контроллер на удаление задачи по id!")
    @Test
    public void testDelete() throws Exception {
        // Просто проверяем, что сервис вызван и статус 204
        doNothing().when(toDoService).delete(1L);

        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isOk());;

        verify(toDoService, times(1)).delete(1L);
    }
}