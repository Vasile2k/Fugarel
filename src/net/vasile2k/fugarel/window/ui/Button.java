package net.vasile2k.fugarel.window.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class Button implements MouseListener, MouseMotionListener {

    private Image buttonImage;
    private Image overlayImage;
    private Image pressedImage;

    private Runnable action;

    private int x;
    private int y;
    private int width;
    private int height;

    private boolean isHovered;
    private boolean isPressed;

    public Button(int x, int y, String name, Runnable action){
        this.action = action;

        this.isHovered = false;
        this.isPressed = false;

        this.x = x;
        this.y = y;
        try {
            this.buttonImage = ImageIO.read(this.getClass().getResourceAsStream("/res/ui/button_" + name + ".png"));
            this.overlayImage = ImageIO.read(this.getClass().getResourceAsStream("/res/ui/overlay_button.png"));
            this.pressedImage = ImageIO.read(this.getClass().getResourceAsStream("/res/ui/button_" + name + "_pressed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.width = buttonImage.getWidth(null);
        this.height = buttonImage.getHeight(null);
    }

    public void draw(Graphics g){
        if(this.isHovered){
            g.drawImage(overlayImage, x, y, null);
        }
        if(this.isPressed){
            g.drawImage(pressedImage, x, y, null);
        }else{
            g.drawImage(buttonImage, x, y, null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(this.isHovered){
            this.isPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(this.isHovered && this.isPressed){
            this.action.run();
        }
        this.isPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(e.getX() >= this.x && e.getX() <= (this.x + this.width) && e.getY() >= this.y && e.getY() <= (this.y + this.height)){
            this.isHovered = true;
        }else{
            this.isHovered = false;
        }
    }
}
