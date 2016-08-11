package com.github.bdeneuter.mindstorms.robot;

import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.port.Port;
import lejos.hardware.port.UARTPort;
import rx.Subscription;

import static com.github.bdeneuter.mindstorms.rx.hardware.EV3Brick.brick;
import static com.github.bdeneuter.mindstorms.rx.hardware.EV3Brick.escapeKey;
import static com.github.bdeneuter.mindstorms.rx.hardware.Port.S3;
import static com.github.bdeneuter.mindstorms.rx.sensor.Sensors.touchSensor;

public class Robot {

    public static final void main(String... args) throws InterruptedException {

        System.out.println("Number of cpu's: " + Runtime.getRuntime().availableProcessors());

        listenForShutdown();

        brick().getAudio().systemSound(2);

        /*irSensor(S4)
                .distance()
                .filter(distance -> distance == 5)
                .subscribe(System.out::println); */

//        colorSensor(S3)
//                .colorId()
//                .subscribe(System.out::println);


        Subscription subscription = touchSensor(S3)
                .touched()
                .subscribe(System.out::println);

        Thread.sleep(10000);

        subscription.unsubscribe();

        Thread.sleep(2000);

        touchSensor(S3)
                .touched()
                .subscribe(System.out::println);

        Thread.sleep(200000);

    }

    private static void listenForShutdown() {
        escapeKey().addKeyListener(new KeyListener() {
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
