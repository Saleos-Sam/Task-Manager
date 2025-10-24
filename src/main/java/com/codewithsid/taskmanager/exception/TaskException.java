package com.codewithsid.taskmanager.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TaskException extends RuntimeException {
    private final HttpStatus status;

    public TaskException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static TaskException notFound(String message) {
        return new TaskException(message, HttpStatus.NOT_FOUND);
    }

    public static TaskException badRequest(String message) {
        return new TaskException(message, HttpStatus.BAD_REQUEST);
    }

    public static TaskException conflict(String message) {
        return new TaskException(message, HttpStatus.CONFLICT);
    }

    public static TaskException forbidden(String message) {
        return new TaskException(message, HttpStatus.FORBIDDEN);
    }
}
