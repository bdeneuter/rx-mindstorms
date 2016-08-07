package com.github.bdeneuter.mindstorms.robot;

import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.port.Port;
import lejos.hardware.port.UARTPort;

import static com.github.bdeneuter.mindstorms.rx.hardware.EV3Brick.brick;
import static com.github.bdeneuter.mindstorms.rx.hardware.EV3Brick.key;
import static com.github.bdeneuter.mindstorms.rx.hardware.Key.ESCAPE;
import static com.github.bdeneuter.mindstorms.rx.hardware.Port.*;
import static com.github.bdeneuter.mindstorms.rx.motor.Motors.mediumMotor;
import static com.github.bdeneuter.mindstorms.rx.sensor.Sensors.irSensor;
import static rx.schedulers.Schedulers.newThread;

public class Robot {

    public static final void main(String... args) throws InterruptedException {

        System.out.println("Number of cpu's: " + Runtime.getRuntime().availableProcessors());

        listenForShutdown();

        irSensor(S4)
                .distance()
                .subscribeOn(newThread())
                .filter(distance -> distance < 10)
                .subscribe(distance -> {
                    mediumMotor(C).forward();
                    mediumMotor(B).forward();
                });

        irSensor(S4)
                .distance()
                .subscribeOn(newThread())
                .filter(distance -> distance > 10)
                .subscribe(distance -> {
                    mediumMotor(C).stop();
                    mediumMotor(B).stop();
                });

        Thread.sleep(20000);

    }

    private static void listenForShutdown() {
        key(ESCAPE).addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(Key k) {

            }

            @Override
            public void keyReleased(Key k) {
                System.exit(0);
            }
        });
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
