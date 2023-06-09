package com.project.socialMedia.controller.user;

import com.project.socialMedia.dto.user.CreateAppUserDTO;
import com.project.socialMedia.dto.user.ResponseAppUserDTO;
import com.project.socialMedia.model.user.AuthUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.ChatPermissionService;
import com.project.socialMedia.service.FriendRequestService;
import com.project.socialMedia.validator.AppUserValidator;
import io.swagger.v3.oas.annotations.Operation;
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
                             AppUserValidator appUserValidator,
                             ChatPermissionService chatPermissionService) {
        super(appUserService, friendRequestService, appUserValidator, chatPermissionService);
    }

    @Operation(summary = "Метод возвращает id, имя, почту и роль пользователя. " +
            "Если пользователя не существует NOT_FOUND " +
            "Ответ OK.")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseAppUserDTO> getById(@PathVariable Long id) {
        return super.getById(id);
    }


    @Operation(summary = "Метод возвращает список друзей пользователя или пустой список. " +
            "Ответ OK.")
    @GetMapping("/friends")
    public ResponseEntity<List<ResponseAppUserDTO>> getFriends(@AuthenticationPrincipal AuthUser authUser) {
        return super.getFriends(authUser.id());
    }

    @Operation(summary = "Метод возвращает список подписчиков пользователя. " +
            "Ответ OK.")
    @GetMapping("/subscribers")
    public ResponseEntity<List<ResponseAppUserDTO>> getSubscribers(@AuthenticationPrincipal AuthUser authUser){
        return super.getSubscribers(authUser.id());
    }

    @Operation(summary = "Метод позволяет удалить информацию о себе. " +
            "Ответ NO_CONTENT.")
    @DeleteMapping("/deleteOwn")
    public ResponseEntity<HttpStatus> deleteOwn(@AuthenticationPrincipal AuthUser authUser) {
        return super.deleteById(authUser.id());
    }

    @Operation(summary = "Метод позволяет редактировать информаию о себе. " +
            "Поле name не может быть пустым или состоять из пустых значений. " +
            "Длина не должна быть меньше 2 и больше 255 символов. " +
            "Поле email не может быть пустым или состоять из пустых значений, должен быть уникальным. " +
            "Длина не должна быть больше 255 символов. " +
            "Должно соответствовать паттерну **@**.** " +
            "Поле password не может быть пустым или состоять из пустых значений. " +
            "Длина не должна быть меньше 8 и больше 255 символов. " +
            "Иначе вернется BAD_REQUEST.")
    @PutMapping("/editOwn")
    public ResponseEntity<?> editOwn(@AuthenticationPrincipal AuthUser authUser,
                                     @Valid @RequestBody CreateAppUserDTO userDTO,
                                     BindingResult bindingResult) {

        return super.edit(authUser.id(), userDTO, bindingResult);
    }
}
