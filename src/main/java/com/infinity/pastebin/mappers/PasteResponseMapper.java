package com.infinity.pastebin.mappers;

import com.infinity.pastebin.dto.PasteResponseDTO;
import com.infinity.pastebin.models.Paste;

public interface PasteResponseMapper {
    PasteResponseDTO toPasteResponseDto(Paste paste);
}
