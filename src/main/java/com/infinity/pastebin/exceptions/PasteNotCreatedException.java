package com.infinity.pastebin.exceptions;

public class PasteNotCreatedException extends RuntimeException {
    public PasteNotCreatedException() { }

    public PasteNotCreatedException(String message) {
        super(message);
    }
}
