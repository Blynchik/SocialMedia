package com.project.socialMedia.model;

import com.project.socialMedia.model.user.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "message")
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
