package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import Level.LevelManager;
import entities.EnemyManager;
import entities.Player;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.constant.Environments.*;

public class playing extends State implements StateMethods {
	private Player player;
	private ObjectManager objectManager;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private boolean paused = false;
	
	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int maxLvlOffsetX;
	
	private BufferedImage backgroundImg, bigCloud, smolCloud;
	private int[] smolCloudsPos;
	private Random rnd = new Random();
	
	private boolean playerDying;
	
	private boolean gameOver;
	private boolean lvlCompleted;
	
	
	
	public playing(Game game) {
		super(game);
		 initClasses();
		 
		 backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		 bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
		 smolCloud = LoadSave.GetSpriteAtlas(LoadSave.SMOL_CLOUDS);
		 smolCloudsPos = new int[8];
		 for(int i = 0; i < smolCloudsPos.length; i++)
			 smolCloudsPos[i] = (int)(70 * Game.SCALE) + rnd.nextInt((int)(150 * Game.SCALE));
		 calcLvlOffset();
		 loadStartLevel();
	}
	
	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentlevel().getPlayerSpawn());
	}
	
	private void loadStartLevel() {
		enemyManager.loadEnemies(levelManager.getCurrentlevel());
		objectManager.loadObjects(levelManager.getCurrentlevel());
	}


	private void calcLvlOffset() {
		maxLvlOffsetX = levelManager.getCurrentlevel().getLvlOffset();		
	}


	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		objectManager = new ObjectManager(this);
		
		player = new Player(200, 200, (int)( 64 * Game.SCALE), (int) (40 * Game.SCALE), this);
		player.loadLvlData(levelManager.getCurrentlevel().getLevelData());
		
		player.setSpawn(levelManager.getCurrentlevel().getPlayerSpawn());
		
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
	}
	
	


	@Override
	public void update() {
		if(paused) {
			pauseOverlay.update();
		} else if(lvlCompleted) {
			levelCompletedOverlay.update();
		} else if(!gameOver) {
			levelManager.update();
			objectManager.update();
			player.update();
			enemyManager.update(levelManager.getCurrentlevel().getLevelData(), player);
			checkCloseToBorder();
		}	
		
	}
	
	private void checkCloseToBorder(){
		int playerX = (int) player.getHitBox().x;
		int diff = playerX - xLvlOffset;
		
		if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if(diff < leftBorder)
			xLvlOffset += diff - leftBorder;
		
		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if(xLvlOffset < 0)
			xLvlOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		
		drawClouds(g);
		
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemyManager.draw(g, xLvlOffset);
		objectManager.draw(g, xLvlOffset);

		if (paused) {
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} else if(gameOver) 
			gameOverOverlay.draw(g);
		else if (lvlCompleted)
				levelCompletedOverlay.draw(g);
		}


	private void drawClouds(Graphics g) {
		for (int i = 0; i < 3; i++)
			g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int)(204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		
		for (int i = 0; i < smolCloudsPos.length; i++ )
		g.drawImage(smolCloud, SMOL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smolCloudsPos[i], SMOL_CLOUD_WIDTH, SMOL_CLOUD_HEIGHT, null);
	}
	
	public void resetAll() {
		gameOver = false;
		paused = false;
		
		lvlCompleted = false;
		
		playerDying = false;
		
		player.resetAll();
		enemyManager.resetAllEnemies();
		objectManager.resetAllObject();
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}
	
	public void checkPotionTouched(Rectangle2D.Float hitbox) {
		objectManager.checkObjectTouched(hitbox);
	}
	
	public void checkObjectHit(Rectangle2D.Float attackBox) {
		objectManager.checkObjectHit(attackBox);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!gameOver) {
			if(e.getButton() == MouseEvent.BUTTON1) 
				player.setAttacking(true);
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mousePressed(e);
		else if (lvlCompleted)
			levelCompletedOverlay.mousePressed(e);
		}

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseReleased(e);
		else if (lvlCompleted)
			levelCompletedOverlay.mouseRealeased(e);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if(!gameOver) 
			if (paused)
				pauseOverlay.mouseDragged(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseMoved(e);
		else if (lvlCompleted)
			gameOverOverlay.mouseMoved(e);
		}
	}
	
	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted = levelCompleted;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver) {
			gameOverOverlay.keyPressed(e);
		}else {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(true);
				break;
			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(true);
				break;
			case KeyEvent.VK_ESCAPE:
				paused = !paused;
				break;
			}
		}
	}

	@Override	
	public void keyReleased(KeyEvent e) {
		if(!gameOver) {
			switch (e.getKeyCode()) {	
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;	
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				break;
			}
		}
	}
	
	
	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}
	public void unpauseGame() {
		paused = false;
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
		
	}
	public Player GetPlayer() {
		return player;
	}


	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
		
	}


	public void setPlayerDying(boolean playerDying) {
		this.playerDying = playerDying;
		
	}
	public LevelManager getLevelManager(){
		return levelManager;
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}
}
