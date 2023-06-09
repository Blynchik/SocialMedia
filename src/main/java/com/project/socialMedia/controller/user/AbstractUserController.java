package com.project.socialMedia.controller.user;

import com.project.socialMedia.dto.user.CreateAppUserDTO;
import com.project.socialMedia.dto.user.ResponseAppUserDTO;
import com.project.socialMedia.exception.AppUserNotFoundException;
import com.project.socialMedia.model.user.AppUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.FriendRequestService;
import com.project.socialMedia.util.Converter;
import com.project.socialMedia.validator.AppUserValidator;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractUserController {

    protected final AppUserService appUserService;
    protected final FriendRequestService friendRequestService;
    protected final AppUserValidator appUserValidator;

    public AbstractUserController(AppUserService appUserService,
                                  FriendRequestService friendRequestService,
                                  AppUserValidator appUserValidator) {
        this.appUserService = appUserService;
        this.appUserValidator = appUserValidator;
        this.friendRequestService = friendRequestService;
    }

    public ResponseEntity<ResponseAppUserDTO> getById(Long id) {
        Optional<AppUser> user = appUserService.getById(id);

        checkUserExistence(id);

        return ResponseEntity.ok().body(Converter.getAppUserDTO(user.get()));
    }

    public ResponseEntity<List<ResponseAppUserDTO>> getAll() {
        return ResponseEntity.ok(
                appUserService.getAll().stream()
                .map(Converter::getAppUserDTO)
                .collect(Collectors.toList())
        );
    }

    public ResponseEntity<List<ResponseAppUserDTO>> getFriends(Long userId) {
        checkUserExistence(userId);

        List<ResponseAppUserDTO> friends = friendRequestService.getFriends(userId).stream()
                .map(Converter::getAppUserDTO).toList();

        return ResponseEntity.ok(friends);
    }

    public ResponseEntity<List<ResponseAppUserDTO>> getSubscribers(Long userId) {
        checkUserExistence(userId);

        List<ResponseAppUserDTO> subscribers = friendRequestService.getSubscribers(userId).stream()
                .map(Converter::getAppUserDTO).toList();

        return ResponseEntity.ok(subscribers);
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

    public ResponseEntity<?> edit(Long id,
                                    CreateAppUserDTO userDTO,
                                    BindingResult bindingResult) {

        appUserValidator.validate(userDTO, bindingResult);

        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    bindingResult.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            );
        }

        appUserService.edit(id, Converter.getAppUser(userDTO));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        checkUserExistence(id);

        appUserService.delete(id);

        return ResponseEntity.noContent().build();
    }

    protected void checkUserExistence(Long id) {
        if (!appUserService.checkExistence(id)) {
            throw new AppUserNotFoundException(id);
        }
    }
}
