package net.vasile2k.fugarel.entity;

import net.vasile2k.fugarel.map.Map;

import java.awt.*;

public class CollisionHelper {

    public static boolean playerIntersectsMap(Player player, Map map, float playerNewX, float playerNewY){
        boolean retVal = false;
        Rectangle playerBox = new Rectangle((int)playerNewX, (int)playerNewY, (int)player.width, (int)player.height);

        boolean intersectedOnX = false;
        boolean intersectedOnY = false;

        for(int i = 0; i < map.getBlockMap().length; ++i) {
            for (int j = 0; j < map.getBlockMap()[i].length; ++j) {
                if(map.getBlockMap()[i][j] != 0){
                    int xPos = i * Map.BLOCK_SIZE;
                    int yPos = j * Map.BLOCK_SIZE;
                    if(xPos < playerNewX - 2*Map.BLOCK_SIZE || xPos > playerNewX + (int)player.width + Map.BLOCK_SIZE){
                        continue;
                    }
                    Rectangle blockBox = new Rectangle(xPos, yPos, Map.BLOCK_SIZE, Map.BLOCK_SIZE);
                    if(playerBox.intersects(blockBox)){
                        // check left-right
                        if(!intersectedOnX){
                            Rectangle playerBoxLeftRight = new Rectangle((int)playerNewX, (int)player.y, (int)player.width, (int)player.height);
                            if(playerBoxLeftRight.intersects(blockBox)){
                                // If left-right new position intersects, then we move safely up-down and compute the left-right position unti we hit the wall
                                if(player.x > playerNewX){
                                    player.x = xPos + Map.BLOCK_SIZE;
                                }else{
                                    player.x = xPos - player.width;
                                }
                                intersectedOnX = true;
                            }else{
                                player.x = playerNewX;
                            }
                        }

                        // check top-bottom
                        if(!intersectedOnY){
                            Rectangle playerBoxTopBottom = new Rectangle((int)player.x, (int)playerNewY, (int)player.width, (int)player.height);
                            if(playerBoxTopBottom.intersects(blockBox)) {
                                if(player.y > playerNewY){
                                    player.y = yPos + Map.BLOCK_SIZE;
                                }else{
                                    player.y = yPos - player.height;
                                }
                                intersectedOnY = true;
                            }else{
                                player.y = playerNewY;
                            }
                        }
                        retVal = true;
                    }
                }
            }
        }
        return retVal;
    }
}
