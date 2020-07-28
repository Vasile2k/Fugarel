package net.vasile2k.fugarel.networking;

import net.vasile2k.fugarel.networking.packets.Packet;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class SocketPacketReader {

    private SocketPacketReader(){

    }

    /**
     * Blocking method to read a packet from a socket
     * @param sock the socket to read from
     * @return the serialized packet
     */
    public static byte[] readPacketFromSocket(Socket sock){
        try {
            int rawData = 0;
            while ((rawData = sock.getInputStream().read()) != Packet.PACKET_BEGIN) {
                // Silentry ignore everythong that's not a packet begin
                if(rawData == -1){
                    return null;
                }
            }
            List<Byte> bytes = new ArrayList<Byte>();
            bytes.add(Packet.PACKET_BEGIN);
            while ((rawData = sock.getInputStream().read()) != Packet.PACKET_END) {
                bytes.add((byte)rawData);
                if(rawData == -1){
                    return null;
                }
            }
            bytes.add(Packet.PACKET_BEGIN);
            byte[] packetBytes = new byte[bytes.size()];
            for(int i = 0; i < bytes.size(); ++i){
                packetBytes[i] = (byte)bytes.get(i);
            }
            return packetBytes;
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return null;
    }

}
