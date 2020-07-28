package net.vasile2k.fugarel.networking.server;

import net.vasile2k.fugarel.networking.PacketDecoder;
import net.vasile2k.fugarel.networking.SocketPacketReader;
import net.vasile2k.fugarel.networking.packets.*;

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

        // Send hello
        HelloClientPacket helloClientPacket = new HelloClientPacket();
        this.sendPacket(helloClientPacket);

        // Wait for IntroduceMyselfPacket
        IntroduceMyselfPacket introPacket = (IntroduceMyselfPacket) this.readPacket();

        // Parse packet
        this.serverClient.name = introPacket.name;

        // Send player list
        for(ServerClient s: this.serverData.clients){
            if(s != this.serverClient){
                NewPlayerPacket newPlayerPacket = new NewPlayerPacket(s.id(), s.name);
                this.sendPacket(newPlayerPacket);
            }
        }

        // Tell other players we have a new player
        NewPlayerPacket newPlayerPacket = new NewPlayerPacket(this.serverClient.id(), this.serverClient.name);
        this.broadcast(newPlayerPacket);

        // Mark as initialized and ready for receiving updates
        this.serverClient.initialized = true;
        System.out.println(this.serverClient.name + " successfully connected!");

        // And so on
        while (true){
            byte[] newPacket = SocketPacketReader.readPacketFromSocket(this.clientSocket);
            if(newPacket == null){
                break;
            }

            Packet packet = PacketDecoder.decodeFromBytes(newPacket);
            // Do some shit with this packet

        }

        // Disconnected, remove client
        RemovePlayerPacket removePlayerPacket = new RemovePlayerPacket(this.serverClient.id());
        this.broadcast(removePlayerPacket);
        System.out.println(this.serverClient.name + " disconnected");
        this.serverData.clients.remove(this.serverClient);
    }

    private void sendPacket(Packet p){
        try {
            this.clientSocket.getOutputStream().write(p.toBytes());
        } catch (IOException e) {
//            e.printStackTrace();
            System.err.println("Error sending packet!");
        }
    }

    private void broadcast(Packet p){
        try {
            for(ServerClient s: this.serverData.clients){
                if(s != this.serverClient && s.initialized){
                    s.socket.getOutputStream().write(p.toBytes());
                }
            }
        } catch (IOException e) {
            System.err.println("Error broadcasting packet!");
        }
    }

    private Packet readPacket(){
        byte[] pack = SocketPacketReader.readPacketFromSocket(this.clientSocket);
        return PacketDecoder.decodeFromBytes(pack);
    }
}
