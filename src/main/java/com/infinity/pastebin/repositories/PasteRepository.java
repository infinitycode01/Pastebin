package com.infinity.pastebin.repositories;

import com.infinity.pastebin.models.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Long> {
    Optional<Paste> findByKey(String key);
    Boolean existsByKey(String key);
}