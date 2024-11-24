package com.laviprog.pastes.models;

import com.laviprog.pastes.dto.pastes.CreatePasteRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "pastes")
@NoArgsConstructor
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paste_id_seq")
    @SequenceGenerator(name = "paste_id_seq", sequenceName = "paste_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body")
    private String body;

    @ElementCollection
    private List<String> categories;

    @ElementCollection
    private List<String> tags;

    @Column(name = "format")
    private String format;

    @Column(name = "number_views")
    private Long views;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "paste", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<FileEntity> files;

    @Column(name = "author")
    private String author;

    @OneToMany(mappedBy = "paste", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Paste(CreatePasteRequest pasteRequest, User user) {
        this.title = pasteRequest.getTitle();
        this.body = pasteRequest.getBody();
        this.categories = pasteRequest.getCategories();
        this.tags = pasteRequest.getTags();
        this.format = pasteRequest.getFormat();
        this.author = user.getUsername();
        this.views = 0L;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.user = user;
    }

    public void incrementNumberViews() {
        this.views += 1;
    }
}