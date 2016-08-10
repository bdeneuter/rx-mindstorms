package com.github.bdeneuter.mindstorms.rx.hardware;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

import java.util.HashMap;
import java.util.Map;

public class Motors {

    private static Motor largeMotor;
    private static Map<Port, Motor> mediumMotor = new HashMap<>();

    public static synchronized Motor mediumMotor(Port port) {
        Motor motor = mediumMotor.get(port);
        if (motor == null) {
            motor = new Motor(new EV3MediumRegulatedMotor(port.getPort()));
            mediumMotor.put(port, motor);
        }
        return motor;
    }

    public static synchronized Motor largeMotor(Port port) {
        if (largeMotor == null) {
            largeMotor = new Motor(new EV3LargeRegulatedMotor(port.getPort()));
        }
        return largeMotor;
    }

}
