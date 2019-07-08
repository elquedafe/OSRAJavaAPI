package org.osra.architecture;

public class Band {
	private String type;
	private int rate;
	private int packets;
	private int bytes;
	private int burstSize;

	public int getRate() {
		return rate;
	}

	public int getBytes() {
		return bytes;
	}

	public int getBurstSize() {
		return burstSize;
	}


}
