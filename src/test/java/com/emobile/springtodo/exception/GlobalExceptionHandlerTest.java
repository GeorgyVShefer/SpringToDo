//package com.emobile.springtodo.exception;
//
//import com.emobile.springtodo.service.ToDoService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class GlobalExceptionHandlerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ToDoService toDoService;
//
//    @Test
//    @DisplayName("Проверка на TaskNotFoundException!")
//    void whenTaskNotFound_thenReturns404() throws Exception {
//        given(toDoService.getById(9999L))
//                .willThrow(new TaskNotFoundException("Task with id 9999 not found"));
//
//        mockMvc.perform(get("/api/todos/9999"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error").value("Task Not Found"));
//    }
//
//
//
//    @Test
//    @DisplayName("Проверка на общий Exception!")
//    void whenUnexpectedException_thenReturns500() throws Exception {
//        given(toDoService.getById(1L))
//                .willThrow(new RuntimeException("Unexpected error"));
//
//        mockMvc.perform(get("/api/todos/1"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.error").value("Internal Server Error"))
//                .andExpect(jsonPath("$.message").value("Unexpected error"));
//    }
//}