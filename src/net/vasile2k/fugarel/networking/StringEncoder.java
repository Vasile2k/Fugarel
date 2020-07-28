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

    public static String decodeString(byte[] input){
        int occurencesEscaped = 0;

        for (byte b : input) {
            if (b == ESCAPE_CHARACTER) {
                occurencesEscaped++;
            }
        }

        byte[] out = new byte[input.length - occurencesEscaped];

        int outIndex = 0;
        for (int i = 0; i < input.length; ++i) {
            if (input[i] == ESCAPE_CHARACTER) {
                byte escaped = input[i + 1];
                byte unescaped = unescapeCharacter(escaped);
                out[outIndex++] = unescaped;
                i++;
            }else{
                out[outIndex++] = input[i];
            }
        }

        return new String(out);
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
