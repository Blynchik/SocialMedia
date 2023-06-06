package com.project.socialMedia.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException(Class clazz, Long id) {
        super(clazz.getName()
                .substring(clazz.getName().lastIndexOf(".") + 1)
                + " id:" + id + " not found");
    }

    public AppUserNotFoundException(Class clazz, String email) {
        super(clazz.getName()
                .substring(clazz.getName().lastIndexOf(".") + 1)
        +" with email " + email + " not found");
    }
}
