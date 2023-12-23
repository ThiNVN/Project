package entities;

import static utilz.constant.EnemyConstants.*;

import main.Game;

public class Nightborne extends Enemy {
	public Nightborne(float x, float y) {
		super(x, y, NIGHTBORNE_WIDTH, NIGHTBORNE_HEIGHT, NIGHTBORNE);
		initHitBox(x, y, (int)(31 * Game.SCALE), (int)(27 * Game.SCALE));
		
	}
}
