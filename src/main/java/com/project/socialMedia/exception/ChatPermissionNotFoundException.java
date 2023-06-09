package com.project.socialMedia.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChatPermissionNotFoundException extends RuntimeException{

    public ChatPermissionNotFoundException(Long id) {
        super("Permission id:" + id + " not found");
    }
}
