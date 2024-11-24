package com.laviprog.pastes.storage;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface Storage {
    String getFileUrl(String bucket, String key);

    void uploadFile(String bucket, String key, InputStream input, ObjectMetadata metadata);

    void deleteFile(String bucket, String key);
}
