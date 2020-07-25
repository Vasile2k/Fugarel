package net.vasile2k.fugarel.window.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Button {

    private Image buttonImage;
    private Image overlayImage;
    private Image pressedImage;

    private int x;
    private int y;
    private int width;
    private int height;

    private boolean isHovered;
    private boolean isPressed;

    public Button(int x, int y, String name){
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

}
