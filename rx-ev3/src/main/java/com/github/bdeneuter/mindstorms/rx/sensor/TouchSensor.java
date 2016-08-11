package com.github.bdeneuter.mindstorms.rx.sensor;

import lejos.hardware.sensor.EV3TouchSensor;
import rx.Observable;

public class TouchSensor {

    private final Observable<Boolean> touched;

    TouchSensor(EV3TouchSensor sensor) {

        touched = new Sampler(() -> sensor.getTouchMode())
                .sample()
                .map(sample -> sample.values[sample.offset])
                .distinctUntilChanged()
                .map(value -> value == 1);


    }

    public Observable<Boolean> touched() {
        return touched;
    }
}
