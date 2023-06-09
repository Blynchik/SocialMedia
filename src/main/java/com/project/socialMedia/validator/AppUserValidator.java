package com.project.socialMedia.validator;

import com.project.socialMedia.dto.user.CreateAppUserDTO;
import com.project.socialMedia.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AppUserValidator implements Validator {
    private final AppUserService appUserService;

    @Autowired
    public AppUserValidator(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateAppUserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateAppUserDTO userDTO = (CreateAppUserDTO) target;

        if (appUserService.getByEmail(userDTO.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Email already registered");
        }
    }
}
