package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gamestates.playing;
import gamestates.gamestate;
import main.Game;

public class GameOverOverlay {
	private playing playing;
	public GameOverOverlay(playing playing) {
		this.playing = playing;
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

		g.setColor(Color.white);
		g.drawString("Game Over", Game.GAME_WIDTH / 2, 150);
		g.drawString("Press esc to enter Main Menu!", Game.GAME_WIDTH / 2, 300);
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			playing.resetAll();
			gamestate.state = gamestate.MENU;
		}
	}
}
