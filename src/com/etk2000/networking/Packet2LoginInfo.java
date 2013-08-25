package com.etk2000.networking;

public class Packet2LoginInfo extends Packet {

	public Packet2LoginInfo() {
		super(2);
		super.registerPacket(2);
	}
}
