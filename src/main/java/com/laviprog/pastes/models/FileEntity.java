package com.laviprog.pastes.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "files")
@NoArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_id_seq")
    @SequenceGenerator(name = "file_id_seq", sequenceName = "file_id_seq", allocationSize = 1)
    @Column(name = "file_id")
    private Long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "bucket")
    private String bucket;

    @Column(name = "key")
    private String key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paste_id", nullable = false)
    private Paste paste;

    @Column(name = "url")
    private String url;

    public FileEntity(String filename, Paste paste, String bucket, String key, String url) {
        this.filename = filename;
        this.bucket = bucket;
        this.paste = paste;
        this.key = key;
        this.url = url;
    }
}
