package com.infinity.pastebin.repositories;

import com.infinity.pastebin.models.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Long> {
    Paste findByKey(String key);
    List<Paste> findByCreatedWho(Long id);
}