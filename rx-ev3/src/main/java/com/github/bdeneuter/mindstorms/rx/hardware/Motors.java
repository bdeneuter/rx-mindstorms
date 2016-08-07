package com.github.bdeneuter.mindstorms.rx.hardware;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

import java.util.HashMap;
import java.util.Map;

public class Motors {

    private static EV3LargeRegulatedMotor largeMotor;
    private static Map<Port, EV3MediumRegulatedMotor> mediumMotor = new HashMap<>();

    public static synchronized EV3MediumRegulatedMotor mediumMotor(Port port) {
        EV3MediumRegulatedMotor motor = mediumMotor.get(port);
        if (motor == null) {
            motor = new EV3MediumRegulatedMotor(port.getPort());
            mediumMotor.put(port, motor);
        }
        return motor;
    }

    public static synchronized EV3LargeRegulatedMotor largeMotor(Port port) {
        if (largeMotor == null) {
            largeMotor = new EV3LargeRegulatedMotor(port.getPort());
        }
        return largeMotor;
    }

}
