package com.project.socialMedia.model.permission;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_permission")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatPermission {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "u1")
    private Long u1;

    @NotNull
    @Column(name = "u2")
    private Long u2;

    @Enumerated(EnumType.STRING)
    private PermissionStatus status;
}
