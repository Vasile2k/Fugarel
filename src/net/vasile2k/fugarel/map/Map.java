package net.vasile2k.fugarel.map;

import net.vasile2k.fugarel.entity.Camera;
import net.vasile2k.fugarel.window.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Map {

    public static final int BLOCK_SIZE = 32;

    private HashMap<Integer, Block> blocksById;

    private int[][] blockMap;

    private Map(){
        blocksById = new HashMap<>();
    }

    public static Map loadLevel(String pathToLevel, Block[] blocks, HashMap<Color, Integer> blockIds){
        Map map = new Map();

        BufferedImage imageMap = null;
        try {
            imageMap = ImageIO.read(Map.class.getResourceAsStream(pathToLevel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert imageMap != null;

        map.blockMap = new int[imageMap.getWidth()][imageMap.getHeight()];

        for(Block block: blocks){
            map.blocksById.put(block.getId(), block);
        }

        for(int i = 0; i < map.blockMap.length; ++i){
            for(int j = 0; j < map.blockMap[i].length; ++j){
                int rgb = imageMap.getRGB(i, j);
                Color color = new Color(rgb);
                int id = 0;
                try{
                    id = blockIds.get(color);
                }catch (NullPointerException e){
                    // Pass here, empty block
                }
                map.blockMap[i][j] = id;
            }
        }

        return map;
    }

    /**
     * Render the world relative to camera position
     * @param camera
     */
    public void render(Graphics g, Camera camera){
        for(int i = 0; i < this.blockMap.length; ++i) {
            for (int j = 0; j < this.blockMap[i].length; ++j) {
                int blockId = this.blockMap[i][j];
                if(blockId != 0){
                    int xPos = i * BLOCK_SIZE - (int)camera.getX();
                    int yPos = j * BLOCK_SIZE - (int)camera.getY();
                    if(xPos >= 0 && xPos < Window.FRAME_WIDTH && yPos >= 0 && yPos < Window.FRAME_HEIGHT){
                        g.drawImage(this.blocksById.get(blockId).getBlockTexture(), xPos, yPos, null);
                    }
                }
            }
        }
    }

}
