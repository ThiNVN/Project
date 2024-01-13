package entities;

import gamestates.playing;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.playing;
import Level.Level;
import utilz.LoadSave;
import static utilz.constant.EnemyConstants.*;

public class EnemyManager {
	private playing playing;
	private BufferedImage[][] nightborneArr;
	private ArrayList<Nightborne> nightbornes = new ArrayList<>();
	
	public EnemyManager(playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}
	
	public void loadEnemies(Level level) {
		nightbornes = level.getNightbornes();
		System.out.println("Size of Nightbornes: " + nightbornes.size());
	}

	public void update(int[][] lvlData, Player player) {
		boolean isAnyActive = false;
		for(Nightborne n : nightbornes) 
			if(n.isActive()) {
				n.update(lvlData, player);
				isAnyActive = true;
			}
		if(!isAnyActive)
			playing.setLevelCompleted(true);
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawNightbornes(g, xLvlOffset);
	}
	
	private void drawNightbornes(Graphics g, int xLvlOffset) {
		for(Nightborne n : nightbornes) {
			if(n.isActive()) {
				g.drawImage(nightborneArr[n.getstate()][n.getAniIndex()], (int)n.getHitBox().x - xLvlOffset - 54 + n.flipX(), (int)n.getHitBox().y - 54, NIGHTBORNE_WIDTH * n.flipW(), NIGHTBORNE_HEIGHT, null);
		//		n.drawHitbox(g, xLvlOffset + 20);
		//		n.drawAttackBox(g, xLvlOffset);
			}
		}
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for(Nightborne n : nightbornes) {
			if(attackBox.intersects(n.getHitBox())) {
				n.hurt(10);
				return;
			}
		}
	}

	private void loadEnemyImgs() {
		nightborneArr = new BufferedImage[5][23];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.NIGHTBORNE_SPRITE);
		for(int j=0; j < nightborneArr.length; j++) {
			for(int i=0; i < nightborneArr[j].length; i++) {
				nightborneArr[j][i] = temp.getSubimage(i * NIGHTBORNE_WIDTH_DEFAULT, j * NIGHTBORNE_HEIGHT_DEFAULT, NIGHTBORNE_WIDTH_DEFAULT, NIGHTBORNE_HEIGHT_DEFAULT);
			}
		}
	}
	
	public void resetAllEnemies() {
		for(Nightborne n : nightbornes) {
			n.resetEnemy();
		}
	}
}

//TODO: Add LevelCompleted audio when LevelCompletedOverlay is implemented//
