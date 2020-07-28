package net.vasile2k.fugarel.networking.packets;

import net.vasile2k.fugarel.networking.MalformedPacketException;
import net.vasile2k.fugarel.networking.PacketSpec;
import net.vasile2k.fugarel.networking.StringEncoder;

@PacketSpec(packetId = IntroduceMyselfPacket.PACKET_ID)
public class IntroduceMyselfPacket extends Packet {

    public String name;

    public static final byte PACKET_ID = 1;

    public IntroduceMyselfPacket(String name){
        this.name = name;
    }

    public IntroduceMyselfPacket(){

    }

    @Override
    public byte[] toBytes() {
        byte[] nameBytes = StringEncoder.encodeString(this.name);

        byte[] pack = new byte[nameBytes.length + 4];
        pack[0] = PACKET_BEGIN;
        pack[1] = PACKET_ID;
        pack[2] = (byte)name.length();

        // Escape packet END and begin byte
        System.arraycopy(nameBytes, 0, pack, 3, nameBytes.length);

        pack[pack.length - 1] = PACKET_END;
        return pack;
    }

    @Override
    public void fromBytes(byte[] bytes) throws MalformedPacketException {
        try{
            assert bytes[0] == PACKET_BEGIN;
            assert bytes[1] == PACKET_ID;

            int length = (int)bytes[2];

            byte[] encodedString = new byte[bytes.length - 4];
            System.arraycopy(bytes, 3, encodedString, 0, encodedString.length);

            this.name = StringEncoder.decodeString(encodedString);
            assert this.name.length() == length;

            assert bytes[bytes.length - 1] == PACKET_END;
        }catch (Exception e){
            throw new MalformedPacketException("Invalid packet of type: " + this.getClass().getName());
        }
    }
}
