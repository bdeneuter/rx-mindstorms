package com.github.bdeneuter.mindstorms.rx.sensor;

import lejos.hardware.sensor.SensorMode;

@FunctionalInterface
interface SensorModeProvider {

    SensorMode getSensorMode();

}
