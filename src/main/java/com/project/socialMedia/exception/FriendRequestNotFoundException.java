package com.project.socialMedia.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FriendRequestNotFoundException extends RuntimeException{

    public FriendRequestNotFoundException(Long id) {
        super("Request id:" + id + " not found");
    }

    public FriendRequestNotFoundException(String message) {
        super(message);
    }
}
