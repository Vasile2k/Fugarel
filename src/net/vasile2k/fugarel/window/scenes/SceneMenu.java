package net.vasile2k.fugarel.window.scenes;

import net.vasile2k.fugarel.GameClient;
import net.vasile2k.fugarel.Main;
import net.vasile2k.fugarel.window.Window;
import net.vasile2k.fugarel.window.ui.Button;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class SceneMenu extends Scene {

    private Image background;
    private Image foreground;
    private float backgroundPosition;

    private Button connectButton;
    private Button exitButton;

    public SceneMenu(){
        try {
            this.background = ImageIO.read(this.getClass().getResourceAsStream("/res/ui/sky_bg.png"));
            this.foreground = ImageIO.read(this.getClass().getResourceAsStream("/res/ui/wood_fg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.backgroundPosition = 0.0F;

        this.connectButton = new Button(415, 150, "connect", () -> ((GameClient) Main.getCurrentGame()).requestNewScene(new SceneConnect()));
        this.exitButton = new Button(415, 430, "exit", () -> ((GameClient) Main.getCurrentGame()).stop());
    }

    @Override
    public void update() {
        this.backgroundPosition -= 1.0F;
        if(this.backgroundPosition < 0.0F){
            this.backgroundPosition += Window.FRAME_WIDTH;
        }
    }

    @Override
    public void repaint(Graphics g) {
        // background
        g.drawImage(this.background, (int)backgroundPosition, 0, null);
        g.drawImage(this.background, (int)backgroundPosition - Window.FRAME_WIDTH, 0, null);
        // foreground
        g.drawImage(this.foreground, 0, 0, null);
        // buttons
        this.connectButton.draw(g);
        this.exitButton.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.connectButton.mouseClicked(e);
        this.exitButton.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.connectButton.mousePressed(e);
        this.exitButton.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.connectButton.mouseReleased(e);
        this.exitButton.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.connectButton.mouseEntered(e);
        this.exitButton.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.connectButton.mouseExited(e);
        this.exitButton.mouseExited(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.connectButton.mouseDragged(e);
        this.exitButton.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.connectButton.mouseMoved(e);
        this.exitButton.mouseMoved(e);
    }
}
