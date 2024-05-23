package com.infinity.pastebin.services;

import com.infinity.pastebin.models.Paste;
import com.infinity.pastebin.repositories.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PasteService {
    private final PasteRepository pasteRepository;

    @Autowired
    public PasteService(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    @Transactional
    public void save(Paste paste) {
        pasteRepository.save(paste);
    }

    @Transactional
    public void delete(Paste paste) {
        pasteRepository.delete(paste);
    }

    // TODO
    // In find method to add a exception (custom) handling
}
