package com.laviprog.pastes.repositories;

import com.laviprog.pastes.models.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PasteRepository extends JpaRepository<Paste, Long> {
    @Query(value = "SELECT * FROM pastes ORDER BY pastes.number_views DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Paste> findTopPastes(@Param("limit") int limit, @Param("offset") int offset);

    Optional<Paste> findPasteByAuthorAndTitle(String author, String title);
}
