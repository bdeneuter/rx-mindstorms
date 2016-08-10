package com.github.bdeneuter.mindstorms.rx.sensor;

import lejos.robotics.Color;

import static java.util.Arrays.asList;

public enum ColorId {

    NONE(Color.NONE),
    BLACK(Color.BLACK),
    BLUE(Color.BLUE),
    GREEN(Color.GREEN),
    YELLOW(Color.YELLOW),
    RED(Color.RED),
    WHITE(Color.WHITE),
    BROWN(Color.BROWN);

    int id;

    ColorId(int id) {
        this.id = id;
    }

    public static ColorId colorId(float id) {
        return colorId((int) id);
    }

    public static ColorId colorId(int id) {
        return asList(values())
                .stream()
                .filter(color -> color.id == id)
                .findFirst()
                .orElse(NONE);
    }
}
