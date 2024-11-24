package com.laviprog.pastes.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "views")
@NoArgsConstructor
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "view_id_seq")
    @SequenceGenerator(name = "view_id_seq", sequenceName = "view_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "paste_id", nullable = false)
    private Long pasteId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "viewed_at", nullable = false)
    private LocalDateTime viewedAt;

    public View(Long pasteId, Long userId) {
        this.pasteId = pasteId;
        this.userId = userId;
        this.viewedAt = LocalDateTime.now();
    }
}

