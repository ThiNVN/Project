package entities;

import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOraboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.HelpMethods.IsFloor;
import static utilz.constant.Directions.LEFT;
import static utilz.constant.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import static utilz.constant.Directions.*;

import main.Game;

public class Nightborne extends Enemy {
	//AttackBox
	private int attackBoxOffsetX;
	
	public Nightborne(float x, float y) {
		super(x, y, NIGHTBORNE_WIDTH, NIGHTBORNE_HEIGHT, NIGHTBORNE);
		initHitBox(31,28);
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(60 * Game.SCALE), (int)(28 * Game.SCALE));
		attackBoxOffsetX = (int) (Game.SCALE * 23);
	}

	public void updateBehavior(int[][] lvlData, Player player) {
		if(firstUpdate) {
			firstUpdateCheck(lvlData);
		}
		
		if(inAir) {
			updateInAir(lvlData);
		}else {
			switch(state) {
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
			case ATTACK:
				if(aniDex == 0) {
					attackChecked = false;
				}
				
				if(aniDex == 10 && !attackChecked) {
					checkEnemyHit(attackBox, player);
				}
				break;
			case HURT:
				
				break;
			}
		}
	}

	public int flipX() {
		if(walkDir == RIGHT) {
			return width;
		} else {
			return 0;
		}
	}
	
	public int flipW() {
		if(walkDir == RIGHT) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}

	private void updateAttackBox() {
		attackBox.x = hitBox.x - attackBoxOffsetX;
		attackBox.y = hitBox.y;
		
	}
	
}
