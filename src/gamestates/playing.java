package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Level.LevelManager;
import entities.Player;
import main.Game;

public class playing extends State implements StateMethods {
	private Player player;
	private LevelManager levelManager;
	public playing(Game game) {
		super(game);
		 initClasses();
	}
	
	
	private void initClasses() {
		levelManager = new LevelManager(game);
		player = new Player(200, 200, (int)( 64 * Game.SCALE), (int) (40 * Game.SCALE));
		 player.loadLvlData(levelManager.getCurrentlevel().getLevelData());
	}
	
	


	@Override
	public void update() {
		levelManager.update();
		player.update();
		
	}


	@Override
	public void draw(Graphics g) {
		levelManager.draw(g);
		player.render(g);
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) 
			player.setAttacking(true);
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	
	public void windowFocusLost() {
		player.resetDirBooleans();
		
	}
	public Player GetPlayer() {
		return player;
	}
	
}
