package com.infinity.pastebin.controllers;

import com.infinity.pastebin.dto.PasteDTO;
import com.infinity.pastebin.models.Paste;
import com.infinity.pastebin.services.S3Service;
import com.infinity.pastebin.util.KeyGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/paste")
public class PasteController {
    private final S3Service s3Service;

    @Autowired
    public PasteController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    // TODO Handle if is does exist
    @GetMapping("/{key}")
    public String getPaste(@PathVariable String key) {
        return s3Service.getText(key);
    }

    // TODO
    // Add handle for uploading service
    // dto object ? need or no ???
    // Saving in bd
    @PostMapping("/new")
    public ResponseEntity<String> createPaste(@RequestBody @Valid PasteDTO pasteDTO) {
        //int MAX_SIZE = 10 * 1024 * 1024;   10 MB
        if (pasteDTO.getContent().length() > 10 * 1024 * 1024) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Payload too large");
        }
        /*
        Paste paste = new Paste();



        */
        try {
            //s3Service.uploadText(pasteDTO.getContent(), key);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return ResponseEntity.ok("Uploaded successfully");
    }

    // TODO Add exception handle
    @DeleteMapping("/{key}")
    public void deletePaste(@PathVariable String key) {
        s3Service.deleteText(key);
    }
}
