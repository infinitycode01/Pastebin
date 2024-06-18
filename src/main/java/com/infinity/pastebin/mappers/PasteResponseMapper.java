package com.infinity.pastebin.mappers;

import com.infinity.pastebin.dto.PasteResponseDto;
import com.infinity.pastebin.models.Paste;

public interface PasteResponseMapper {
    PasteResponseDto toPasteResponseDto(Paste paste);
}
