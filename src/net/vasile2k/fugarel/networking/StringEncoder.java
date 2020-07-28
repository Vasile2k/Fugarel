package net.vasile2k.fugarel.networking;

import net.vasile2k.fugarel.networking.packets.Packet;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class StringEncoder {

    public static final byte ESCAPE_CHARACTER = 45;

    public static final HashMap<Byte, Byte> encodings;

    static {
        encodings = new HashMap<>();
        encodings.put(Packet.PACKET_BEGIN, (byte)0x13);
        encodings.put(Packet.PACKET_END, (byte)0x10);
        encodings.put(ESCAPE_CHARACTER, (byte)(ESCAPE_CHARACTER + 1));
    }

    private StringEncoder(){

    }

    public static byte[] encodeString(String input){
        byte[] in = input.getBytes(StandardCharsets.US_ASCII);
        return encodeBytes(in);
    }

    public static String decodeString(byte[] input){
        return new String(decodeBytes(input));
    }

    public static byte[] encodeInt(int value){
        byte[] intBytes = new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value
        };
        return encodeBytes(intBytes);
    }

    public static int decodeInt(byte[] input){
        byte[] bytes = decodeBytes(input);
        return  ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8 ) |
                ((bytes[3] & 0xFF));
    }

    public static byte[] encodeBytes(byte[] in){
        int occurencesToEscape = 0;

        for (byte b : in) {
            if (encodings.containsKey(b)) {
                occurencesToEscape++;
            }
        }

        byte[] out = new byte[in.length + occurencesToEscape];

        int outIndex = 0;
        for (byte b : in) {
            if (encodings.containsKey(b)) {
                out[outIndex++] = ESCAPE_CHARACTER;
                out[outIndex++] = (byte)encodings.get(b);
            }else{
                out[outIndex++] = b;
            }
        }
        return out;
    }

    public static byte[] decodeBytes(byte[] in){
        int occurencesEscaped = 0;

        for (byte b : in) {
            if (b == ESCAPE_CHARACTER) {
                occurencesEscaped++;
            }
        }

        byte[] out = new byte[in.length - occurencesEscaped];

        int outIndex = 0;
        for (int i = 0; i < in.length; ++i) {
            if (in[i] == ESCAPE_CHARACTER) {
                byte escaped = in[i + 1];
                byte unescaped = unescapeCharacter(escaped);
                out[outIndex++] = unescaped;
                i++;
            }else{
                out[outIndex++] = in[i];
            }
        }
        return out;
    }

    private static byte unescapeCharacter(byte chr){
        for (Map.Entry<Byte, Byte> entry : encodings.entrySet()) {
            if (entry.getValue().equals(chr)) {
                return entry.getKey();
            }
        }
        return (byte)0;
    }
}
