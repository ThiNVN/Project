package utilz;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelpMethods {
//	private static final int tileXPos = 0;

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		 if(!IsSolid(x,y,lvlData))
			 if(!IsSolid(x+width,y+height, lvlData))
				 if(!IsSolid(x+width,y, lvlData))
					 if(!IsSolid(x,y+height, lvlData))
						 return true;
			 return false;
		
	}

	private static boolean IsSolid(float x , float y,int[][] lvlData ) {
		
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;
		
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		int value = lvlData[(int) yIndex][(int) xIndex];
		if(value >= 48 || value < 0 || value != 11)
			return true;
		return false;	
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox,float xSpeed) {
		int currentTile = (int)(hitBox.x / Game.TILES_SIZE);
		if (xSpeed > 0 ) {
//			right
			int tileXPos = currentTile * Game.TILES_SIZE; 
			int xOffset = (int)(Game.TILES_SIZE - hitBox.width);
			return tileXPos + xOffset - 1;
		}else
//			left
			return currentTile * Game.TILES_SIZE;
		}
	public static float GetEntityYPosUnderRoofOraboveFloor(Rectangle2D.Float hitBox,float airSpeed) {
		int currentTile = (int)(hitBox.y / Game.TILES_SIZE);
		if(airSpeed > 0) {
//			falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int)(Game.TILES_SIZE - hitBox.height);
			return tileYPos + yOffset - 1;
		}else {
//			jumping
			return currentTile * Game.TILES_SIZE;
		}
	}
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
		if(!IsSolid(hitBox.x,hitBox.y + hitBox.height + 1,lvlData)) {
			if(!IsSolid(hitBox.x + hitBox.width,hitBox.y + hitBox.height + 1,lvlData)) {
				return false;
			}
		}
		return true;
			
	}
}


