package entities;


import static utilz.constant.playerConstants.*;
import static utilz.HelpMethods.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import audio.AudioPlayer;
import main.Game;
import utilz.LoadSave;

import gamestates.playing;

public class Player extends Entity {

	private BufferedImage[][] animations;
	private	int aniTick,aniDex,aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean left, up ,right, down, jump;
	private boolean moving = false, attacking = false;
	private float playerSpeed = 1.5f * Game.SCALE;
	private int[][] lvlData;
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;
	

//	jumping / Gravity	
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;
	
	//StatusBarUI
	private BufferedImage statusBarImg;
	
	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);
	
	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);
	
	private int maxHealth = 100;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;
	
	//AttackBox
	private Rectangle2D.Float attackBox;
	
	private int flipX = 0;
	private int flipW = 1;
	
	private boolean attackChecked;
	
	private playing playing;
	
	public Player(float x, float y, int width, int height, playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		loadAnimation();
		initHitBox(y, y, 20 * Game.SCALE, 27 * Game.SCALE);
		initAttackBox();
	}
	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitBox.x =x;
		hitBox.y = y;
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(20 * Game.SCALE), (int) (20* Game.SCALE));
		
	}

	public void update() {
		updateHealthBar();
		if (currentHealth <= 0) {
			if (playerAction != DEAD) {
				playerAction = DEAD;
				aniTick = 0;
				aniDex = 0;
				playing.setPlayerDying(true);
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
			} else if (aniDex == GetSpriteAmount(DEAD) - 1 && aniTick >= aniSpeed - 1) {
				playing.setGameOver(true);
				playing.getGame().getAudioPlayer().stopSong();
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
			} else
				updateAnimationTick();

			return;
		}
		
		updateAttackBox();
		
		updatePos();
		if(attacking) {
			checkAttack();
		}
		setAnimation();
		
		updateAnimationTick();	
		
	}
	
	private void checkAttack() {
		if(attackChecked || aniDex != 1) {
			return;
		}
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
		playing.getGame().getAudioPlayer().playAttackSound();
	}

	private void updateAttackBox() {
		if(right) {
			attackBox.x = hitBox.x + hitBox.width + (int)(Game.SCALE * 10);
		}else if(left) {
			attackBox.x = hitBox.x - hitBox.width - (int)(Game.SCALE * 10);
		}
		attackBox.y = hitBox.y + (Game.SCALE * 10);
		
	}

	private void updateHealthBar() {
		healthWidth = (int)((currentHealth / (float) maxHealth) * healthBarWidth);
	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[playerAction][aniDex],(int)(hitBox.x - xDrawOffset) - lvlOffset + flipX, (int)(hitBox.y - yDrawOffset), width * flipW, height, null);
//		drawHitbox(g, lvlOffset);
//		drawAttackBox(g, lvlOffset);
		drawUI(g);
	}
	
	
	
	private void drawAttackBox(Graphics g, int lvlOffsetX) {
		g.setColor(Color.red);
		g.drawRect((int)attackBox.x - lvlOffsetX, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		
		if(currentHealth <= 100 && currentHealth > 70) {
			g.setColor(Color.green);
			g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
		} else if (currentHealth <= 70 && currentHealth > 30) {
			g.setColor(Color.yellow);
			g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
		} else {
			g.setColor(Color.red);
			g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
		}
		
	}

	private void setAnimation() {
		
		int startAni = playerAction;
		if(moving) {
			playerAction = RUNNING;
		}
		else {
			playerAction = IDLE;
		}
		
		if(inAir) {
			if(airSpeed < 0)	
				playerAction = JUMP;
		}else
			playerAction = FALLING;
		
		if (attacking) {
			playerAction = ATTACK;
			if(startAni != ATTACK) {
				aniDex = 0;
				aniTick = 0;
				return;
			}
		}
		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniDex = 0;
		
	}

	private void updateAnimationTick() {
		aniTick++;
		if( aniTick >= aniSpeed) {
			aniTick = 0;
			aniDex++;
			if (aniDex >= GetSpriteAmount(playerAction)) {
				aniDex = 0;
				attacking = false;
				attackChecked = false;
			}
		}
	}

	private void updatePos() {
		moving = false;
		
		if(jump)
			jump();
			
//		if(!left && !right && !inAir)
//			return;
		if (!inAir)
			if ((!left && !right) || (right && left))
				return;
		
		float xSpeed = 0;
		
		if (left) {
			xSpeed -= playerSpeed;
			flipX = width;
			flipW = -1;
		}
		if(right) {
			xSpeed += playerSpeed;
			flipX = 0;
			flipW = 1;
		}
		if(!inAir) {
			if(!IsEntityOnFloor(hitBox, lvlData)) {
				inAir = true;
			}
		}
			
		
		if(inAir) {
			if(CanMoveHere(hitBox.x, hitBox.y + airSpeed,hitBox.width,hitBox.height, lvlData )) {
				hitBox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			}else {
				hitBox.y = GetEntityYPosUnderRoofOraboveFloor(hitBox,airSpeed);
				if(airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		}else {
			updateXPos(xSpeed);
			}
		moving = true;
	}
	
	private void jump() {
		if(inAir)
			return;
		playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
		inAir = true;
		airSpeed = jumpSpeed;
		
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitBox.x + xSpeed,hitBox.y ,hitBox.width,hitBox.height,lvlData)) {
			hitBox.x += xSpeed;
		}
		else {
			hitBox.x = GetEntityXPosNextToWall(hitBox,xSpeed);
		}
	}
	
	public void changeHealth(int value) {
		currentHealth += value;
		if(currentHealth <= 0) {
			currentHealth = 0;
			//gameOver();
		} else if(currentHealth >= maxHealth) {
			currentHealth = maxHealth;
		}
	}

	private void loadAnimation() {
		
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PlAYER_ATLAS);
			
			animations = new BufferedImage[7][8];
			for (int j= 0; j < animations.length;j++)
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
			}
		
			statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
		
	}
	public void loadLvlData(int[][] lvlData ) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitBox, lvlData)) {
			inAir = true;
		}
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
		
	}
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;
		
		hitBox.x = x;
		hitBox .y = y;
		
		if(!IsEntityOnFloor(hitBox, lvlData)) {
			inAir = true;
		}
	}
}
