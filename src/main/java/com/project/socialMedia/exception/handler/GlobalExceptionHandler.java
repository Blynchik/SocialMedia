package com.project.socialMedia.exception.handler;

import com.project.socialMedia.exception.AppUserNotFoundException;
import com.project.socialMedia.exception.ForbiddenActionException;
import com.project.socialMedia.exception.FriendRequestNotFoundException;
import com.project.socialMedia.exception.PostNotFoundException;
import com.project.socialMedia.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(FriendRequestNotFoundException e){
        ExceptionResponse response= new ExceptionResponse(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(AppUserNotFoundException e){
        ExceptionResponse response= new ExceptionResponse(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(PostNotFoundException e){
        ExceptionResponse response= new ExceptionResponse(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(ForbiddenActionException e){
        ExceptionResponse response= new ExceptionResponse(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(IOException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
