package net.vasile2k.fugarel.networking.packets;

import net.vasile2k.fugarel.networking.MalformedPacketException;
import net.vasile2k.fugarel.networking.PacketSpec;

@PacketSpec(packetId = RemovePlayerPacket.PACKET_ID)
public class RemovePlayerPacket extends Packet {

    public static final byte PACKET_ID = 4;

    public int playerId;

    public RemovePlayerPacket(int playerId){
        this.playerId = playerId;
    }

    public RemovePlayerPacket(){

    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void fromBytes(byte[] bytes) throws MalformedPacketException {

    }
}
