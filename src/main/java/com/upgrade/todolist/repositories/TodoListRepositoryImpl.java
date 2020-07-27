package com.upgrade.todolist.repositories;

import com.upgrade.todolist.domain.TodoList;
import com.upgrade.todolist.exceptions.TLBadRequestException;
import com.upgrade.todolist.exceptions.TLResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TodoListRepositoryImpl implements TodoListRepository {

    private static final String SQL_INSERT = "INSERT INTO todolist(todolist_id, uid, title, notes) VALUES(NEXTVAL('todolist_seq'), ?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT todolist_id, uid, title, notes FROM todolist WHERE uid = ? AND todolist_id = ?";
    private static final String SQL_FIND_ALL = "SELECT todolist_id, uid, title, notes FROM todolist WHERE uid = ?";
    private static final String SQL_UPDATE = "UPDATE todolist SET title = ?, notes = ? WHERE uid = ? AND todolist_id = ?";
    private static final String SQL_DELETE = "DELETE FROM todolist WHERE uid = ? AND todolist_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<TodoList> findAll(Integer userID) throws TLResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userID}, todoListRowMapper);
    }

    @Override
    public TodoList findByID(Integer userID, Integer todoListID) throws TLResourceNotFoundException {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userID, todoListID}, todoListRowMapper);
        }catch (Exception e) {
            throw new TLResourceNotFoundException("TodoList not found!!!");
        }
    }

    @Override
    public Integer addTodoList(Integer userID, String title, String notes) throws TLBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userID);
                ps.setString(2, title);
                ps.setString(3, notes);
                return ps;

            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("todolist_id");
        }catch (Exception e) {
            throw new TLBadRequestException("Invalid request!!!");
        }
    }

    @Override
    public void update(Integer userID, Integer todoListID, TodoList todoList) throws TLBadRequestException {
        try{
            jdbcTemplate.update(SQL_UPDATE, new Object[]{todoList.getTitle(), todoList.getNotes(), userID, todoListID});
        }catch (Exception e) {
            throw new TLBadRequestException("Invalid request!!!");
        }
    }

    @Override
    public void deleteTodoListByID(Integer userID, Integer todoListID) throws TLResourceNotFoundException{
        int count = jdbcTemplate.update(SQL_DELETE, new Object[]{userID, todoListID});
        if(count == 0)
            throw new TLResourceNotFoundException("TodoList not found!!!");
    }

    private RowMapper<TodoList> todoListRowMapper = ((rs, rowNum) -> {
        return new TodoList(rs.getInt("todolist_id"),
                rs.getInt("uid"),
                rs.getString("title"),
                rs.getString("notes"));
    });
}
