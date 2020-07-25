package net.vasile2k.fugarel.window;

import net.vasile2k.fugarel.window.scenes.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

        this.frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scene.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                scene.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                scene.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                scene.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                scene.mouseExited(e);
            }
        });

        this.frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                scene.mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                scene.mouseMoved(e);
            }
        });

        this.frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                scene.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                scene.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                scene.keyReleased(e);
            }
        });

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
