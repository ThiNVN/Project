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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(gamestate.state) {
		case MENU:
			gamePanel.GetGame().getMenu().mouseClicked(e);
			break;
		case PLAYING:
			gamePanel.GetGame().getPlaying().mouseClicked(e);
			break;
		default:
			break;
		}
		
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}