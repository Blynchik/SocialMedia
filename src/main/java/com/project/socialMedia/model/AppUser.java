package com.project.socialMedia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Should not be empty")
    @Size(min = 2, max = 255, message = "Should be more then 2 and less then 255 symbols")
    private String name;

    @Column(name = "email")
    @NotBlank(message = "Should not be empty")
    @Size(max = 255, message = "Should be less then 255 symbols")
    @Email(message = "Enter your email")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Should not be empty")
    @Size(min = 8, max = 255, message = "Should be more then 8 and less then 255 symbols")
    private String password;

    @Column(name = "role")
    @CollectionTable(name = "role",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role"))
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
}
