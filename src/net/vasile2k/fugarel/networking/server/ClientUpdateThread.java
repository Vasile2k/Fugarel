package net.vasile2k.fugarel.networking.server;

import java.io.IOException;

public class ClientUpdateThread implements Runnable {

    private ServerData serverData;

    public ClientUpdateThread(ServerData serverData){
        this.serverData = serverData;
    }

    @Override
    public void run() {
        while(true){

            // Create update packet with all player positions

            // Broadcast it to clients only if the client is initialized(finished handshaking)

            for(ServerClient client: this.serverData.clients){
                if(client.initialized){
                    try {
                        client.socket.getOutputStream(); //.write(...something nice here...);
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
