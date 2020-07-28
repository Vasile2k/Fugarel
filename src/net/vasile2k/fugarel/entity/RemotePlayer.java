package net.vasile2k.fugarel.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class RemotePlayer extends Player {

    private Image playerTexture;
    private int serverId;

    public RemotePlayer(int serverId, String name){
        super(name);
        this.x = 100;
        this.y = 100;
        this.serverId = serverId;
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
        g.setFont(new Font("Consolas", Font.BOLD, 25));
        g.setColor(Color.BLUE);
        g.drawString(this.name, (int)(this.x - camera.getX() + this.width/2.0F - this.name.length()*6.0F), (int)(this.y - camera.getY() - 25.0F));
        g.drawImage(this.playerTexture, (int)(this.x - camera.getX()), (int)(this.y - camera.getY()), null);
    }

    @Override
    public void update() {

    }

    @Override
    public Image getImage() {
        return this.playerTexture;
    }

    public int getServerId() {
        return serverId;
    }
}
