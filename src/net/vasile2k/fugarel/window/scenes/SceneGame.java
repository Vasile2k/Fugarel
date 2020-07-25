package net.vasile2k.fugarel.window.scenes;

import net.vasile2k.fugarel.GameClient;
import net.vasile2k.fugarel.GameServer;
import net.vasile2k.fugarel.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class SceneGame extends Scene {

    private String ip;
    private int port;

    public SceneGame(String serverIp){
        if(serverIp.contains(":")){
            this.ip = serverIp.split(":")[0];
            this.port = Integer.parseInt(serverIp.split(":")[1]);
        }else{
            this.ip = serverIp;
            this.port = GameServer.DEFAULT_PORT;
        }
        System.out.println("Entering game on " + this.ip + ":" + this.port);
        this.connect();
    }

    @Override
    public void update() {

    }

    @Override
    public void repaint(Graphics g) {

    }

    public void connect(){

    }

    public void disconnect(){

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

    }

    @Override
    public void keyReleased(KeyEvent e) {

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
