package entities;

import gamestates.playing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import utilz.LoadSave;
import static utilz.constant.EnemyConstants.*;

public class EnemyManager {
	private playing playing;
	private BufferedImage[][] nightborneArr;
	private ArrayList<Nightborne> nightbornes = new ArrayList<>();
	
	public EnemyManager(playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}
	
	private void addEnemies() {
		nightbornes = LoadSave.GetNightbornes();
		
	}

	public void update() {
		for(Nightborne n : nightbornes) {
			n.update();
		}
	}
	
	public void draw(Graphics g) {
		drawNightbornes(g);
	}
	
	private void drawNightbornes(Graphics g) {
		for(Nightborne n : nightbornes) {
			g.drawImage(nightborneArr[n.getEnemyState()][n.getAniIndex()], (int)n.getHitBox().x, (int)n.getHitBox().y, NIGHTBORNE_WIDTH, NIGHTBORNE_HEIGHT, null);
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
}
