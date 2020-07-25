package net.vasile2k.fugarel.window.scenes;

import net.vasile2k.fugarel.GameClient;
import net.vasile2k.fugarel.Main;
import net.vasile2k.fugarel.window.Window;
import net.vasile2k.fugarel.window.ui.Button;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class SceneConnect extends Scene {

    private Button playButton;

    public SceneConnect(){
        this.playButton = new Button(415, 450, "play", () -> { });
    }

    @Override
    public void update() {

    }

    @Override
    public void repaint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Window.FRAME_WIDTH, Window.FRAME_HEIGHT);

        this.playButton.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
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
