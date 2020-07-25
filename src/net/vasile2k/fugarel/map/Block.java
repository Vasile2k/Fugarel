package net.vasile2k.fugarel.map;

import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Block {

    private Image blockTexture;
    private int id;

    public Block(Image blockTexture, int id){
        this.blockTexture = blockTexture;
        this.id = id;
    }

    public static List<Block> loadAllBlocks(){
        List<Block> blocks = new ArrayList<Block>();

        InputStream files = Block.class.getResourceAsStream("/res/tiles");

        String fileNames = "";
        try {
            fileNames = new String(IOUtils.readFully(files, -1, true));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] filesToRead = fileNames.split("\n");

        int lastBlockId = 1;

        for(String fileName: filesToRead){
            Image blockTexture = null;
            try {
                blockTexture = ImageIO.read(Block.class.getResourceAsStream("/res/tiles/" + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            blocks.add(new Block(blockTexture, lastBlockId++));
        }

        return blocks;
    }

}
