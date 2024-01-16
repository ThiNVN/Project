package objects;

import main.Game;

public class Spike extends GameObjects {

	public Spike(int x, int y, int objType) {
		super(x, y, objType);
		
		initHitBox(32,16);
		xDrawOffset = 0;
		yDrawOffset = (int)(Game.SCALE * 16);
		hitbox.y += yDrawOffset;
	}

}
