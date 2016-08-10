package com.github.bdeneuter.mindstorms.rx.hardware;

import lejos.hardware.motor.BaseRegulatedMotor;
import rx.Observable;

import static com.github.bdeneuter.mindstorms.rx.hardware.Movement.*;
import static rx.Observable.empty;

public class Motor {

    private final BaseRegulatedMotor motor;
    private Observable<Movement> movements = empty();

    Motor(BaseRegulatedMotor motor) {
        this.motor = motor;
    }

    public void connect(Observable<Movement> movements) {
        this.movements = this.movements.mergeWith(movements);
    }

    public void activate() {
        movements.filter(m -> m == FORWARD).subscribe(m -> motor.forward());
        movements.filter(m -> m == BACKWARD).subscribe(m -> motor.backward());
        movements.filter(m -> m == STOP).subscribe(m -> motor.stop(true));
    }

}
