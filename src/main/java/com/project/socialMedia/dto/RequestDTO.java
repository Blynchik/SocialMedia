package com.project.socialMedia.dto;

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
public class RequestDTO {

    private Long id;

    private ResponseAppUserDTO initiator;

    private ResponseAppUserDTO target;

    private LocalDateTime createdAt;
}
