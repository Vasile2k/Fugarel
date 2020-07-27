package net.vasile2k.fugarel.networking.server;

import java.io.IOException;
import java.net.Socket;

public class ClientListenerThread implements Runnable {

    private Socket client;

    public ClientListenerThread(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        try {
            int rawData = 0;
            while((rawData = this.client.getInputStream().read()) != -1){
                byte data = (byte)rawData;
                // Read until packet end
                // Parse packet
            }
            // Disconnected, remove client
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
