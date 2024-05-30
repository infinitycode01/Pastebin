package com.infinity.pastebin.mappers;

import com.infinity.pastebin.dto.PasteCreateDTO;
import com.infinity.pastebin.models.Paste;

public interface PasteCreateMapper {
    Paste toPaste(PasteCreateDTO pasteCreateDTO);
}
