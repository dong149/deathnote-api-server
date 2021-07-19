package com.rest.api.exception.note;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException() {
    }

    public NoteNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public NoteNotFoundException(String msg) {
        super(msg);
    }
}
