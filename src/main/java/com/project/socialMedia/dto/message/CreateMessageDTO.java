package com.project.socialMedia.dto.message;

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
public class CreateMessageDTO {

    @NotBlank(message = "Message can't be empty")
    @Size(min = 1, max = 300, message = "Message length should be between 1 and 300 symbols")
    private String text;
}
