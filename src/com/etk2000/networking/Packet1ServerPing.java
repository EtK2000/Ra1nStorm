package com.etk2000.networking;

public class Packet1ServerPing extends Packet {

	public Packet1ServerPing() {
		super(1);
		super.registerPacket(1);
	}

}
