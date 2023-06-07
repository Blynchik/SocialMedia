package com.project.socialMedia.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException(Long id) {
        super("Post id:" + id + " not found");
    }
}
