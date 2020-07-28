package net.vasile2k.fugarel.networking.packets;

import net.vasile2k.fugarel.networking.MalformedPacketException;
import net.vasile2k.fugarel.networking.PacketSpec;
import net.vasile2k.fugarel.networking.StringEncoder;

@PacketSpec(packetId = NewPlayerPacket.PACKET_ID)
public class NewPlayerPacket extends Packet {

    public static final byte PACKET_ID = 3;

    public int id;
    public String name;

    public NewPlayerPacket(int id, String name){
        this.id = id;
        this.name = name;
    }

    public NewPlayerPacket(){

    }

    @Override
    public byte[] toBytes() {
        byte[] idBytes = StringEncoder.encodeInt(this.id);
        byte[] nameBytes = StringEncoder.encodeString(this.name);

        byte[] pack = new byte[nameBytes.length + idBytes.length + 3];
        pack[0] = PACKET_BEGIN;
        pack[1] = PACKET_ID;

        System.arraycopy(idBytes, 0, pack, 2, idBytes.length);
        System.arraycopy(nameBytes, 0, pack, 2 + idBytes.length, nameBytes.length);

        pack[pack.length - 1] = PACKET_END;
        return pack;
    }

    @Override
    public void fromBytes(byte[] bytes) throws MalformedPacketException {
        try{
            assert bytes[0] == PACKET_BEGIN;
            assert bytes[1] == PACKET_ID;

            byte[] encodedId = new byte[8];
            System.arraycopy(bytes, 2, encodedId, 0, 8);
            this.id = StringEncoder.decodeInt(encodedId);

            int encodedIdLengthInBytes = 0;
            int currentChar = 0;
            for(; encodedIdLengthInBytes < 8 && currentChar < 4; ++encodedIdLengthInBytes){
                if(encodedId[encodedIdLengthInBytes] != StringEncoder.ESCAPE_CHARACTER){
                    ++currentChar;
                }
            }

            byte[] encodedString = new byte[bytes.length - 3 - encodedIdLengthInBytes];
            System.arraycopy(bytes, 2 + encodedIdLengthInBytes, encodedString, 0, encodedString.length);

            this.name = StringEncoder.decodeString(encodedString);

            assert bytes[bytes.length - 1] == PACKET_END;
        }catch (Exception e){
            throw new MalformedPacketException("Invalid packet of type: " + this.getClass().getName());
        }
    }
}
