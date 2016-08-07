package com.github.bdeneuter.mindstorms.rx.hardware;

public enum Key {

    ESCAPE("Escape");

    private String value;

    Key(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }
}
