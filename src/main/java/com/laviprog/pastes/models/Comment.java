package com.laviprog.pastes.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comments")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
    @SequenceGenerator(name = "comment_id_seq", sequenceName = "comment_id_seq", allocationSize = 1)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "paste_id", nullable = false)
    private Paste paste;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(String body, String author, Paste paste, User user) {
        this.body = body;
        this.author = author;
        this.paste = paste;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }
}
