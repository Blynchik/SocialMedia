package com.project.socialMedia.model.post;

import com.project.socialMedia.model.user.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "header")
    @Size(max = 100, message = "Header length should be less then 101 symbols")
    private String header;

    @Column(name = "text", nullable = false)
    @NotBlank(message = "Text should not be empty")
    @Size(min = 1, max = 5000, message = "Text should be between 1 and 5000 symbols")
    private String text;

    @Column(name = "image")
    @Lob
    private byte[] imageAsByte;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private AppUser owner;

    @Column(name = "type")
    private String type;

    @Column(name = "byte_size")
    private Integer size = imageAsByte.length;
}
