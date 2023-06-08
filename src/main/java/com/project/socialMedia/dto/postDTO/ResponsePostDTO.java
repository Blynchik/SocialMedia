package com.project.socialMedia.dto.postDTO;

import com.project.socialMedia.dto.userDTO.ResponseAppUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String type;

    private byte[] imgAsBytes;


}
