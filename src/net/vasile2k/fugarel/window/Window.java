package net.vasile2k.fugarel.window;

import net.vasile2k.fugarel.window.scenes.Scene;

import javax.swing.*;
import java.awt.*;

public class Window {

    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 720;

    private JFrame frame;
    private JPanel panel;

    private Scene scene;

    public Window(){
        this.frame = new JFrame("Fugarel");
        Dimension frameSize = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
        this.frame.setSize(frameSize);
        this.frame.setUndecorated(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation(new Point(screenSize.width/2 - FRAME_WIDTH/2, screenSize.height/2 - FRAME_HEIGHT/2));
        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        this.panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                scene.repaint(g);
            }
        };

        this.panel.setSize(frameSize);
        this.frame.add(this.panel);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void display() {
        frame.setVisible(true);
    }

    public void update() {
        this.scene.update();
    }

    public void redraw() {
        this.frame.repaint();
    }

    public void destroy() {
        this.frame.dispose();
    }
}
