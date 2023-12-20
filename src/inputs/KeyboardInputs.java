package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			gamePanel.GetGame().GetPlayer().setUp(false);
			break;
		case KeyEvent.VK_A:
			gamePanel.GetGame().GetPlayer().setLeft(false);
			break;
		case KeyEvent.VK_S:
			gamePanel.GetGame().GetPlayer().setDown(false);
			break;
		case KeyEvent.VK_D:
			gamePanel.GetGame().GetPlayer().setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			gamePanel.GetGame().GetPlayer().setJump(false);
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			gamePanel.GetGame().GetPlayer().setUp(true);
			break;
		case KeyEvent.VK_A:
			gamePanel.GetGame().GetPlayer().setLeft(true);
			break;
		case KeyEvent.VK_S:
			gamePanel.GetGame().GetPlayer().setDown(true);
			break;
		case KeyEvent.VK_D:
			gamePanel.GetGame().GetPlayer().setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			gamePanel.GetGame().GetPlayer().setJump(true);
			break;
		}

	}

}