package com.project.socialMedia.controller;

import com.project.socialMedia.model.AppUser;
import com.project.socialMedia.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class AbstractUserController {

    protected AppUserService appUserService;

    public AbstractUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    public ResponseEntity<AppUser> getById(Long id) {
        Optional<AppUser> user = appUserService.getById(id);

        checkUserExistence(user, id);

        return ResponseEntity.ok().body(user.get());
    }

    public ResponseEntity<List<AppUser>> getAll(){
        return ResponseEntity.ok(appUserService.getAll());
    }

    public ResponseEntity<?> create(AppUser appUser){

        appUserService.create(appUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Long id, AppUser updatedAppUser){
        appUserService.update(id, updatedAppUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<AppUser> deleteById(Long id) {
        checkUserExistence(id);

        appUserService.delete(id);

        return ResponseEntity.noContent().build();
    }

    protected void checkUserExistence(Optional<AppUser> user, Long id) {
        if (user.isEmpty()) {
            throw new RuntimeException("User id:" + id + " not found"); //TODO Custom exception
        }
    }

    protected void checkUserExistence(Long id) {
        Optional<AppUser> user = appUserService.getById(id);

        if (user.isEmpty()) {
            throw new RuntimeException("User id:" + id + " not found"); //TODO Custom exception
        }
    }
}
