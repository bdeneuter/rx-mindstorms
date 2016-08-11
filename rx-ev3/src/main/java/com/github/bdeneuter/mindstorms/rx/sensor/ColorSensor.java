package com.github.bdeneuter.mindstorms.rx.sensor;

import com.github.bdeneuter.mindstorms.rx.hardware.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import rx.Observable;

/**
 * <b>EV3 Infra Red sensor</b><br>
 */
public class ColorSensor {

    private final Observable<ColorId> colorId;
    private final Observable<Color> color;

    ColorSensor(Port port) {
        colorId = Observable.using(
                        () -> createSensor(port),
                        sensor -> new Sampler(() -> sensor.getColorIDMode()).sample(),
                        sensor -> closeSensor(sensor)
                )
                .share()
                .map(sample -> sample.values[sample.offset])
                .map(ColorId::colorId)
                .distinctUntilChanged();

        this.color = Observable.using(
                        () -> createSensor(port),
                        sensor -> new Sampler(() -> sensor.getRGBMode()).sample(),
                        sensor -> closeSensor(sensor)
                )
                .share()
                .map(sample -> new Color(
                        Math.round(sample.values[sample.offset]),
                        Math.round(sample.values[sample.offset + 1]),
                        Math.round(sample.values[sample.offset + 2])))
                .distinctUntilChanged(color ->
                        "r: " + color.getRed() +
                                ", g: " + color.getGreen() +
                                ", b: " + color.getBlue() );
    }

    private EV3ColorSensor createSensor(Port port) {
        System.out.println("Create LeJOS color sensor");
        return new EV3ColorSensor(port.getPort());
    }

    private void closeSensor(EV3ColorSensor sensor) {
        System.out.println("Close LeJOS color sensor");
        sensor.close();
    }

    /**
     * <b>ColorId mode</b><br>
     * Measures the colorId.
     */
    public Observable<ColorId> colorId() {
        return colorId;
    }

    /**
     * <b>Color mode</b><br>
     * Measures the color.
     */
    public Observable<Color> color() {
        return color;
    }

}
