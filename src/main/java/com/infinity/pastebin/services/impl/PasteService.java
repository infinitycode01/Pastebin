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

    public Paste findByKey(String key) {
        return pasteRepository.findByKey(key).orElseThrow(() ->
                new PasteNotFoundException("Paste with key: " + key + "not found"));
    }

    public boolean existsByKey(String key) {
        return pasteRepository.existsByKey(key);
    }

    @Transactional
    public void save(Paste paste) {
        pasteRepository.save(paste);
    }

    @Transactional
    public void delete(Paste paste) {
        pasteRepository.delete(paste);
    }

}
