package com.upgrade.todolist.services;

import com.upgrade.todolist.domain.User;
import com.upgrade.todolist.exceptions.TodoAuthException;

public interface UserService {
    User validateUser(String email, String password) throws TodoAuthException;

    User registerUser(String name, String email, String password) throws TodoAuthException;

    void changePassword(String email, String currentPassword, String newPassword);

}
