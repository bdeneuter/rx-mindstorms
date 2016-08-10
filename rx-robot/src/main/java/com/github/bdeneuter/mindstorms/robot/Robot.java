package com.github.bdeneuter.mindstorms.robot;

import com.github.bdeneuter.mindstorms.rx.sensor.ColorId;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.port.Port;
import lejos.hardware.port.UARTPort;
import rx.Observable;

import static com.github.bdeneuter.mindstorms.rx.hardware.EV3Brick.brick;
import static com.github.bdeneuter.mindstorms.rx.hardware.EV3Brick.escapeKey;
import static com.github.bdeneuter.mindstorms.rx.hardware.Motors.mediumMotor;
import static com.github.bdeneuter.mindstorms.rx.hardware.Movement.FORWARD;
import static com.github.bdeneuter.mindstorms.rx.hardware.Movement.STOP;
import static com.github.bdeneuter.mindstorms.rx.hardware.Port.B;
import static com.github.bdeneuter.mindstorms.rx.hardware.Port.C;
import static com.github.bdeneuter.mindstorms.rx.hardware.Port.S3;
import static com.github.bdeneuter.mindstorms.rx.sensor.ColorId.RED;
import static com.github.bdeneuter.mindstorms.rx.sensor.Sensors.colorSensor;

public class Robot {

    public static final void main(String... args) throws InterruptedException {

        System.out.println("Number of cpu's: " + Runtime.getRuntime().availableProcessors());

        listenForShutdown();

        brick().getAudio().systemSound(2);

        /*irSensor(S4)
                .distance()
                .filter(distance -> distance == 5)
                .subscribe(System.out::println); */

        Observable<ColorId> red = colorSensor(S3)
                .colorId()
                .filter(colorId -> colorId == RED);

        Observable<ColorId> otherColor = colorSensor(S3)
                .colorId()
                .filter(colorId -> colorId != RED);

        mediumMotor(B).connect(red.map(colorId -> FORWARD));
        mediumMotor(C).connect(red.map(colorId -> FORWARD));

        mediumMotor(B).connect(otherColor.map(colorId -> STOP));
        mediumMotor(C).connect(otherColor.map(colorId -> STOP));

        mediumMotor(B).activate();
        mediumMotor(C).activate();

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
