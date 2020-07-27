package com.upgrade.todolist.repositories;

import com.upgrade.todolist.domain.TodoList;
import com.upgrade.todolist.exceptions.TLBadRequestException;
import com.upgrade.todolist.exceptions.TLResourceNotFoundException;

import java.util.List;

public interface TodoListRepository {

    List<TodoList> findAll(Integer userID) throws TLResourceNotFoundException;

    TodoList findByID(Integer userID, Integer todoListID) throws TLResourceNotFoundException;

    Integer addTodoList(Integer userID, String title, String notes) throws TLBadRequestException;

    void update(Integer userID, Integer todoListID, TodoList todoList) throws TLBadRequestException;

    void deleteTodoListByID(Integer userID, Integer todoListID) throws TLResourceNotFoundException;
}
