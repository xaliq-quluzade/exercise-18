package com.example.exercise18.repository.impl;

import com.example.exercise18.model.Task;
import com.example.exercise18.repository.TaskRepository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private JdbcTemplate jdbcTemplate;

    public TaskRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Task task) {
        String sql = "INSERT INTO task (name, description) VALUES (?, ?)";
        jdbcTemplate.update(sql,task.getName(),task.getDescription());
    }

    @Override
    public void saveBatch(List<Task> tasks) {
        String sql = "INSERT INTO task (name, description) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1,tasks.get(i).getName());
                ps.setString(2,tasks.get(i).getDescription());
            }

            @Override
            public int getBatchSize() {
                return tasks.size();
            }
        });
    }

    @Override
    public void update(Task task) {
        String sql = "UPDATE task SET name = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql, task.getName(), task.getDescription(), task.getId());
    }

    @Override
    public Optional<Task> findById(Long id) {
        String sql = "SELECT * FROM task WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> Optional.ofNullable(
                        new Task(rs.getString("name"),rs.getString("description")))
                ,id);
    }

    @Override
    public List<Task> findAll() {
        String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        new Task(rs.getString("name"),rs.getString("description"))
                );
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM task WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
