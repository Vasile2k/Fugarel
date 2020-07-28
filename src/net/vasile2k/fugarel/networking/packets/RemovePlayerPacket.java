package net.vasile2k.fugarel.networking.packets;

import net.vasile2k.fugarel.networking.MalformedPacketException;
import net.vasile2k.fugarel.networking.PacketSpec;
import net.vasile2k.fugarel.networking.StringEncoder;

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
        byte[] idBytes = StringEncoder.encodeInt(this.playerId);

        byte[] pack = new byte[idBytes.length + 3];
        pack[0] = PACKET_BEGIN;
        pack[1] = PACKET_ID;

        System.arraycopy(idBytes, 0, pack, 2, idBytes.length);

        pack[pack.length - 1] = PACKET_END;
        return pack;
    }

    @Override
    public void fromBytes(byte[] bytes) throws MalformedPacketException {
        try{
            assert bytes[0] == PACKET_BEGIN;
            assert bytes[1] == PACKET_ID;

            byte[] encodedId = new byte[bytes.length - 3];
            System.arraycopy(bytes, 2, encodedId, 0, encodedId.length);
            this.playerId = StringEncoder.decodeInt(encodedId);

            assert bytes[bytes.length - 1] == PACKET_END;
        }catch (Exception e){
            throw new MalformedPacketException("Invalid packet of type: " + this.getClass().getName());
        }
    }
}
