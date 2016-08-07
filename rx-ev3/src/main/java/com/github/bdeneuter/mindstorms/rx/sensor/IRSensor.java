package com.github.bdeneuter.mindstorms.rx.sensor;

import lejos.hardware.sensor.EV3IRSensor;
import rx.Observable;

/**
 * <b>EV3 Infra Red sensor</b><br>
 */
public class IRSensor {

    private final Observable<Float> distance;

    IRSensor(EV3IRSensor sensor) {
        this.distance = new Sampler("IRSensor", sensor.getDistanceMode())
                .sample()
                .map(sample -> sample.values[sample.offset]);
    }

    /**
     * <b>Distance mode</b><br>
     * Measures the distance to an object in front of the sensor.
     *
     * <p>
     * The effective range of the sensor in Distance mode  is about 5 to 50 centimeters. Outside this range a zero is returned
     * for low values and positive infinity for high values.
     */
    public Observable<Float> distance() {
        return distance;
    }

}
