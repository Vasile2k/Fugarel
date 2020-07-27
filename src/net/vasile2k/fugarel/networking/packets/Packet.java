package net.vasile2k.fugarel.networking.packets;

public abstract class Packet {

    public static final byte PACKET_BEGIN = 69;
    public static final byte PACKET_END = 0x69;

    public abstract byte PACKET_ID();

    public abstract byte[] serialize();

}
