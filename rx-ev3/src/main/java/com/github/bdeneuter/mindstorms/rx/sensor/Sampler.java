package com.github.bdeneuter.mindstorms.rx.sensor;

import lejos.hardware.sensor.SensorMode;
import rx.Observable;
import rx.Subscriber;

import static java.lang.Thread.sleep;
import static rx.schedulers.Schedulers.newThread;

class Sampler {

    private final Observable<Sample> sample;

    Sampler(SensorModeProvider sensorModeProvider) {
        this.sample = createSampleObservable(sensorModeProvider)
                .subscribeOn(newThread())
                .share();
    }

    private Observable<Sample> createSampleObservable(SensorModeProvider sensorModeProvider) {
        return Observable.create(subscriber -> {
            try {
                sample(sensorModeProvider.getSensorMode(), subscriber);
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    private void sample(SensorMode sensorMode, Subscriber<? super Sample> subscriber) throws InterruptedException {
        System.out.println("Start sampling " + sensorMode.getName() + ", sample size: " + sensorMode.sampleSize());
        Sample sample = new Sample(new float[sensorMode.sampleSize()], 0);
        while (!subscriber.isUnsubscribed()) {
            sensorMode.fetchSample(sample.values, sample.offset);
            subscriber.onNext(sample);
            sleep(200);
        }
        System.out.println("Stop sampling " + sensorMode.getName());
    }

    Observable<Sample> sample() {
        return sample;
    }
}
