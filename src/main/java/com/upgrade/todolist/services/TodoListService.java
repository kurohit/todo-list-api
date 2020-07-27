package com.upgrade.todolist.services;

import com.upgrade.todolist.domain.TodoList;
import com.upgrade.todolist.exceptions.TLBadRequestException;
import com.upgrade.todolist.exceptions.TLResourceNotFoundException;

import java.util.List;

public interface TodoListService {
    List<TodoList> fetchAllTodoLists(Integer userID);

    TodoList fetchTodoListByID(Integer userID, Integer todoListID) throws TLResourceNotFoundException;

    TodoList addTodoList(Integer userID, String title, String notes) throws TLBadRequestException;

    void updateTodoList(Integer userID, Integer todoListID, TodoList todoList) throws TLBadRequestException;

    void removeTodoList(Integer userID, Integer todoListID) throws TLResourceNotFoundException;

}
