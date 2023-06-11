package com.project.socialMedia.controller.auth;

import com.project.socialMedia.controller.user.AbstractUserController;
import com.project.socialMedia.dto.user.AuthDTO;
import com.project.socialMedia.dto.user.CreateAppUserDTO;
import com.project.socialMedia.exception.response.ExceptionResponse;
import com.project.socialMedia.util.JWTUtil;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.service.FriendRequestService;
import com.project.socialMedia.util.Converter;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
                                         AuthenticationManager authenticationManager) {
        super(appUserService, friendRequestService, appUserValidator);
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

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

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<?> performLogin(@RequestBody AuthDTO authDTO) {

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDTO.getEmail().toLowerCase(),
                        authDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new ExceptionResponse("Incorrect credentials", LocalDateTime.now()));
        }

        String token = jwtUtil.generateToken(authDTO.getEmail());
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> performLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Successfully logged out.");
    }
}
