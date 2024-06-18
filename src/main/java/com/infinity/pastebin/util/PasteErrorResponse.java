package com.infinity.pastebin.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PasteErrorResponse {
    private String message;
    private long timestamp;
}
