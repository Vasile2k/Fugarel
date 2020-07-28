package net.vasile2k.fugarel.networking.packets;

import net.vasile2k.fugarel.networking.MalformedPacketException;
import net.vasile2k.fugarel.networking.PacketSpec;

@PacketSpec(packetId = HelloClientPacket.PACKET_ID)
public class HelloClientPacket extends Packet {

    public static final byte PACKET_ID = 2;

    public HelloClientPacket(){

    }

    @Override
    public byte[] toBytes() {
        return new byte[]{PACKET_BEGIN, PACKET_ID, PACKET_END};
    }

    @Override
    public void fromBytes(byte[] bytes) throws MalformedPacketException {
        try{
            assert bytes[0] == PACKET_BEGIN;
            assert bytes[1] == PACKET_ID;
            assert bytes[2] == PACKET_END;
        }catch (Exception e){
            throw new MalformedPacketException("Invalid packet of type: " + this.getClass().getName());
        }
    }
}
