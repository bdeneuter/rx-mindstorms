package com.github.bdeneuter.mindstorms.rx.hardware;

import static com.github.bdeneuter.mindstorms.rx.hardware.Hardware.brick;

public enum Port {
	A, B, C, D, S1, S2, S3, S4;

	public lejos.hardware.port.Port getPort() {
		return brick().getPort(name());
	}
}
