package com.github.bdeneuter.mindstorms.rx.sensor;

import lejos.hardware.sensor.SensorMode;
import rx.Observable;
import rx.Subscriber;

import static rx.schedulers.Schedulers.newThread;

class Sampler {

    private final String name;
    private final Observable<Sample> sample;

    Sampler(String name, SensorMode sensorMode) {
        this.name = name;
        this.sample = createSampleObservable(sensorMode)
                .subscribeOn(newThread())
                .replay(1)
                .refCount();
    }

    private Observable<Sample> createSampleObservable(SensorMode sensorMode) {
        return Observable.create(subscriber -> {
            try {
                sample(sensorMode, subscriber);
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    private void sample(SensorMode sensorMode, Subscriber<? super Sample> subscriber) {
        System.out.println("Start sampling " + name);
        Sample sample = new Sample(new float[sensorMode.sampleSize()], 0);
        while (!subscriber.isUnsubscribed()) {
            sensorMode.fetchSample(sample.values, sample.offset);
            subscriber.onNext(sample);
        }
        System.out.println("Stop sampling " + name);
    }

    Observable<Sample> sample() {
        return sample;
    }
}
