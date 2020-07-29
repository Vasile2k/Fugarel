package net.vasile2k.fugarel.networking.packets;

import net.vasile2k.fugarel.networking.MalformedPacketException;
import net.vasile2k.fugarel.networking.PacketSpec;
import net.vasile2k.fugarel.networking.StringEncoder;

@PacketSpec(packetId = PlayerPositionPacket.PACKET_ID)
public class PlayerPositionPacket extends Packet {

    public static final byte PACKET_ID = 5;

    public int playerId;
    public int x;
    public int y;

    public PlayerPositionPacket(int playerId, int x, int y){
        this.playerId = playerId;
        this.x = x;
        this.y = y;
    }

    public PlayerPositionPacket(){

    }

    @Override
    public byte[] toBytes() {
        byte[] idBytes = StringEncoder.encodeInt(this.playerId);
        byte[] xBytes = StringEncoder.encodeInt(this.x);
        byte[] yBytes = StringEncoder.encodeInt(this.y);

        byte[] pack = new byte[idBytes.length + xBytes.length + yBytes.length + 3];
        pack[0] = PACKET_BEGIN;
        pack[1] = PACKET_ID;

        System.arraycopy(idBytes, 0, pack, 2, idBytes.length);
        System.arraycopy(xBytes, 0, pack, 2 + idBytes.length, xBytes.length);
        System.arraycopy(yBytes, 0, pack, 2 + idBytes.length + xBytes.length, yBytes.length);

        pack[pack.length - 1] = PACKET_END;
        return pack;
    }

    @Override
    public void fromBytes(byte[] bytes) throws MalformedPacketException {
        try{
            assert bytes[0] == PACKET_BEGIN;
            assert bytes[1] == PACKET_ID;

            byte[] encoded = new byte[bytes.length - 3];
            System.arraycopy(bytes, 2, encoded, 0, encoded.length);
            this.playerId = StringEncoder.decodeInt(encoded);

            int encodedIdLengthInBytes = 0;
            int currentChar = 0;
            for(; encodedIdLengthInBytes < 8 && currentChar < 4; ++encodedIdLengthInBytes){
                if(encoded[encodedIdLengthInBytes] != StringEncoder.ESCAPE_CHARACTER){
                    ++currentChar;
                }
            }

            System.arraycopy(bytes, 2 + encodedIdLengthInBytes, encoded, 0, encoded.length - encodedIdLengthInBytes);
            this.x = StringEncoder.decodeInt(encoded);

            int encodedIdLengthInBytes2 = 0;
            currentChar = 0;
            for(; encodedIdLengthInBytes2 < 8 && currentChar < 4; ++encodedIdLengthInBytes2){
                if(encoded[encodedIdLengthInBytes2] != StringEncoder.ESCAPE_CHARACTER){
                    ++currentChar;
                }
            }

            System.arraycopy(bytes, 2 + encodedIdLengthInBytes + encodedIdLengthInBytes2, encoded, 0, encoded.length - encodedIdLengthInBytes - encodedIdLengthInBytes2);
            this.y = StringEncoder.decodeInt(encoded);

            assert bytes[bytes.length - 1] == PACKET_END;
        }catch (Exception e){
            throw new MalformedPacketException("Invalid packet of type: " + this.getClass().getName());
        }
    }
}
