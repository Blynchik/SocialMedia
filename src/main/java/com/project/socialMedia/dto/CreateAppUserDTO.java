package com.project.socialMedia.dto;

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

    @NotBlank(message = "Should not be empty")
    @Size(min = 2, max = 255, message = "Should be more then 2 and less then 255 symbols")
    private String name;

    @NotBlank(message = "Should not be empty")
    @Size(max = 255, message = "Should be less then 255 symbols")
    @Email(message = "Enter your email")
    private String email;

    @NotBlank(message = "Should not be empty")
    @Size(min = 8, max = 255, message = "Should be more then 8 and less then 255 symbols")
    private String password;
}
