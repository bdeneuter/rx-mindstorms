package com.github.bdeneuter.mindstorms.rx.sensor;

import lejos.hardware.sensor.SensorMode;
import rx.Observable;

class Sampler {

    private final SensorMode sensorMode;
    private final Sample sample;

    private boolean stopped;

    Sampler(SensorMode sensorMode) {
        this.sensorMode = sensorMode;
        this.sample = new Sample(new float[sensorMode.sampleSize()], 0);
    }

    Observable<Sample> sample() {
        System.out.println("Start sampling");
        return Observable.create(subscriber -> {
            try {
                while (!stopped) {
                    sensorMode.fetchSample(sample.values, sample.offset);
                    subscriber.onNext(sample);
                }
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    void stop() {
        System.out.println("Stop sampling");
        stopped = true;
    }
}
