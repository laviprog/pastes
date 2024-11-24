package com.laviprog.pastes.repositories;

import com.laviprog.pastes.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {

}
