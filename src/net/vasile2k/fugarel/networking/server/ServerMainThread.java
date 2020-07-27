package net.vasile2k.fugarel.networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMainThread implements Runnable {

    private int port;

    protected ServerSocket server;
    protected List<Socket> clients;

    public ServerMainThread(int port){
        this.port = port;
    }

    @Override
    public void run() {

        try {
            this.server = new ServerSocket(this.port);
            this.clients = new ArrayList<>();

            while (true){
                Socket client = this.server.accept();
                this.clients.add(client);
                new Thread(new ClientListenerThread(client)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
