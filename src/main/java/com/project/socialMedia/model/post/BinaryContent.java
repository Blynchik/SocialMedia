package com.project.socialMedia.model.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "binary_content")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BinaryContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] imgAsBytes;
    private String type;
}
