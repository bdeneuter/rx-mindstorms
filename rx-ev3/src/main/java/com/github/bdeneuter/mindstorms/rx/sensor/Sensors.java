package com.github.bdeneuter.mindstorms.rx.sensor;

import com.github.bdeneuter.mindstorms.rx.hardware.Port;
import lejos.hardware.sensor.EV3IRSensor;

public class Sensors {

    private static IRSensor irSensor;

    public static IRSensor irSensor(Port port) {
        if (irSensor == null) {
            System.out.println("Create IRSensor");
            irSensor = new IRSensor(new EV3IRSensor(port.getPort()));
        }
        return irSensor;
    }

}
