package com.upgrade.todolist.domain;

public class TodoList {
    private Integer todolist_id;
    private Integer uid;
    private String title;
    private String notes;

    public TodoList(Integer todolist_id, Integer uid, String title, String notes) {
        this.todolist_id = todolist_id;
        this.uid = uid;
        this.title = title;
        this.notes = notes;
    }

    public Integer getTodolist_id() {
        return todolist_id;
    }

    public void setTodolist_id(Integer todolist_id) {
        this.todolist_id = todolist_id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
