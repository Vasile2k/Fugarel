package net.vasile2k.fugarel.entity;

import java.awt.*;

public abstract class Player extends Entity {

    protected String name;

    public Player(String name){
        this.name = name;
    }

    /**
     * Render the player to it's relative position in world
     * @param camera
     */
    public abstract void render(Graphics g, Camera camera);
}
