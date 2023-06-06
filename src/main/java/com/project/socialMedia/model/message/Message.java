package com.project.socialMedia.model.message;

import com.project.socialMedia.model.user.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Message {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "message", nullable = false)
    @NotBlank(message = "Message can't be empty")
    @Size(min = 1, max = 300, message = "Message length should be between 1 and 300 symbols")
    private String text;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private AppUser sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private AppUser recipient;
}
