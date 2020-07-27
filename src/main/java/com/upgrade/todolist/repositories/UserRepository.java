package com.upgrade.todolist.repositories;

import com.upgrade.todolist.domain.User;
import com.upgrade.todolist.exceptions.TodoAuthException;

public interface UserRepository {
    Integer addUser(String name, String email, String password) throws TodoAuthException;

    User findByEmailAndPassword(String email, String password) throws TodoAuthException;

    Integer getCountByEmail(String email);

    User findByID(Integer userID);

    void changePassword(String email, String newPassword);

    void deleteUserByID(Integer userID);
}
