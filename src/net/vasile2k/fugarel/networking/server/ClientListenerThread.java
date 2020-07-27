package net.vasile2k.fugarel.networking.server;

import java.io.IOException;
import java.net.Socket;

public class ClientListenerThread implements Runnable {

    private ServerData serverData;
    private Socket clientSocket;
    private ServerClient serverClient;

    public ClientListenerThread(ServerData serverData, ServerClient client){
        this.serverData = serverData;
        this.clientSocket = client.socket;
        this.serverClient = client;
    }

    @Override
    public void run() {
        System.out.println("Listening for client input");
        try {
            int rawData = 0;
            while((rawData = this.clientSocket.getInputStream().read()) != -1){
                byte data = (byte)rawData;
                // Read until packet end
                // Parse packet
            }
            // Disconnected, remove client
            System.out.println("Client disconnected");
            this.serverData.clients.remove(this.serverClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
