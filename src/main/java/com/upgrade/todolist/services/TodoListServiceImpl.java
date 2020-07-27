package com.upgrade.todolist.services;

import com.upgrade.todolist.domain.TodoList;
import com.upgrade.todolist.exceptions.TLBadRequestException;
import com.upgrade.todolist.exceptions.TLResourceNotFoundException;
import com.upgrade.todolist.repositories.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TodoListServiceImpl implements TodoListService {

    @Autowired
    TodoListRepository todoListRepository;

    @Override
    public List<TodoList> fetchAllTodoLists(Integer userID) {
        return todoListRepository.findAll(userID);
    }

    @Override
    public TodoList fetchTodoListByID(Integer userID, Integer todoListID) throws TLResourceNotFoundException {
        return todoListRepository.findByID(userID, todoListID);
    }

    @Override
    public TodoList addTodoList(Integer userID, String title, String notes) throws TLBadRequestException {
        int todoListID = todoListRepository.addTodoList(userID, title, notes);
        return todoListRepository.findByID(userID, todoListID);
    }

    @Override
    public void updateTodoList(Integer userID, Integer todoListID, TodoList todoList) throws TLBadRequestException {
        TodoList currentTodoList = todoListRepository.findByID(userID, todoListID);
        if(currentTodoList != null)
            todoListRepository.update(userID, todoListID, todoList);
        else
            throw new TLResourceNotFoundException("Invalid todolist_id!!!");
    }

    @Override
    public void removeTodoList(Integer userID, Integer todoListID) throws TLResourceNotFoundException {
        todoListRepository.deleteTodoListByID(userID, todoListID);
    }
}
