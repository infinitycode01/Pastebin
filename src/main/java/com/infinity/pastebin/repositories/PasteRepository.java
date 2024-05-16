package com.infinity.pastebin.repositories;

import com.infinity.pastebin.models.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Long> {
}
