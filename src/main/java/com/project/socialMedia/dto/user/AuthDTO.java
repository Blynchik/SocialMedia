package com.project.socialMedia.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {

    @NotBlank(message = "Email should not be empty")
    @Size(max = 255, message = "Email length should be less then 255 symbols")
    @Email(message = "Enter your email")
    private String email;

    @NotBlank(message = "Password should not be empty")
    @Size(min = 8, max = 255, message = "Password length should be more then 8 and less then 255 symbols")
    private String password;
}
