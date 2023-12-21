package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Level.LevelManager;
import entities.EnemyManager;
import entities.Player;
import main.Game;
import ui.PauseOverlay;

public class playing extends State implements StateMethods {
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private PauseOverlay pauseOverlay;
	private boolean paused = false;
	
	
	public playing(Game game) {
		super(game);
		 initClasses();
	}
	
	
	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		player = new Player(200, 200, (int)( 64 * Game.SCALE), (int) (40 * Game.SCALE));
		 player.loadLvlData(levelManager.getCurrentlevel().getLevelData());
		 pauseOverlay = new PauseOverlay(this);
	}
	
	


	@Override
	public void update() {
		if (!paused) {
			levelManager.update();
			player.update();
			enemyManager.update();
		} else {
			pauseOverlay.update();
		}
	}


	@Override
	public void draw(Graphics g) {
		levelManager.draw(g);
		player.render(g);
		enemyManager.draw(g, xLvlOffset);

		if (paused)
			pauseOverlay.draw(g);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) 
			player.setAttacking(true);
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if (paused)
			pauseOverlay.mousePressed(e);

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseReleased(e);

	}

	public void mouseDragged(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseDragged(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseMoved(e);

	}


	@Override
	public void keyPressed(KeyEvent e) {
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

	@Override	
	public void keyReleased(KeyEvent e) {
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
	
	public void unpauseGame() {
		paused = false;
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
		
	}
	public Player GetPlayer() {
		return player;
	}
	
}
