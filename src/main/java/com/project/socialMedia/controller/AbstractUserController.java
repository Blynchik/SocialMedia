package com.project.socialMedia.controller;

import com.project.socialMedia.dto.CreateAppUserDTO;
import com.project.socialMedia.dto.ResponseAppUserDTO;
import com.project.socialMedia.model.AppUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.util.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AbstractUserController {

    protected AppUserService appUserService;

    public AbstractUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    public ResponseEntity<ResponseAppUserDTO> getById(Long id) {
        Optional<AppUser> user = appUserService.getById(id);

        checkUserExistence(user, id);

        return ResponseEntity.ok().body(Converter.getAppUserDTO(user.get()));
    }

    public ResponseEntity<List<ResponseAppUserDTO>> getAll() {
        return ResponseEntity.ok(
                appUserService.getAll().stream()
                .map(Converter::getAppUserDTO)
                .collect(Collectors.toList())
        );
    }

    public ResponseEntity<?> create(CreateAppUserDTO userDTO) {

        appUserService.create(
                Converter.getAppUser(userDTO)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Long id, CreateAppUserDTO userDTO) {
        appUserService.update(id, Converter.getAppUser(userDTO));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
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
