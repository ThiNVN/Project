package entities;

import static utilz.constant.EnemyConstants.*;
import static utilz.constant.*;

import java.awt.geom.Rectangle2D;

import main.Game;

import static utilz.HelpMethods.*;
import static utilz.constant.Directions.*;

public abstract class Enemy extends Entity {
	protected int  enemyType;
	protected boolean firstUpdate = true;
	protected float walkSpeed = 0.5f * Game.SCALE;
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	protected boolean active = true;
	protected boolean attackChecked;
	
	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x , y, width, height);
		this.enemyType = enemyType;
		
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
		walkSpeed = Game.SCALE * 0.35f;
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if(!IsEntityOnFloor(hitBox, lvlData)) {
			inAir = true;
		}
		firstUpdate = false;
	}
	
	protected void updateInAir(int[][] lvlData) {
		if(CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
			hitBox.y += airSpeed;
			airSpeed += GRAVITY;
		}else {
			inAir = false;
			hitBox.y = GetEntityYPosUnderRoofOraboveFloor(hitBox, airSpeed);
			tileY = (int) (hitBox.y / Game.TILES_SIZE);
		}
	}
	
	protected void move(int[][] lvlData) {
		float xSpeed = 0;
		
		if(walkDir == LEFT) {
			xSpeed = -walkSpeed;
		}else {
			xSpeed = walkSpeed;
		}
		if(CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
			if(IsFloor(hitBox, xSpeed, lvlData)) {
				hitBox.x += xSpeed;
				return;
			}
		}
		changeWalkDir();
	}
	
	protected void turnTowardsPlayer(Player player) {
		if(player.hitBox.x > hitBox.x) {
			walkDir = RIGHT;
		}else {
			walkDir = LEFT;
		}
	}
	
	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int) (player.getHitBox().y / Game.TILES_SIZE);
		if(playerTileY == tileY) {
			if(isPlayerInRange(player)) {
				if(IsSightClear(lvlData, hitBox, player.hitBox, tileY)) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean isPlayerInRange(Player player) {
		int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absValue <= attackDistance * 5;
	}
	
	protected boolean isPlayerCloseForAttack(Player player) {
		int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absValue <= attackDistance;
	}

	protected void newState(int state) {
		this.state = state;
		aniTick = 0;
		aniDex = 0;
	}
	
	public void hurt(int amount) {
		currentHealth -= amount;
		if(currentHealth <= 0) {
//			deleteHitBox();
			newState(DEAD);
		} else {
			newState(HURT);
		}
	}
	
	protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
		if(attackBox.intersects(player.hitBox)) {
			player.changeHealth(-GetEnemyDmg(enemyType));
		}
		attackChecked = true;
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if(aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniDex++;
			if(aniDex >= GetSpriteAmount(enemyType, state)) {
				aniDex = 0;
				
				switch(state) {
				case ATTACK, HURT -> state = IDLE;
				case DEAD -> active = false;
				}
			}
		}
	}
	
	public void update(int[][] lvlData) {
		updateMove(lvlData);
		updateAnimationTick();
	}
	
	public void updateMove(int[][] lvlData) {
		if(firstUpdate) {
			if(!IsEntityOnFloor(hitBox, lvlData)) {
				inAir = true;
			}
			firstUpdate = false;
		}
		
		if(inAir) {
			if(CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
				hitBox.y += airSpeed;
				airSpeed += GRAVITY;
			}else {
				inAir = false;
				hitBox.y = GetEntityYPosUnderRoofOraboveFloor(hitBox, airSpeed);
			}
		}else {
			switch(state) {
			case IDLE:
				state = RUNNING;
				break;
			case RUNNING:
				float xSpeed = 0;
				
				if(walkDir == LEFT) {
					xSpeed = -walkSpeed;
				}else {
					xSpeed = walkSpeed;
				}
				if(CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
					if(IsFloor(hitBox, xSpeed, lvlData)) {
						hitBox.x += xSpeed;
						return;
					}
				}
				changeWalkDir();
				
				break;
			}
		}
	}
	
	protected void changeWalkDir() {
		if(walkDir == LEFT) {
			walkDir = RIGHT;
		}else {
			walkDir = LEFT;
		}
	}
	
	public void resetEnemy() {
		hitBox.x = x;
		hitBox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		airSpeed = 0;
	}

	public boolean isActive() {
		return active;
	}
}
