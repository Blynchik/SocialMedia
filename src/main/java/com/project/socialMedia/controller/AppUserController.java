package com.project.socialMedia.controller;

import com.project.socialMedia.model.AppUser;
import com.project.socialMedia.model.AuthUser;
import com.project.socialMedia.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AppUserController extends AbstractUserController {

    @Autowired
    public AppUserController(AppUserService appUserService){
        super(appUserService);
    }

    @GetMapping("/getOwn")
    public ResponseEntity<AppUser> getOwn(@AuthenticationPrincipal AuthUser authUser) {
        return super.getById(authUser.id());
    }

    @DeleteMapping("/deleteOwn")
    public ResponseEntity<AppUser> deleteOwn(@AuthenticationPrincipal AuthUser authUser) {
        return super.deleteById(authUser.id());
    }

    @PutMapping("/editOwn")
    public ResponseEntity<?> updateOwn(@AuthenticationPrincipal AuthUser authUser,
                                       @Valid @RequestBody AppUser updatedAppUser,
                                       BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    bindingResult.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            );
        }

        return super.update(authUser.id(), updatedAppUser);
    }
}
