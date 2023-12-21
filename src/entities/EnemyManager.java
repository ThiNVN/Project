package entities;

import java.awt.image.BufferedImage;

import gamestate.Playing;
import utilz.LoadSave;

public class EnemyManager {
	private Playing playing;
	private BufferedImage[][] skeletonArr;
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}
	private void loadEnemyImgs() {
		skeletonArr = new BufferedImage[5][9];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.)
	}
}
