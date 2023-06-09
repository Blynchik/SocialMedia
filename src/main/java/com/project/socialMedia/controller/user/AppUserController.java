package com.project.socialMedia.controller.user;

import com.project.socialMedia.dto.user.CreateAppUserDTO;
import com.project.socialMedia.dto.user.ResponseAppUserDTO;
import com.project.socialMedia.model.user.AuthUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.FriendRequestService;
import com.project.socialMedia.validator.AppUserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class AppUserController extends AbstractUserController {

    @Autowired
    public AppUserController(AppUserService appUserService,
                             FriendRequestService friendRequestService,
                             AppUserValidator appUserValidator) {
        super(appUserService, friendRequestService, appUserValidator);
    }

    @GetMapping("/getOwn")
    public ResponseEntity<ResponseAppUserDTO> getOwn(@AuthenticationPrincipal AuthUser authUser) {
        return super.getById(authUser.id());
    }

    @GetMapping("/friends")
    public ResponseEntity<List<ResponseAppUserDTO>> getFriends(@AuthenticationPrincipal AuthUser authUser) {
        return super.getFriends(authUser.id());
    }

    @GetMapping("/subscribers")
    public ResponseEntity<List<ResponseAppUserDTO>> getSubscribers(@AuthenticationPrincipal AuthUser authUser){
        return super.getSubscribers(authUser.id());
    }

    @DeleteMapping("/deleteOwn")
    public ResponseEntity<HttpStatus> deleteOwn(@AuthenticationPrincipal AuthUser authUser) {
        return super.deleteById(authUser.id());
    }

    @PutMapping("/editOwn")
    public ResponseEntity<?> editOwn(@AuthenticationPrincipal AuthUser authUser,
                                     @Valid @RequestBody CreateAppUserDTO userDTO,
                                     BindingResult bindingResult) {

        return super.edit(authUser.id(), userDTO, bindingResult);
    }
}
