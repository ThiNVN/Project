package entities;

import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOraboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.HelpMethods.IsFloor;
import static utilz.constant.Directions.LEFT;
import static utilz.constant.EnemyConstants.*;

import main.Game;

public class Nightborne extends Enemy {
	public Nightborne(float x, float y) {
		super(x, y, NIGHTBORNE_WIDTH, NIGHTBORNE_HEIGHT, NIGHTBORNE);
		initHitBox(x, y, (int)(31 * Game.SCALE), (int)(27 * Game.SCALE));
		
	}
	
	public void updateMove(int[][] lvlData, Player player) {
		if(firstUpdate) {
			firstUpdateCheck(lvlData);
		}
		
		if(inAir) {
			updateInAir(lvlData);
		}else {
			switch(enemyState) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				if(canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);
				}
				if(isPlayerCloseForAttack(player)) {
					newState(ATTACK);
				}
				
				move(lvlData);
				break;
			}
		}
	}
	
	public void update(int[][] lvlData, Player player) {
		updateMove(lvlData, player);
		updateAnimationTick();
	}
	
}
