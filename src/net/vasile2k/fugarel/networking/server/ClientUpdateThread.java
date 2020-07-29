package net.vasile2k.fugarel.networking.server;

import net.vasile2k.fugarel.networking.packets.PlayerPositionPacket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientUpdateThread implements Runnable {

    private ServerData serverData;

    public ClientUpdateThread(ServerData serverData){
        this.serverData = serverData;
    }

    @Override
    public void run() {
        while(true){

            // Create update packets with all player positions

            List<PlayerPositionPacket> packets = new ArrayList<>();
            for(ServerClient serverClient: this.serverData.clients){
                PlayerPositionPacket p = new PlayerPositionPacket(serverClient.id(), serverClient.x, serverClient.y);
                packets.add(p);
            }

            // Broadcast them to clients only if the client is initialized(finished handshaking)

            for(ServerClient client: this.serverData.clients){
                if(client.initialized){
                    try {
                        for(PlayerPositionPacket p : packets){
                            client.socket.getOutputStream().write(p.toBytes());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
