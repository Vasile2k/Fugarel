package net.vasile2k.fugarel.networking.server;

import java.net.Socket;

public class ServerClient {

    protected int x = 0;
    protected int y = 0;
    protected String name = "";

    protected boolean initialized = false;

    protected Socket socket;
    protected Thread listenerThread;

}
