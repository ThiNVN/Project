package Level;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Nightborne;
import main.Game;
import objects.GameContainer;
import objects.Potion;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetNightbornes;
import static utilz.HelpMethods.GetPlayerSpawn;

public class Level {
	
	private BufferedImage img;
	
	private int[][] lvlData;
	
	private ArrayList<Nightborne> nightbornes;
	private ArrayList<Potion> potions;
	private ArrayList<GameContainer> containers;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createPotions();
		createContainers();
		calcLvlOffsets();
		calcPlayerSpawn();
		
	}
	private void createContainers() {
		// TODO Auto-generated method stub
		
	}
	private void createPotions() {
		// TODO Auto-generated method stub
		
	}
	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
		
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
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
}
