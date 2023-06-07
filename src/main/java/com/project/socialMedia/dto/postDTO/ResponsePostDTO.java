package com.project.socialMedia.dto.postDTO;

import com.project.socialMedia.dto.userDTO.ResponseAppUserDTO;
import com.project.socialMedia.model.post.BinaryContent;
import com.project.socialMedia.model.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;

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

    private ResponseAppUserDTO owner;

    private MediaType mediaType;

    private byte[] imgAsBytes;


}
