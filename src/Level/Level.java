package Level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Nightborne;
import main.Game;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetNightbornes;

public class Level {
	
	private BufferedImage img;
	
	private int[][] lvlData;
	
	private ArrayList<Nightborne> nightbornes;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		calcLvlOffsets();
		
	}
	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}
	private void createEnemies() {
		nightbornes = GetNightbornes(img);
		
	}
	private void createLevelData() {
		lvlData = GetLevelData(img);
		
	}
	public int getSpriteindex(int x, int y) {
		return lvlData[y][x];
	}
	public int[][] getLevelData(){
		return lvlData;
	}
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}
	public ArrayList<Nightborne> getNightbornes() {
		return nightbornes;
	}
}
