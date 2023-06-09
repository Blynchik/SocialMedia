package com.project.socialMedia.dto.post;

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
public class CreatePostDTO {

    @Size(max = 100, message = "Header length should be less then 101 symbol")
    private String header;

    @NotBlank(message = "Text should not be empty")
    @Size(min = 1, max = 5000, message = "Text should be between 1 and 5000 symbols")
    private String text;

    private String pathToImage;
}
