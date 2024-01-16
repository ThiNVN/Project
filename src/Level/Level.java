	package Level;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Nightborne;
import main.Game;
import objects.GameContainer;
import objects.Potion;
import objects.Spike;
import utilz.HelpMethods;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetNightbornes;
import static utilz.HelpMethods.GetPlayerSpawn;

public class Level {
	
	private BufferedImage img;
	
	private int[][] lvlData;
	
	private ArrayList<Nightborne> nightbornes;
	private ArrayList<Potion> potions;
	private ArrayList<Spike> spikes;
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
		createSpikes();
		calcLvlOffsets();
		calcPlayerSpawn();
		
	}
	private void createSpikes() {
		spikes = HelpMethods.GetSpikes(img);
		
	}
	private void createContainers() {
		containers = HelpMethods.GetContainers(img);
		
	}
	private void createPotions() {
		potions = HelpMethods.GetPotions(img);
		
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
	public ArrayList<Potion> getPotions() {
		return potions;
	}
	public ArrayList<GameContainer> getContainers() {
		return containers;
	}
	public ArrayList<Spike> getSpikes(){
		return spikes;
	}
}
