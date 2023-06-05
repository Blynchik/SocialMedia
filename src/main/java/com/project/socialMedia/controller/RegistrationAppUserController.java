package com.project.socialMedia.controller;

import com.project.socialMedia.model.AppUser;
import com.project.socialMedia.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
public class RegistrationAppUserController extends AbstractUserController{

    @Autowired
    public RegistrationAppUserController(AppUserService appUserService){
        super(appUserService);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AppUser appUser,
                                    BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    bindingResult.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            );
        }

        return super.create(appUser);
    }
}
