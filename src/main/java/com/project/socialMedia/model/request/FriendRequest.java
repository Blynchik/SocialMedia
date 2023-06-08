package com.project.socialMedia.model.request;

import com.project.socialMedia.model.user.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FriendRequest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    private AppUser initiator;

    @Enumerated(EnumType.STRING)
    private Status initiatorStatus;

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "id")
    private AppUser target;

    @Enumerated(EnumType.STRING)
    private Status targetStatus;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt = LocalDateTime.now();
}
