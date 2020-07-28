package net.vasile2k.fugarel.window.scenes;

import net.vasile2k.fugarel.GameClient;
import net.vasile2k.fugarel.GameServer;
import net.vasile2k.fugarel.Main;
import net.vasile2k.fugarel.entity.*;
import net.vasile2k.fugarel.map.Block;
import net.vasile2k.fugarel.map.Map;
import net.vasile2k.fugarel.networking.PacketDecoder;
import net.vasile2k.fugarel.networking.SocketPacketReader;
import net.vasile2k.fugarel.networking.packets.*;
import net.vasile2k.fugarel.window.Window;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SceneGame extends Scene {

    private String ip;
    private int port;

    private Block[] blocks;
    private Map level;

    private Camera camera;
    private LocalPlayer player;

    private List<RemotePlayer> remotePlayers;

    private Socket serverConnection;

    private Thread serverReceivingThread;
    private Thread serverSendingThread;

    public SceneGame(String serverIp, String playerName){
        if(serverIp.contains(":")){
            this.ip = serverIp.split(":")[0];
            this.port = Integer.parseInt(serverIp.split(":")[1]);
        }else{
            this.ip = serverIp;
            this.port = GameServer.DEFAULT_PORT;
        }

        List<Block> _blocks = Block.loadAllBlocks();
        this.blocks = new Block[_blocks.size()];
        _blocks.toArray(this.blocks);
        HashMap<Color, Integer> mapToBlocks = new HashMap<>();
        mapToBlocks.put(Color.RED, 1);
        mapToBlocks.put(Color.GREEN, 2);
        mapToBlocks.put(Color.BLUE, 3);
        this.level = Map.loadLevel("/res/levels/level.png", this.blocks, mapToBlocks);

        this.camera = new Camera();
        this.player = new LocalPlayer(playerName);
        this.player.setX(100.0F);
        this.player.setY(100.0F);
        this.remotePlayers = new ArrayList<>();

        System.out.println("Entering game on " + this.ip + ":" + this.port);
        this.connect();
    }

    @Override
    public void update() {
        // update player
        float playerNewX = this.player.getX() + this.player.getVelX();
        float playerNewY = this.player.getY() + this.player.getVelY();
        float playerVelY = this.player.getVelY();
        playerVelY += 0.1F;
        if(playerVelY > 10.0F){
            playerVelY = 10.0F;
        }

        if(!CollisionHelper.playerIntersectsMap(this.player, this.level, playerNewX, playerNewY)){
            this.player.setX(playerNewX);
            this.player.setY(playerNewY);
        }
        this.player.setVelY(playerVelY);

        // update camera
        if(this.player.getX() < this.camera.getX() + Window.FRAME_WIDTH/4.0F){
            this.camera.setX(this.camera.getX() - 1.0F);
        }
        if(this.player.getX() > this.camera.getX() + 3.0F*Window.FRAME_WIDTH/4.0F){
            this.camera.setX(this.camera.getX() + 1.0F);
        }
    }

    @Override
    public void repaint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Window.FRAME_WIDTH, Window.FRAME_HEIGHT);

        this.level.render(g, this.camera);
        this.player.render(g, this.camera);

        for(RemotePlayer rp: this.remotePlayers){
            rp.render(g, this.camera);
        }
    }

    public void connect(){
        try {
            this.serverConnection = new Socket(this.ip, this.port);
        } catch (IOException e) {
//            e.printStackTrace();
            System.err.println("Can't connect to server!");
        }

        HelloClientPacket helloClientPacket = (HelloClientPacket) this.readPacket();

        IntroduceMyselfPacket introduceMyselfPacket = new IntroduceMyselfPacket(this.player.getName());
        this.sendPacket(introduceMyselfPacket);

        this.serverReceivingThread = new Thread(() -> {
            while (true){
                Packet p = this.readPacket();

                if(p instanceof NewPlayerPacket){
                    this.remotePlayers.add(new RemotePlayer(((NewPlayerPacket) p).id, ((NewPlayerPacket) p).name));
                }
                if(p instanceof RemovePlayerPacket){
                    this.remotePlayers.removeIf(remotePlayer -> remotePlayer.getServerId() == ((RemovePlayerPacket) p).playerId);
                }
            }
        });

        this.serverSendingThread = new Thread(() -> {
            while (true){

            }
        });

        this.serverReceivingThread.start();
        this.serverSendingThread.start();
    }

    @SuppressWarnings("deprecation")
    public void disconnect(){
        try {
            this.serverReceivingThread.stop();
            this.serverReceivingThread.join();

            this.serverSendingThread.stop();
            this.serverSendingThread.join();

            this.serverConnection.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendPacket(Packet p){
        try {
            this.serverConnection.getOutputStream().write(p.toBytes());
        } catch (IOException e) {
//            e.printStackTrace();
            System.err.println("Error sending packet!");
        }
    }

    private Packet readPacket(){
        byte[] pack = SocketPacketReader.readPacketFromSocket(this.serverConnection);
        return PacketDecoder.decodeFromBytes(pack);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
            System.out.println("Disconnecting from server");
            this.disconnect();
            ((GameClient) Main.getCurrentGame()).requestNewScene(new SceneMenu());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A){
            this.player.setVelX(-2.0F);
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            this.player.setVelX(2.0F);
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !this.player.isJumping){
            this.player.setVelY(-7.0F);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D){
            this.player.setVelX(0.0F);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
