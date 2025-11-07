package com.emobile.springtodo.repository;

import com.emobile.springtodo.dto.ToDoDto;
import com.emobile.springtodo.model.ToDo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ToDoRepository {

    private final JdbcTemplate jdbcTemplate;

    private ToDo mapRow(ResultSet rs) throws SQLException {
        ToDo t = new ToDo();
        t.setId(rs.getLong("id"));
        t.setTitle(rs.getString("title"));
        t.setDescription(rs.getString("description"));
        t.setCompleted(rs.getBoolean("completed"));
        t.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        t.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return t;
    }



    public List<ToDoDto> findTodos(int limit, int offset) {
        String sql = "SELECT id, title, description, completed FROM todo ORDER BY id ASC LIMIT ? OFFSET ?";

        return jdbcTemplate.query(
                sql,
                ps -> {
                    ps.setInt(1, limit);
                    ps.setInt(2, offset);
                },
                (rs, rowNum) -> new ToDoDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("completed")
                )
        );
    }

    // Найти по ID
    public ToDo findById(Long id) {
        String sql = "SELECT * FROM todo WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(ToDo.class),
                id
        );
    }

    // Добавить задачу
    public ToDo create(ToDo dto) {

        String insertSql = """
                    INSERT INTO todo (title, description, completed, created_at, updated_at)
                    VALUES (?, ?, ?, NOW(), NOW())
                """;

        jdbcTemplate.update(
                insertSql,
                dto.getTitle(),
                dto.getDescription(),
                dto.getCompleted() != null && dto.getCompleted()
        );


        String selectSql = """
                    SELECT id, title, description, completed, created_at, updated_at
                    FROM todo
                    WHERE title = ?
                    ORDER BY created_at DESC
                    LIMIT 1
                """;

        List<ToDo> results = jdbcTemplate.query(
                selectSql,
                (rs, rowNum) -> mapRow(rs),
                dto.getTitle()
        );
        if (results == null || results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    // Обновить задачу
    public ToDo update(long id, ToDo entity) {
        String sql = """
                    UPDATE todo
                    SET title = ?, description = ?, completed = ?, updated_at = NOW()
                    WHERE id = ?
                    RETURNING *;
                """;

        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(ToDo.class),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCompleted(),
                id
        );
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM todo WHERE id = ?", id);
    }
}
