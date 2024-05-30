package com.infinity.pastebin.controllers;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.infinity.pastebin.dto.PasteCreateDTO;
import com.infinity.pastebin.mappers.impl.PasteMapperImpl;
import com.infinity.pastebin.models.Paste;
import com.infinity.pastebin.services.impl.HashGeneratorService;
import com.infinity.pastebin.services.impl.PasteService;
import com.infinity.pastebin.services.impl.S3TextServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PasteController {
    private static final Logger logger = LoggerFactory.getLogger(PasteController.class);
    private final S3TextServiceImpl s3TextServiceImpl;
    private final HashGeneratorService hashGeneratorService;
    private final PasteService pasteService;
    private final PasteMapperImpl pasteMapperImpl;

    @Autowired
    public PasteController(S3TextServiceImpl s3TextServiceImpl, HashGeneratorService hashGeneratorService, PasteService pasteService, PasteMapperImpl pasteMapperImpl) {
        this.s3TextServiceImpl = s3TextServiceImpl;
        this.hashGeneratorService = hashGeneratorService;
        this.pasteService = pasteService;
        this.pasteMapperImpl = pasteMapperImpl;
    }

    // TODO
    // Add handle for uploading service
    // dto object ? need or no ???
    // Saving in bd
    @PostMapping("/new")
    public ResponseEntity<String> create(@RequestBody @Valid PasteCreateDTO pasteCreateDTO,
                                         BindingResult bindingResult) {
        /*
        * **CHECK FOR ERRORS**
        *
        * Create Paste with Mapper
        * upload to S3 using Paste(for key) and PasteDTO(for content)
        * save to DB
        * */


        Paste paste = pasteMapperImpl.toPaste(pasteCreateDTO);

        logger.info(paste.toString());

        s3TextServiceImpl.upload(pasteCreateDTO.getContent(),
                paste.getKey(),
                pasteCreateDTO.getExpirationTimeDays());

        pasteService.save(paste);

        logger.info("Paste created");
        return ResponseEntity.ok("Uploaded successfully");
    }

    // TODO
    // Handle if is does exist
    // return object
    // make a personDTO encharitate
    @GetMapping("/{key}")
    public ResponseEntity<String> getPaste(@PathVariable String key) {
        try {
            String content = s3TextServiceImpl.get(key);

            if (content == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(content);

        } catch (AmazonS3Exception e) {
            throw new AmazonServiceException("Failed to retrieve text from S3 bucket", e);
        }
    }

    @PutMapping
    public void updatePaste() {
        //TODO
    }


    // TODO Add exception handle
    @DeleteMapping("/{key}")
    public ResponseEntity<String> deletePaste(@PathVariable String key) {
        s3TextServiceImpl.delete(key);
        return ResponseEntity.ok("Deleted successfully");
    }
}
