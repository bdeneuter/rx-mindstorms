package com.github.bdeneuter.mindstorms.rx.sensor;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import rx.Observable;

/**
 * <b>EV3 Infra Red sensor</b><br>
 */
public class ColorSensor {

    private final Observable<ColorId> colorId;
    private final Observable<Color> color;

    ColorSensor(EV3ColorSensor sensor) {
        colorId = new Sampler(() -> sensor.getColorIDMode())
                .sample()
                .map(sample -> sample.values[sample.offset])
                .map(ColorId::colorId)
                .distinctUntilChanged();

        this.color = new Sampler(() -> sensor.getRGBMode())
                .sample()
                .map(sample -> new Color(
                        Math.round(sample.values[sample.offset]),
                        Math.round(sample.values[sample.offset + 1]),
                        Math.round(sample.values[sample.offset + 2])))
                .distinctUntilChanged(color ->
                        "r: " + color.getRed() +
                                ", g: " + color.getGreen() +
                                ", b: " + color.getBlue() );
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
