package com.project.socialMedia.dto.userDTO;

import com.project.socialMedia.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAppUserDTO {

    private Long id;

    private String name;

    private String email;

    private Set<Role> roles;

}
