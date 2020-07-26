package net.vasile2k.fugarel.window.scenes;

import javafx.scene.input.KeyCode;
import net.vasile2k.fugarel.GameClient;
import net.vasile2k.fugarel.Main;
import net.vasile2k.fugarel.window.Window;
import net.vasile2k.fugarel.window.ui.Button;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class SceneConnect extends Scene {

    private Button playButton;

    private String ipOfServer;
    private String playerName;

    private Rectangle ipInputArea;
    private Rectangle nameInputArea;

    private boolean ipInputSelected;

    public SceneConnect(){
        this.ipOfServer = "";
        this.playerName = "";
        this.playButton = new Button(415, 450, "play", () -> ((GameClient)Main.getCurrentGame()).requestNewScene(new SceneGame(this.ipOfServer, this.playerName)));
        this.ipInputArea = new Rectangle(390, 130, 500, 50);
        this.nameInputArea = new Rectangle(390, 330, 500, 50);
        this.ipInputSelected = true;
    }

    @Override
    public void update() {

    }

    @Override
    public void repaint(Graphics g) {
        // background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Window.FRAME_WIDTH, Window.FRAME_HEIGHT);

        g.setFont(new Font("Consolas", Font.BOLD, 25));

        // IP input box
        g.setColor(this.ipInputSelected ? Color.ORANGE : Color.GRAY);
        g.drawString("Type IP here:", 420, this.ipInputArea.y - 30);
        g.drawRect(this.ipInputArea.x, this.ipInputArea.y, this.ipInputArea.width, this.ipInputArea.height);
        g.drawString(ipOfServer, Window.FRAME_WIDTH/2 - ipOfServer.length()*28/4, this.ipInputArea.y + 35);

        // Name input box
        g.setColor(!this.ipInputSelected ? Color.ORANGE : Color.GRAY);
        g.drawString("Enter your name here:", 420, this.nameInputArea.y - 30);
        g.drawRect(this.nameInputArea.x, this.nameInputArea.y, this.nameInputArea.width, this.nameInputArea.height);
        g.drawString(playerName, Window.FRAME_WIDTH/2 - playerName.length()*28/4, this.nameInputArea.y + 35);

        // button
        this.playButton.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
            ((GameClient) Main.getCurrentGame()).requestNewScene(new SceneMenu());
        }else{
            if(this.ipInputSelected){
                if("0123456789.:".indexOf((int)e.getKeyChar()) != -1){
                    ipOfServer += e.getKeyChar();
                }else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE && ipOfServer.length() > 0){
                    ipOfServer = ipOfServer.substring(0, ipOfServer.length() - 1);
                }
            }else{
                if(Character.isLetter(e.getKeyChar())){
                    playerName += e.getKeyChar();
                }else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE && ipOfServer.length() > 0){
                    playerName = playerName.substring(0, playerName.length() - 1);
                }
            }
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
        this.playButton.mouseClicked(e);
        if(this.ipInputArea.contains(e.getPoint())){
            this.ipInputSelected = true;
        }else if(this.nameInputArea.contains(e.getPoint())){
            this.ipInputSelected = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.playButton.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.playButton.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.playButton.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.playButton.mouseExited(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.playButton.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.playButton.mouseMoved(e);
    }
}
