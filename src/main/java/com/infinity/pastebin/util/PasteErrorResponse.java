package com.infinity.pastebin.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PasteErrorResponse {
    private String message;
    private LocalDateTime timestamp;
}
