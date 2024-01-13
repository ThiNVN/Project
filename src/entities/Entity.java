package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity {
	protected float x,y;
	protected int width,height;
	protected Rectangle2D.Float hitBox;
	protected int aniTick,aniDex;
	protected int state;
	protected float airSpeed;
	protected boolean inAir = false;
	protected int maxHealth;
	protected int currentHealth = maxHealth;
	protected Rectangle2D.Float attackBox;
	protected float walkSpeed = 1.5f * Game.SCALE;
	
	public Entity(float x,float y, int width,int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	protected void drawAttackBox(Graphics g, int lvlOffsetX) {
		g.setColor(Color.red);
		g.drawRect((int)attackBox.x - lvlOffsetX, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
	}
	protected void drawHitbox(Graphics g, int xLvlOffset) {
			g.setColor(Color.RED);
			g.drawRect((int)hitBox.x - xLvlOffset, (int)hitBox.y,(int)hitBox.width,(int)hitBox.height);
	}
	protected void initHitBox(int width,int height) {
		hitBox = new Rectangle2D.Float(x, y, (int)width * Game.SCALE, (int)height * Game.SCALE);
	}

	public void deleteHitBox() {
		hitBox.width = 0;
		hitBox.height = 0;
	}
	public Rectangle2D.Float getHitBox() {
		return hitBox;
	}
	public int getstate() {
		return state;
	}
	public int getAniIndex() {
		return aniDex;
	}
}
