package com.etk2000.networking;

import java.util.ArrayList;

public abstract class Packet {
	private ArrayList<Integer> acceptedPacketIDs = new ArrayList<Integer>();
	private int id;

	public Packet(int PacketID) {
		id = PacketID;
	}

	public int getPacketID() {
		return id;
	}
	
	/** add a new packet, returns false if packet already exists **/
	public boolean registerPacket(int PacketID) {
		if (!acceptedPacketIDs.contains(PacketID)) {
			acceptedPacketIDs.add(PacketID);
			return true;
		}
		return false;
	}
}