package com.project.socialMedia.exception;

public class ForbiddenActionException extends RuntimeException{

    public ForbiddenActionException(String message) {
        super(message);
    }
}
