package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.gamestate;

import static utilz.constant.Directions.*;
import main.GamePanel;

public class KeyboardInputs implements KeyListener {

	private GamePanel gamePanel;

	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(gamestate.state) {
		case MENU:
			gamePanel.GetGame().getMenu().keyReleased(e);
			break;
		case PLAYING:
			gamePanel.GetGame().getPlaying().keyReleased(e);
			break;
		default:
			break;
		
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(gamestate.state) {
		case MENU:
			gamePanel.GetGame().getMenu().keyPressed(e);
			break;
		case PLAYING:
			gamePanel.GetGame().getPlaying().keyPressed(e);
			break;
		default:
			break;
		
		}
		

	}

}