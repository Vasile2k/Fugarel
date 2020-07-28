package net.vasile2k.fugarel.networking.server;

import java.net.Socket;

public class ServerClient {

    private int id;
    private static int last_id = 0;
    protected int x = 0;
    protected int y = 0;
    protected String name = "";

    protected boolean initialized = false;

    protected Socket socket;
    protected Thread listenerThread;

    public ServerClient(){
        this.id = last_id++;
    }

    public int id(){
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerClient client = (ServerClient) o;
        return id == client.id;
    }
}
