package net.vasile2k.fugarel.networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerMainThread implements Runnable {

    private ServerData serverData;

    public ServerMainThread(int port){
        this.serverData = new ServerData();
        this.serverData.port = port;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void run() {

        this.serverData.clients = new ArrayList<>();

        Thread updateThread = new Thread(new ClientUpdateThread(this.serverData));
        updateThread.start();

        try {
            this.serverData.server = new ServerSocket(this.serverData.port);

            while (true){
                ServerClient newClient = new ServerClient();
                newClient.socket = this.serverData.server.accept();
                System.out.println("Got new client!");
                newClient.listenerThread = new Thread(new ClientListenerThread(this.serverData, newClient));
                this.serverData.clients.add(newClient);
                newClient.listenerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(ServerClient client: this.serverData.clients){
            // Can't be unsafe since the thread is not locking anything
            // noinspection deprecation
            client.listenerThread.stop();
            try {
                client.listenerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // noinspection deprecation
        updateThread.stop();
        try {
            updateThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
