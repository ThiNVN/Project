package Level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.gamestate;
import main.Game;
import utilz.LoadSave;

public class LevelManager {
	private Game game;
	private BufferedImage[] LevelSprite;
	private ArrayList<Level> levels;
	private int lvlIndex = 0;
	
	
	public LevelManager(Game game) {
		this.game = game;
//		LevelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		importOutsideSrpite();
		levels = new ArrayList<>();
		buildAllLevels();
	}
	
	public void loadNextLevel() {
		lvlIndex++;
		if(lvlIndex >= levels.size()) {
			lvlIndex = 0;
			System.out.println("Game Completed!!!");
			gamestate.state = gamestate.MENU;
		}
		
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().GetPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
	}
	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for(BufferedImage img : allLevels)
			levels.add(new Level(img));
 		
	}
	private void importOutsideSrpite() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		LevelSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)	
			for (int i = 0; i < 12 ; i++) {
				int index = j*12 + i;
				LevelSprite[index] = img.getSubimage(i* 32, j*32, 32, 32);
			}
		
	}
	public void draw(Graphics g, int lvlOffset) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for (int i = 0; i <levels.get(lvlIndex).getLevelData()[0].length; i++) {
				int index = levels.get(lvlIndex).getSpriteindex(i, j);
				g.drawImage(LevelSprite[index],Game.TILES_SIZE * i - lvlOffset,Game.TILES_SIZE * j  ,Game.TILES_SIZE,Game.TILES_SIZE, null);
			}
		
	}
	public void update() {
		
	}
	public Level getCurrentlevel() {
		return levels.get(lvlIndex);
	}
	
	public int getAmountOfLevels() {
		return levels.size();
	}
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	public int getLevelIndex(){
		return lvlIndex;
	}
}
