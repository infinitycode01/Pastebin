package com.infinity.pastebin.mappers.impl;

import com.infinity.pastebin.dto.PasteCreateDto;
import com.infinity.pastebin.dto.PasteResponseDto;
import com.infinity.pastebin.mappers.PasteCreateMapper;
import com.infinity.pastebin.mappers.PasteResponseMapper;
import com.infinity.pastebin.models.Paste;
import com.infinity.pastebin.services.impl.HashGeneratorService;
import com.infinity.pastebin.services.impl.S3TextServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PasteMapperImpl implements PasteCreateMapper, PasteResponseMapper {

    private final HashGeneratorService hashGeneratorService;
    private final S3TextServiceImpl s3TextService;

    @Value("${hashGeneratorUrl}")
    private String hashGeneratorUrl;

    public PasteMapperImpl(HashGeneratorService hashGeneratorService, S3TextServiceImpl s3TextService) {
        this.hashGeneratorService = hashGeneratorService;
        this.s3TextService = s3TextService;
    }

    public Paste toPaste(@Valid PasteCreateDto pasteCreateDTO) {
        String key = hashGeneratorService.getNewHash();

        Paste.PasteBuilder builder = Paste.builder()
                .title(pasteCreateDTO.getTitle())
                .key(key)
                .author(pasteCreateDTO.getAuthor())
                .creationDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(7))
                .url(hashGeneratorUrl + "/api/" + key);

        if (pasteCreateDTO.getVisibleFor().isEmpty()) {
            builder.isPrivate(false)
                    .visibleFor(new HashSet<>());
        } else {
            builder.isPrivate(true)
                    .visibleFor(Stream.concat(
                            Stream.of(pasteCreateDTO.getAuthor()),
                            pasteCreateDTO.getVisibleFor().stream()).collect(
                            Collectors.toCollection(HashSet::new)));
        }

        return builder.build();
    }

    public PasteResponseDto toPasteResponseDto(@Valid Paste paste) {
        return PasteResponseDto.builder()
                .title(paste.getTitle())
                .content(s3TextService.get(paste.getKey()))
                .author(paste.getAuthor())
                .creationDate(paste.getCreationDate())
                .expirationDate(paste.getExpirationDate())
                .url(paste.getUrl())
                .isPrivate(paste.isPrivate())
                .visibleFor(paste.getVisibleFor())
                .build();
    }
}
