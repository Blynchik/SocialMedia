package com.project.socialMedia.controller.user;

import com.project.socialMedia.dto.userDTO.CreateAppUserDTO;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.validator.AppUserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
public class RegistrationAppUserController extends AbstractUserController {

    @Autowired
    public RegistrationAppUserController(AppUserService appUserService,
                                         AppUserValidator appUserValidator){
        super(appUserService, appUserValidator);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateAppUserDTO appUserDTO,
                                    BindingResult bindingResult) {

        return super.create(appUserDTO, bindingResult);
    }
}
