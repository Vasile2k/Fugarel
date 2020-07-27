package net.vasile2k.fugarel;

import net.vasile2k.fugarel.networking.server.ServerMainThread;

public class GameServer implements Runnable {

    public static final int DEFAULT_PORT = 669;

    private int port;

    public GameServer(int port){
        this.port = port;
    }

    public GameServer(){
        this(DEFAULT_PORT);
    }

    @Override
    public void run() {
        System.out.println("Starting server on port " + port);
        new ServerMainThread(this.port).run();
    }
}


