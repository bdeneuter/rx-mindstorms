package com.github.bdeneuter.mindstorms.rx.hardware;

import lejos.hardware.*;

public class Hardware {

    private static Brick brick;

    public static Brick brick() {
        if (brick == null) {
            brick = BrickFinder.getDefault();
        }
        return brick;
    }

    public static lejos.hardware.Key key(Key key) {
        return brick().getKey(key.getValue());
    }

}
