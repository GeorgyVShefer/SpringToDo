//package com.emobile.springtodo.repository;
//
//import com.emobile.springtodo.model.ToDo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.jdbc.Sql;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Sql(statements = {
//        "DROP TABLE IF EXISTS todo;",
//        "CREATE TABLE todo (" +
//                "id BIGSERIAL PRIMARY KEY," +
//                "title VARCHAR(255) NOT NULL," +
//                "description TEXT," +
//                "completed BOOLEAN DEFAULT FALSE," +
//                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
//                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
//                ");"
//})
//
//class ToDoRepositoryTest {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private ToDoRepository repository;
//
//    @BeforeEach
//    void setUp() {
//        repository = new ToDoRepository(jdbcTemplate);
//    }
//
//    @Test
//    @DisplayName("Тестирует создание и поиск объекта по id!")
//    void testCreateAndFindById() {
//        ToDo todo = new ToDo();
//        todo.setTitle("Test Task");
//        todo.setDescription("Description");
//        todo.setCompleted(false);
//
//        ToDo created = repository.create(todo);
//        assertThat(created.getId()).isNotNull();
//        assertThat(created.getTitle()).isEqualTo("Test Task");
//
//        ToDo found = repository.findById(created.getId());
//        assertThat(found.getTitle()).isEqualTo("Test Task");
//        assertThat(found.getCompleted()).isFalse();
//    }
//
//
//    @Test
//    @DisplayName("Тестирует обновление задачи в по id!")
//    void testUpdate() {
//        ToDo todo = repository.create(new ToDo(null, "Old Title", "Old Desc", false, null, null));
//        todo.setTitle("New Title");
//        todo.setCompleted(true);
//
//        ToDo updated = repository.update(todo.getId(), todo);
//        assertThat(updated.getTitle()).isEqualTo("New Title");
//        assertThat(updated.getCompleted()).isTrue();
//    }
//
//}