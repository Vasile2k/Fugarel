package net.vasile2k.fugarel.networking.packets;

import net.vasile2k.fugarel.networking.MalformedPacketException;

public abstract class Packet {

    public static final byte PACKET_BEGIN = 69;
    public static final byte PACKET_END = 0x69;

    public abstract byte[] toBytes();
    public abstract void fromBytes(byte[] bytes) throws MalformedPacketException;

    public static byte getPacketId(byte[] packet){
        // A valid packet should have a t least 3 bytes
        assert packet.length >= 3;
        // And the ID is second byte
        return packet[1];
    }
}
