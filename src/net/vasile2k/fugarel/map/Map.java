package net.vasile2k.fugarel.map;

import net.vasile2k.fugarel.entity.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {

    private HashMap<Integer, Block> blocksById;

    private int[][] blockMap;

    private Map(){
        blocksById = new HashMap<>();
    }

    public static Map loadLevel(String pathToLevel, Block[] blocks, HashMap<Color, Integer> blockIds){
        Map result = new Map();

        return result;
    }

    /**
     * Render the world relative to camera position
     * @param camera
     */
    public void render(Camera camera){

    }

}
