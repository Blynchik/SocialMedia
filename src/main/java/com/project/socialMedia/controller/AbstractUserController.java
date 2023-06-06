package com.project.socialMedia.controller;

import com.project.socialMedia.dto.CreateAppUserDTO;
import com.project.socialMedia.dto.ResponseAppUserDTO;
import com.project.socialMedia.exception.AppUserNotFoundException;
import com.project.socialMedia.model.user.AppUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.util.Converter;
import com.project.socialMedia.validator.AppUserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AbstractUserController {

    protected final AppUserService appUserService;
    protected final AppUserValidator appUserValidator;

    public AbstractUserController(AppUserService appUserService,
                                  AppUserValidator appUserValidator) {
        this.appUserService = appUserService;
        this.appUserValidator = appUserValidator;
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

    public ResponseEntity<?> create(CreateAppUserDTO appUserDTO,
                                    BindingResult bindingResult) {

        appUserValidator.validate(appUserDTO, bindingResult);

        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    bindingResult.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            );
        }

        appUserService.create(
                Converter.getAppUser(appUserDTO)
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> update(Long id,
                                    CreateAppUserDTO userDTO,
                                    BindingResult bindingResult) {

        appUserValidator.validate(userDTO, bindingResult);

        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    bindingResult.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            );
        }

        appUserService.update(id, Converter.getAppUser(userDTO));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        checkUserExistence(id);

        appUserService.delete(id);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<HttpStatus> logout(HttpServletRequest request,
                                             HttpServletResponse response) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    protected void checkUserExistence(Optional<AppUser> user, Long id) {
        if (user.isEmpty()) {
            throw new AppUserNotFoundException(id);
        }
    }

    protected void checkUserExistence(Long id) {
        Optional<AppUser> user = appUserService.getById(id);

        if (user.isEmpty()) {
            throw new AppUserNotFoundException(id);
        }
    }
}
