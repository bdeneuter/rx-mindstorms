package com.github.bdeneuter.mindstorms.rx.sensor;

import com.github.bdeneuter.mindstorms.rx.hardware.Port;

public class Sensors {

    private static IRSensor irSensor;
    private static ColorSensor colorSensor;
    private static TouchSensor touchSensor;

    public static IRSensor irSensor(Port port) {
        if (irSensor == null) {
            System.out.println("Create IRSensor");
            irSensor = new IRSensor(port);
        }
        return irSensor;
    }

    public static ColorSensor colorSensor(Port port) {
        if (colorSensor == null) {
            System.out.println("Create ColorSensor");
            colorSensor = new ColorSensor(port);
        }
        return colorSensor;
    }

    public static TouchSensor touchSensor(Port port) {
        if (touchSensor == null) {
            System.out.println("Create TouchSensor");
            touchSensor = new TouchSensor(port);
        }
        return touchSensor;
    }

}
