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

    public SceneConnect(){
        this.ipOfServer = "";
        this.playButton = new Button(415, 450, "play", () -> ((GameClient)Main.getCurrentGame()).requestNewScene(new SceneGame(this.ipOfServer)));
    }

    @Override
    public void update() {

    }

    @Override
    public void repaint(Graphics g) {
        // background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Window.FRAME_WIDTH, Window.FRAME_HEIGHT);

        // IP input box
        g.setColor(Color.GRAY);
        g.setFont(new Font("Consolas", Font.BOLD, 25));
        g.drawString("Type IP here:", 420, 100);
        g.drawRect(390, 130, 500, 50);
        g.drawString(ipOfServer, Window.FRAME_WIDTH/2 - ipOfServer.length()*28/4, 165);

        // button
        this.playButton.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
            ((GameClient) Main.getCurrentGame()).requestNewScene(new SceneMenu());
        }else{
            if("0123456789.:".indexOf((int)e.getKeyChar()) != -1){
                ipOfServer += e.getKeyChar();
            }else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE && ipOfServer.length() > 0){
                ipOfServer = ipOfServer.substring(0, ipOfServer.length() - 1);
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
