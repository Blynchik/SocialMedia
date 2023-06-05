package com.project.socialMedia.model;

import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.User;

public class AuthUser extends User {

    private final AppUser user;

    public AuthUser(@NonNull AppUser user){
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public Long id(){
        return user.getId();
    }

    public AppUser getUser(){
        return user;
    }
}
