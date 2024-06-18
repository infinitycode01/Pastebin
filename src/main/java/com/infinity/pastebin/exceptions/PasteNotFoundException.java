package com.infinity.pastebin.exceptions;

public class PasteNotFoundException extends RuntimeException {
    public PasteNotFoundException() { }

    public PasteNotFoundException(String message) {
        super(message);
    }
}
