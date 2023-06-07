package com.project.socialMedia.dto.postDTO;

import com.project.socialMedia.model.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostDTO {

    private Long id;

    private LocalDateTime createdAt;

    private String header;

    private String text;

    private AppUser owner;

    private BufferedImage img;
}
