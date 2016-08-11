package com.github.bdeneuter.mindstorms.rx.sensor;

import com.github.bdeneuter.mindstorms.rx.hardware.Port;
import lejos.hardware.sensor.EV3IRSensor;
import rx.Observable;

/**
 * <b>EV3 Infra Red sensor</b><br>
 */
public class IRSensor {

    private final Observable<Float> distance;

    IRSensor(Port port) {
        this.distance = Observable.using(
                            () -> createSensor(port),
                            sensor -> new Sampler(() -> sensor.getDistanceMode()).sample(),
                            sensor -> closeSensor(sensor)
                )
                .share()
                .map(sample -> sample.values[sample.offset])
                .distinctUntilChanged();
    }

    private EV3IRSensor createSensor(Port port) {
        System.out.println("Create LeJOS sensor");
        return new EV3IRSensor(port.getPort());
    }

    private void closeSensor(EV3IRSensor sensor) {
        System.out.println("Close LeJOS sensor");
        sensor.close();
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
