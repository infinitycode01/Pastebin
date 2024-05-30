package com.infinity.pastebin.services.impl;

import com.infinity.pastebin.exceptions.PasteNotFoundException;
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

    public Paste findById(Long id) {
        return pasteRepository.findById(id).orElseThrow(PasteNotFoundException::new);
    }

    @Transactional
    public void delete(Paste paste) {
        pasteRepository.delete(paste);
    }

    // TODO
    // In find method to add a exception (custom) handling


}
