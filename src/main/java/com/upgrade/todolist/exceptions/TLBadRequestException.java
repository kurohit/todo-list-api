package com.upgrade.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TLBadRequestException extends RuntimeException {

    public TLBadRequestException(String message) {
        super(message);
    }

}
