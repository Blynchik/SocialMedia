package com.project.socialMedia.model.user;

import com.project.socialMedia.model.message.Message;
import com.project.socialMedia.model.permission.ChatPermission;
import com.project.socialMedia.model.post.Post;
import com.project.socialMedia.model.request.FriendRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AppUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 255, message = "Name length should be more then 2 and less then 255 symbols")
    private String name;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email should not be empty")
    @Size(max = 255, message = "Email length should be less then 255 symbols")
    @Email(message = "Enter your email")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password should not be empty")
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

    @OneToMany(mappedBy = "sender")
    private List<Message> outgoing;

    @OneToMany(mappedBy = "recipient")
    private List<Message> incoming;

    @OneToMany(mappedBy = "owner")
    private List<Post> posts;

    @OneToMany(mappedBy = "initiator")
    private List<FriendRequest> myRequests;

    @OneToMany(mappedBy =  "target")
    private List<FriendRequest> otherRequests;
}
