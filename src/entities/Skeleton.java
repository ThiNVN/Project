package entities;

import static utilz.constant.EnemyConstants.*;

public class Skeleton extends Enemy {
	public Skeleton(float x, float y) {
		super(x, y, SKELETON_WIDTH, SKELETON_HEIGHT, SKELETON);
	}
}