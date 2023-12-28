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
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.constant.Environments.*;

public class playing extends State implements StateMethods {
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private boolean paused = false;
	
	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	
	private BufferedImage backgroundImg, bigCloud, smolCloud;
	private int[] smolCloudsPos;
	private Random rnd = new Random();
	
	private boolean gameOver;
	private boolean playerDying;
	
	
	public playing(Game game) {
		super(game);
		 initClasses();
		 
		 backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		 bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
		 smolCloud = LoadSave.GetSpriteAtlas(LoadSave.SMOL_CLOUDS);
		 smolCloudsPos = new int[8];
		 for(int i = 0; i < smolCloudsPos.length; i++)
			 smolCloudsPos[i] = (int)(70 * Game.SCALE) + rnd.nextInt((int)(150 * Game.SCALE));
	}
	
	
	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		player = new Player(200, 200, (int)( 64 * Game.SCALE), (int) (40 * Game.SCALE), this);
		player.loadLvlData(levelManager.getCurrentlevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
	}
	
	


	@Override
	public void update() {
		if (!paused && !gameOver) {
			levelManager.update();
			player.update();
			checkCloseToBorder();
			enemyManager.update(levelManager.getCurrentlevel().getLevelData(), player);
		} 
		else if (gameOver) {
			gameOverOverlay.update();
		}
		else if(playerDying) {
			player.update();
		}
		else {
			pauseOverlay.update();
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

		if (paused) {
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}else if(gameOver) {
			gameOverOverlay.draw(g);
		}
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
		playerDying = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
		
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
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
		} else
			gameOverOverlay.mousePressed(e);

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseReleased(e);
		} else
			gameOverOverlay.mouseReleased(e);
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
		} else
			gameOverOverlay.mouseMoved(e);
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


	public void setPlayerDying(boolean playerDyin) {
		this.playerDying = playerDying;
		
	}
}
