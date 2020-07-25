package net.vasile2k.fugarel.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class RemotePlayer extends Player {

    private Image playerTexture;

    public RemotePlayer(String name){
        super(name);
        try {
            this.playerTexture = ImageIO.read(this.getClass().getResourceAsStream("/res/player/remote.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.width = this.playerTexture.getWidth(null);
        this.height = this.playerTexture.getHeight(null);
    }

    @Override
    public void render(Graphics g, Camera camera) {

    }

    @Override
    public void update() {

    }

    @Override
    public Image getImage() {
        return this.playerTexture;
    }
}
