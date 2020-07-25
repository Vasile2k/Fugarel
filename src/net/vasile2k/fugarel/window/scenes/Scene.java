package net.vasile2k.fugarel.window.scenes;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class Scene implements MouseListener, MouseMotionListener, KeyListener {

    public abstract void update();

    public abstract void repaint(Graphics g);

}
