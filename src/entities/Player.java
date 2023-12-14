package entities;


import static utilz.constant.playerConstants.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import utilz.LoadSave;

public class Player extends Entity {

	private BufferedImage[][] animations;
	private	int aniTick,aniDex,aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean left, up ,right, down;
	private boolean moving = false, attacking = false;
	private float playerSpeed = 1.0f;
	private int[][] lvlData;
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimation();
	}
	
	public void update() {
		
		updatePos();
		
		updateHitBox();
		
		setAnimation();
		
		updateAnimationTick();	
		
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniDex],(int) x, (int) y, width, height, null);
		drawHitbox(g);
	}
	
	
	
	private void setAnimation() {
		
		int startAni = playerAction;
		if(moving) {
			playerAction = RUNNING;
		}
		else {
			playerAction = IDLE;
		}
		if (attacking) {
			playerAction = ATTACK_1;
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
			}
		}
	}

	

	private void updatePos() {
		moving = false;
		if ( left && !right) {
			x -= playerSpeed;
			moving = true;
		}else if(right && !left){
			x += playerSpeed;
			moving = true;
		}
		
		if (up && !down) {
			y -= playerSpeed;
			moving = true;
		}else if(down && !up) {
			y += playerSpeed;
			moving = true;
		}
	}
	
	private void loadAnimation() {
		

		
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PlAYER_ATLAS);
			
			animations = new BufferedImage[9][6];
			for (int j= 0; j < animations.length;j++)
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
			}
		
		
	}
	public void loadLvlData(int[][] lvlData ) {
		this.lvlData = lvlData;
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

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
		
	}
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
}
