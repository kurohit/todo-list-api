package com.upgrade.todolist.resources;

import com.upgrade.todolist.domain.TodoList;
import com.upgrade.todolist.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todolists")
public class TodoListResource {

    @Autowired
    TodoListService todoListService;

    @GetMapping("")
    public ResponseEntity<List<TodoList>> getAllTodoLists(HttpServletRequest request) {
        int userID = (Integer) request.getAttribute("userID");
        List<TodoList> todoLists = todoListService.fetchAllTodoLists(userID);
        return new ResponseEntity<>(todoLists, HttpStatus.OK);
    }

    @GetMapping("/{todoListID}")
    public ResponseEntity<TodoList> getTodoListByID(HttpServletRequest request, @PathVariable("todoListID") Integer todoListID) {
        int userID = (Integer) request.getAttribute("userID");
        TodoList todoList = todoListService.fetchTodoListByID(userID, todoListID);
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TodoList> addTodoList(HttpServletRequest request, @RequestBody Map<String, Object> todoListMap) {
        int userID = (Integer) request.getAttribute("userID");
        String title = (String) todoListMap.get("title");
        String notes = (String) todoListMap.get("notes");

        TodoList todoList = todoListService.addTodoList(userID, title, notes);
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    @PutMapping("/{todoListID}")
    public ResponseEntity<Map<String, Boolean>> updateTodoList(HttpServletRequest request, @PathVariable("todoListID") Integer todoListID,
                                                               @RequestBody TodoList todoList) {
        int userID = (Integer) request.getAttribute("userID");
        todoListService.updateTodoList(userID, todoListID, todoList);

        Map<String, Boolean> map = new HashMap<>();
        map.put("Success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{todoListID}")
    public ResponseEntity<Map<String, Boolean>> deleteTodoList(HttpServletRequest request, @PathVariable("todoListID") Integer todoListID) {
        return null;
    }
}
