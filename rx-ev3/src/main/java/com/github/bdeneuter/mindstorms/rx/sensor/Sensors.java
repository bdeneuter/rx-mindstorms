package com.github.bdeneuter.mindstorms.rx.sensor;

import com.github.bdeneuter.mindstorms.rx.hardware.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;

public class Sensors {

    private static IRSensor irSensor;
    private static ColorSensor colorSensor;
    private static TouchSensor touchSensor;

    public static IRSensor irSensor(Port port) {
        if (irSensor == null) {
            System.out.println("Create IRSensor");
            irSensor = new IRSensor(new EV3IRSensor(port.getPort()));
        }
        return irSensor;
    }

    public static ColorSensor colorSensor(Port port) {
        if (colorSensor == null) {
            System.out.println("Create ColorSensor");
            colorSensor = new ColorSensor(new EV3ColorSensor(port.getPort()));
        }
        return colorSensor;
    }

    public static TouchSensor touchSensor(Port port) {
        if (touchSensor == null) {
            System.out.println("Create TouchSensor");
            touchSensor = new TouchSensor(new EV3TouchSensor(port.getPort()));
        }
        return touchSensor;
    }

}
