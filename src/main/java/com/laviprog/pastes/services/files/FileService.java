package com.laviprog.pastes.services.files;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.laviprog.pastes.models.FileEntity;
import com.laviprog.pastes.models.Paste;
import com.laviprog.pastes.repositories.FileEntityRepository;
import com.laviprog.pastes.storage.StorageService;
import com.laviprog.pastes.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileEntityRepository repository;
    private final StorageService storageService;
    private final String bucketDefault;

    public FileEntity createFile(MultipartFile file, Paste paste) {
        String filename = file.getOriginalFilename();
        String key = FileUtils.generateFilenameWithUUID(Objects.requireNonNull(filename));
        String url = storageService.getFileUrl(bucketDefault, key);

        FileEntity fileEntity = new FileEntity(filename, paste, bucketDefault, key, url);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        try {
            storageService.uploadFile(bucketDefault, key, file.getInputStream(), objectMetadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return repository.save(fileEntity);
    }

    public void deleteFile(FileEntity fileEntity) {
        repository.delete(fileEntity);
    }

}
