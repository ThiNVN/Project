package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.gamestate;
import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private GamePanel gamePanel;

	public MouseInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (gamestate.state) {
		case PLAYING:
			gamePanel.GetGame().getPlaying().mouseDragged(e);
			break;
		case OPTIONS:
			gamePanel.GetGame().getGameOptions().mouseDragged(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (gamestate.state) {
		case MENU:
			gamePanel.GetGame().getMenu().mouseMoved(e);
			break;
		case PLAYING:
			gamePanel.GetGame().getPlaying().mouseMoved(e);
			break;
		case OPTIONS:
			gamePanel.GetGame().getGameOptions().mouseMoved(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(gamestate.state) {
		case PLAYING:
			gamePanel.GetGame().getPlaying().mouseClicked(e);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (gamestate.state) {
		case MENU:
			gamePanel.GetGame().getMenu().mousePressed(e);
			break;
		case PLAYING:
			gamePanel.GetGame().getPlaying().mousePressed(e);
			break;
		case OPTIONS:
			gamePanel.GetGame().getGameOptions().mousePressed(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (gamestate.state) {
		case MENU:
			gamePanel.GetGame().getMenu().mouseReleased(e);
			break;
		case PLAYING:
			gamePanel.GetGame().getPlaying().mouseReleased(e);
			break;
		case OPTIONS:
			gamePanel.GetGame().getGameOptions().mouseReleased(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}