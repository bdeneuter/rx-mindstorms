package com.github.bdeneuter.mindstorms.rx.sensor;

import com.github.bdeneuter.mindstorms.rx.hardware.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import rx.Observable;

public class TouchSensor {

    private final Observable<Boolean> touched;

    TouchSensor(Port port) {
        touched = Observable.using(
                        () -> createSensor(port),
                        sensor -> new Sampler(() -> sensor.getTouchMode()).sample(),
                        sensor -> closeSensor(sensor)
                    )
                    .share()
                    .map(sample -> sample.values[sample.offset])
                    .distinctUntilChanged()
                    .map(value -> value == 1);
    }

    private EV3TouchSensor createSensor(Port port) {
        System.out.println("Create LeJOS sensor");
        return new EV3TouchSensor(port.getPort());
    }

    private void closeSensor(EV3TouchSensor sensor) {
        System.out.println("Close LeJOS sensor");
        sensor.close();
    }

    public Observable<Boolean> touched() {
        return touched;
    }
}
