package com.github.bdeneuter.mindstorms.robot;

import com.github.bdeneuter.mindstorms.rx.sensor.Sensors;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.port.Port;
import lejos.hardware.port.UARTPort;

import static com.github.bdeneuter.mindstorms.rx.hardware.Hardware.brick;
import static com.github.bdeneuter.mindstorms.rx.hardware.Hardware.key;
import static com.github.bdeneuter.mindstorms.rx.hardware.Key.ESCAPE;
import static com.github.bdeneuter.mindstorms.rx.hardware.Port.S4;

public class Robot {

    public static final void main(String... args) {

        System.out.println("Number of cpu's: " + Runtime.getRuntime().availableProcessors());

        key(ESCAPE).addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(Key k) {

            }

            @Override
            public void keyReleased(Key k) {
                System.exit(0);
            }
        });

        Sensors.irSensor(S4)
                .distance()
                //.limit(20)
                .subscribe(distance -> System.out.println(distance));

    }

    private static void openPort(Port p) {
        try {
            UARTPort port = brick().getPort(p.getName()).open(UARTPort.class);
            port.close();
            System.out.println("Port opened " + p);
        } catch (Exception e) {
            System.out.println("Could not open port " + p);
        }
    }

}
