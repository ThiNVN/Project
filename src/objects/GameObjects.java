package objects;

import static utilz.constant.ANI_SPEED;
import static utilz.constant.ObjectConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class GameObjects {
	protected int x,y, objType;
	protected Rectangle2D.Float hitbox;
	protected boolean doAnimation, active = true;
	protected int aniTick,aniDex;
	protected int xDrawOffset, yDrawOffset;
	
	public GameObjects(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if(aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniDex++;
			if(aniDex >= GetSpriteAmount(objType)) {
				aniDex = 0;
				if(objType == BARREL || objType == BOX ) {
					doAnimation = false;
					active = false;
				}
			}
		}
	}
	
	public void reset() {
		aniTick = 0;
		aniDex = 0;
		active = true;
		if(objType == BARREL || objType == BOX )
			doAnimation = false;
		else
			doAnimation = true;
	}
	
	protected void initHitBox(int width,int height) {
		hitbox = new Rectangle2D.Float(x, y, (int)width * Game.SCALE, (int)height * Game.SCALE);
	}
	public void drawHitbox(Graphics g, int xLvlOffset) {
		g.setColor(Color.RED);
		g.drawRect((int)hitbox.x - xLvlOffset, (int)hitbox.y,(int)hitbox.width,(int)hitbox.height);
	}

	public int getObjType() {
		return objType;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setAnimation(boolean doAnimation) {
		this.doAnimation = doAnimation;
	}

	public int getxDrawOffset() {
		return xDrawOffset;
	}

	public int getyDrawOffset() {
		return yDrawOffset;
	}

	public int getAniDex() {
		return aniDex;
	}
	
}
