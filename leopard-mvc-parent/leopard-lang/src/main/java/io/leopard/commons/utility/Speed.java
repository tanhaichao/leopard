package io.leopard.commons.utility;

public abstract class Speed {

	public Speed(int size) {
		this(size, "speed");
	}

	public Speed(int size, String name) {
		Clock clock = Clock.start();
		for (int i = 0; i < size; i++) {
			this.run();
		}
		clock.avg(size, name);
	}

	public abstract void run();
}
