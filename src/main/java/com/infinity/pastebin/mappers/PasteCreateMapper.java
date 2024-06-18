package com.infinity.pastebin.mappers;

import com.infinity.pastebin.dto.PasteCreateDto;
import com.infinity.pastebin.models.Paste;

public interface PasteCreateMapper {
    Paste toPaste(PasteCreateDto pasteCreateDTO);
}
