package com.project.socialMedia.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(Long id) {
        super("User id:" + id + " not found");
    }

    public AppUserNotFoundException(String email) {
        super("User with email " + email + " not found");
    }
}
