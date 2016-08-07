package com.github.bdeneuter.mindstorms.rx.hardware;

import lejos.hardware.*;

public class EV3Brick {

    private static Brick brick;

    public static Brick brick() {
        if (brick == null) {
            brick = BrickFinder.getDefault();
        }
        return brick;
    }

    public static Key escapeKey() {
        return brick().getKey("Escape");
    }

}
