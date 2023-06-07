package com.project.socialMedia.exception;

public class ForbiddenActionException extends RuntimeException{

    public ForbiddenActionException() {
        super("Forbidden action");
    }
}
