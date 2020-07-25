package net.vasile2k.fugarel;

import net.vasile2k.fugarel.window.Window;
import net.vasile2k.fugarel.window.scenes.Scene;
import net.vasile2k.fugarel.window.scenes.SceneMenu;

public class GameClient implements Runnable {

    private Window window;

    private Scene currentScene;

    private boolean isRunning = false;

    public GameClient(){
        this.window = new Window();
        this.currentScene = new SceneMenu();
        this.window.setScene(this.currentScene);
    }

    public void stop(){
        this.isRunning = false;
    }

    public void requestNewScene(Scene newScene){
        this.currentScene = newScene;
        this.window.setScene(this.currentScene);
    }

    @Override
    public void run() {
        System.out.println("Starting game...");
        this.window.display();

        this.isRunning = true;

        while(isRunning){

            this.window.update();
            this.window.redraw();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.window.destroy();
    }
}
