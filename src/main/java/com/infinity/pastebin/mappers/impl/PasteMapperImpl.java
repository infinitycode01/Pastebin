package com.infinity.pastebin.mappers.impl;

import com.infinity.pastebin.dto.PasteCreateDTO;
import com.infinity.pastebin.dto.PasteResponseDTO;
import com.infinity.pastebin.mappers.PasteCreateMapper;
import com.infinity.pastebin.mappers.PasteResponseMapper;
import com.infinity.pastebin.models.Paste;
import com.infinity.pastebin.services.impl.HashGeneratorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PasteMapperImpl implements PasteCreateMapper, PasteResponseMapper {
    private final HashGeneratorService hashGeneratorService;

    public PasteMapperImpl(HashGeneratorService hashGeneratorService) {
        this.hashGeneratorService = hashGeneratorService;
    }

    @Override
    public Paste toPaste(@Valid PasteCreateDTO pasteCreateDTO) {

        String key = hashGeneratorService.getNewHash();
        boolean isPrivate = !pasteCreateDTO.getVisibleFor().isEmpty();

        return Paste.builder()
                .title(pasteCreateDTO.getTitle())
                .key(key)
                .author(pasteCreateDTO.getAuthor())
                .creationDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(pasteCreateDTO.getExpirationTimeDays()))
                .url("http://localhost:8081/api/" + key)
                .isPrivate(isPrivate)
                .visibleFor(Stream.concat(
                        Stream.of(pasteCreateDTO.getAuthor()),
                        pasteCreateDTO.getVisibleFor().stream()).collect(
                                Collectors.toCollection(HashSet::new)))
                .build();
    }

    @Override
    public PasteResponseDTO toPasteResponseDto(Paste paste) {
        return null;
    }
}
