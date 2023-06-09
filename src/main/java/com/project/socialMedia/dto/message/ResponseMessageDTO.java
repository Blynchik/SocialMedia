package com.project.socialMedia.dto.message;

import com.project.socialMedia.dto.user.ResponseAppUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageDTO {

    private Long id;

    private ResponseAppUserDTO sender;

    private ResponseAppUserDTO recipient;

    private String text;

    private LocalDateTime createdAt;
}
