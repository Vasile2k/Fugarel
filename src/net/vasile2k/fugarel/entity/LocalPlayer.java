package net.vasile2k.fugarel.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class LocalPlayer extends Player {

    private Image playerTexture;

    public LocalPlayer(String name){
        super(name);
        try {
            this.playerTexture = ImageIO.read(this.getClass().getResourceAsStream("/res/player/local.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.width = this.playerTexture.getWidth(null);
        this.height = this.playerTexture.getHeight(null);
    }

    @Override
    public void render(Graphics g, Camera camera) {
        g.setFont(new Font("Consolas", Font.BOLD, 25));
        g.setColor(Color.ORANGE);
        g.drawString(this.name, (int)(this.x - camera.getX() + this.x/2.0F - this.name.length()*6.0F), (int)(this.y - camera.getY() - 25.0F));
        g.drawImage(this.playerTexture, (int)(this.x - camera.getX()), (int)(this.y - camera.getY()), null);
    }

    @Override
    public void update() {

    }

    @Override
    public Image getImage() {
        return this.playerTexture;
    }
}
