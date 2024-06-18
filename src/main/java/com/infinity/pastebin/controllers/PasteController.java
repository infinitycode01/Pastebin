package com.infinity.pastebin.controllers;

import com.infinity.pastebin.dto.PasteCreateDto;
import com.infinity.pastebin.dto.PasteResponseDto;
import com.infinity.pastebin.mappers.impl.PasteMapperImpl;
import com.infinity.pastebin.models.Paste;
import com.infinity.pastebin.services.impl.PasteService;
import com.infinity.pastebin.services.impl.S3TextServiceImpl;
import com.infinity.pastebin.exceptions.PasteNotCreatedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PasteController {

    private final S3TextServiceImpl s3TextServiceImpl;
    private final PasteService pasteService;
    private final PasteMapperImpl pasteMapperImpl;

    /*
     * {
     *   "title": "",
     *   "content": "",
     *   "author": "",
     *   "expirationTimeDays": "",
     *   "visibleFor": ["", "", ""]
     * }
     */
    @PostMapping("/new")
    public ResponseEntity<String> create(@RequestBody @Valid PasteCreateDto pasteCreateDTO,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("\n");
            }
            throw new PasteNotCreatedException(errorMsg.toString());
        }

        Paste paste = pasteMapperImpl.toPaste(pasteCreateDTO);

        s3TextServiceImpl.upload(pasteCreateDTO.getContent(),
                paste.getKey(),
                pasteCreateDTO.getExpirationTimeDays());

        pasteService.save(paste);

        return ResponseEntity.status(HttpStatus.CREATED).body("Paste was created");
    }

    @GetMapping("/{key}")
    public ResponseEntity<PasteResponseDto> getPaste(@PathVariable String key) {
        return ResponseEntity.ok(pasteMapperImpl
                .toPasteResponseDto(pasteService.findByKey(key)));
    }

    @PutMapping("/{key}")
    public ResponseEntity<String> updatePaste(@RequestBody String content, @PathVariable String key) {
        s3TextServiceImpl.update(content, key);
        return ResponseEntity.status(HttpStatus.OK).body("Paste was updated");
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<String> deletePaste(@PathVariable String key) {
        s3TextServiceImpl.delete(key);
        return ResponseEntity.ok("Deleted successfully");
    }

}
