package com.infinity.pastebin.controllers;

import com.infinity.pastebin.config.WebClientConfig;
import com.infinity.pastebin.services.HashGeneratorService;
import com.infinity.pastebin.services.PasteService;
import com.infinity.pastebin.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api")
public class PasteController {
    private final S3Service s3Service;
    private final HashGeneratorService hashGeneratorService;
    private final PasteService pasteService;

    @Autowired
    public PasteController(S3Service s3Service, HashGeneratorService hashGeneratorService, PasteService pasteService) {
        this.s3Service = s3Service;
        this.hashGeneratorService = hashGeneratorService;
        this.pasteService = pasteService;
    }

    // TODO
    // Handle if is does exist
    // return object
    // make a personDTO encharitate
    @GetMapping("/{key}")
    public String getPaste(@PathVariable String key) {
        return s3Service.getText(key);
    }

    // TODO
    // Add handle for uploading service
    // dto object ? need or no ???
    // Saving in bd
    @PostMapping("/new")
    public ResponseEntity<String> createPaste(@RequestBody String text) {
        s3Service.uploadText(text, hashGeneratorService.getHash());


        //pasteService.save(text);



        // pasteDTO encharitate
        // save a paste in bd


        return ResponseEntity.ok("Uploaded successfully");
    }

    // TODO Add exception handle
    @DeleteMapping("/{key}")
    public ResponseEntity<String> deletePaste(@PathVariable String key) {
        s3Service.deleteText(key);
        return ResponseEntity.ok("Deleted successfully");
    }

    /*private Paste convertToPaste(PasteDTO pasteDTO) {
        Paste paste = new Paste();

        paste.setTitle(pasteDTO.getTitle());
        paste.setKey();
        paste.setCreatedWho();
        paste.setCreatedAt();
        paste.setUpdatedAt();
    }*/
}
