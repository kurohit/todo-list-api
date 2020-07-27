package com.upgrade.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TodoAuthException extends RuntimeException {

    public TodoAuthException(String message) {
        super(message);
    }
}
