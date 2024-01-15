package objects;

import main.Game;

public class Potion extends GameObjects {

	public Potion(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;
		
		initHitBox(7,14);

		xDrawOffset = (int)(3 * Game.SCALE);
		yDrawOffset = (int)(2 * Game.SCALE);
	}
	
	public void update() {
		updateAnimationTick();	
	}

}
