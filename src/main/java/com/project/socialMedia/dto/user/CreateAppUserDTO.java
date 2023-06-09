package com.project.socialMedia.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppUserDTO {

    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 255, message = "Name length should be more then 2 and less then 255 symbols")
    private String name;

    @NotBlank(message = "Email should not be empty")
    @Size(max = 255, message = "Email length should be less then 255 symbols")
    @Email(message = "Enter your email")
    private String email;

    @NotBlank(message = "Password should not be empty")
    @Size(min = 8, max = 255, message = "Password length should be more then 8 and less then 255 symbols")
    private String password;
}
