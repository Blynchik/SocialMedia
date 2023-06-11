package com.project.socialMedia.controller.auth;

import com.project.socialMedia.controller.user.AbstractUserController;
import com.project.socialMedia.dto.user.AuthDTO;
import com.project.socialMedia.dto.user.CreateAppUserDTO;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.ChatPermissionService;
import com.project.socialMedia.service.FriendRequestService;
import com.project.socialMedia.util.Converter;
import com.project.socialMedia.util.JWTUtil;
import com.project.socialMedia.validator.AppUserValidator;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegistrationAndAuthController extends AbstractUserController {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public RegistrationAndAuthController(AppUserService appUserService,
                                         FriendRequestService friendRequestService,
                                         AppUserValidator appUserValidator,
                                         JWTUtil jwtUtil,
                                         AuthenticationManager authenticationManager,
                                         ChatPermissionService chatPermissionService) {
        super(appUserService, friendRequestService, appUserValidator, chatPermissionService);
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Создает нового пользователя с помощью данных из объекта CreateAppUserDTO.
     * Проверяет валидность данных с помощью аннотаций в DTO классе,
     * а также проверяет уникальность email.
     * Возвращает сгенерированный JWT токен
     * в случае успешного создания пользователя и HTTP статусом CREATED.
     * В случае невалидных данных или дублирования email возвращает ResponseEntity
     * с HTTP статусом BAD_REQUEST и сообщениями о проблеме.
     * @param appUserDTO объект с данными пользователя.
     *                   Поле name не может быть пустым или состоять из пустых значений.
     *                   Длина не должна быть меньше 2 и больше 255 символов.
     *                   Поле email не может быть пустым или состоять из пустых значений, должен быть уникальным.
     *                   Длина не должна быть больше 255 символов.
     *                   Должно соответствовать паттерну **@**.**
     *                   Поле password не может быть пустым или состоять из пустых значений.
     *                   Длина не должна быть меньше 8 и больше 255 символов.
     *                   Иначе вернется BAD_REQUEST.
     */
    @PostMapping("/registration")
    @PermitAll
    public ResponseEntity<?> create(@Valid @RequestBody CreateAppUserDTO appUserDTO,
                                    BindingResult bindingResult) {

        appUserValidator.validate(appUserDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    bindingResult.getAllErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            );
        }

        appUserService.create(
                Converter.getAppUser(appUserDTO)
        );

        String token = jwtUtil.generateToken(appUserDTO.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    /**
     * Метод для аутентификации пользователя.
     * Регистрирует новую попытку входа в систему с использованием
     * предоставленных учётных данных.
     * Генерирует токен JSON Web Token (JWT) для пользователя,
     * чтобы использовать его в дальнейшей авторизации.
     * Возвращает успешный ответ OK с токеном для авторизации.
     * @param authDTO объект с данными пользователя.
     * Возвращает ошибку неверных учетных данных, если аутентификация не удалась.
     */
    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<?> performLogin(@RequestBody AuthDTO authDTO){

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDTO.getEmail().toLowerCase(),
                        authDTO.getPassword());

            authenticationManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(authDTO.getEmail());
        return ResponseEntity.ok().body(token);
    }

    /**
     * Метод контроллера "/logout" выполняет выход пользователя из системы.
     * При вызове этого метода происходит очистка текущей сессии пользователя
     * и убирание его аутентификационных данных из контекста безопасности.
     * В результате у пользователя будет сброшен статус авторизованного в системе.
     * Метод возвращает сообщение об успешном выходе пользователя из системы OK.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> performLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Successfully logged out.");
    }
}
