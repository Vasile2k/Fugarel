package net.vasile2k.fugarel.networking.server;

import java.net.ServerSocket;
import java.util.List;

public class ServerData {

    protected int port;

    protected ServerSocket server;
    protected List<ServerClient> clients;

}
