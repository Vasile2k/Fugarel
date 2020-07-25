package net.vasile2k.fugarel.window.scenes;

import net.vasile2k.fugarel.window.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class SceneMenu extends Scene {

    private Image background;
    private Image foreground;
    private float backgroundPosition;

    public SceneMenu(){
        try {
            this.background = ImageIO.read(this.getClass().getResourceAsStream("/res/ui/sky_bg.png"));
            this.foreground = ImageIO.read(this.getClass().getResourceAsStream("/res/ui/wood_fg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.backgroundPosition = 0.0F;
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
    }
}
