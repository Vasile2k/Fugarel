package net.vasile2k.fugarel.networking;

import net.vasile2k.fugarel.networking.packets.Packet;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class PacketDecoder {

    private static HashMap<Byte, Class<? extends Packet>> packetTypes;

    static {
        packetTypes = new HashMap<>();

        Package currentPackage = PacketDecoder.class.getPackage();
        String packetsPackage = currentPackage.getName() + ".packets";
        String packetsPath = packetsPackage.replace('.', '/');
        InputStream is = PacketDecoder.class.getClassLoader().getResourceAsStream(packetsPath);
        assert is != null;

        StringBuilder data = new StringBuilder();

        int buf = 0;

        try{
            while ((buf = is.read()) != -1){
                data.append((char) buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] potentialPacketClasses = data.toString().split("\n");

        for(String packetClassName: potentialPacketClasses){
            if(packetClassName.endsWith(".class")){
                // Remove .class extension
                String className = packetClassName.substring(0, packetClassName.length() - 6);
                String fullClassName = packetsPackage + "." + className;
                try {
                    Class<?> packetClass = Class.forName(fullClassName);
                    // Check if class is concrete
                    if(!Modifier.isAbstract(packetClass.getModifiers())){
                        Annotation[] annotations = packetClass.getAnnotations();
                        for(Annotation a: annotations){
                            if(a instanceof PacketSpec){
                                byte packetId = ((PacketSpec) a).packetId();
                                System.out.println("[Packet Loader] Loaded packet id " + packetId + ": " + packetClass.getSimpleName());

                                Class<? extends Packet> packet = (Class<? extends Packet>) packetClass;

                                if(PacketDecoder.packetTypes.containsKey(packetId)){
                                    System.err.println("Found multipla packets with the same id, ignoring one of them");
                                }else {
                                    PacketDecoder.packetTypes.put(packetId, packet);
                                }

                                // If annotation found, we don't have to search anymore
                                break;
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("[Networking] Loaded " + PacketDecoder.packetTypes.size() + " packets");

    }

    public static Packet decodeFromBytes(byte[] serializedPacket){
        byte packetId = Packet.getPacketId(serializedPacket);

        Class<? extends Packet> packetClass = PacketDecoder.packetTypes.get(packetId);

        if(packetClass != null){
            try {
                Packet p = packetClass.newInstance();
                p.fromBytes(serializedPacket);
                return p;
            } catch (InstantiationException | IllegalAccessException | MalformedPacketException e) {
                System.err.println("[Packet Decoder] Got an invalid packet with id " + packetId);
            }
        }

        return null;
    }

}
