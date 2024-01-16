package objects;



import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Level.Level;
import entities.Player;
import gamestates.playing;
import utilz.LoadSave;
import static utilz.constant.ObjectConstants.*;

	public class ObjectManager {
		
		private BufferedImage[][] potionImgs, containerImgs;
		private BufferedImage spikeImg;
		private playing playing;
		private ArrayList<Potion> potions;
		private ArrayList<GameContainer> containers;
		private ArrayList<Spike> spikes;
		
		public ObjectManager(playing playing) {
			this.playing = playing;
			loadImgs();
		}
		
		public void applyEffectToPlayer(Potion p) {
			if (p.getObjType() == RED_POTION)
				playing.GetPlayer().changeHealth(RED_POTION_VALUE);
			else
				playing.GetPlayer().changePower(BLUE_POTION_VALUE);
		}
		
		public void checkSpikeTouched(Player p) {
			for (Spike s : spikes)
				if (s.getHitbox().intersects(p.getHitBox()))
					p.kill();
		}
		
		public void checkObjectTouched(Rectangle2D.Float hitbox) {
			for (Potion p : potions)
				if (p.isActive()) {
					if (hitbox.intersects(p.getHitbox())) {
						p.setActive(false);
						applyEffectToPlayer(p);
					}
				}
		}
		
		public void checkObjectHit(Rectangle2D.Float attackbox) {
			for (GameContainer gc : containers)
				if (gc.isActive()) {
					if (gc.getHitbox().intersects(attackbox)) {
						gc.setAnimation(true);
						int type = 0;
						if (gc.getObjType() == BARREL)
							type = 1;
						potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
						return;
					}
				}
		}
		
		public void loadObjects(Level newLevel) {
			potions = new ArrayList<>(newLevel.getPotions());
			containers = new ArrayList<>(newLevel.getContainers());
			spikes = new ArrayList<>(newLevel.getSpikes());
		}

		private void loadImgs() {
			BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
			potionImgs = new BufferedImage[2][7];
			
			for (int j = 0; j < potionImgs.length; j++) {
				for (int i = 0; i < potionImgs[j].length; i++) {
					potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
				}
			}
			
			BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
			containerImgs = new BufferedImage[2][8];
			
			for (int j = 0; j < containerImgs.length; j++) {
				for (int i = 0; i < containerImgs[j].length; i++) {
					containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
				}
			}
			spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);
		}
		public void update() {
			for (Potion p : potions) {
				if (p.isActive())
					p.update();
			}
			for (GameContainer gc : containers) {
				if(gc.isActive())
					gc.update();
			}
		}
		public void draw(Graphics g, int xLvlOffset) {
			drawPotions(g, xLvlOffset);
			drawContainers(g, xLvlOffset);
			drawTraps(g, xLvlOffset);
		}

		private void drawTraps(Graphics g, int xLvlOffset) {
			for (Spike s : spikes)
				g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
		}

		private void drawContainers(Graphics g, int xLvlOffset) {
			for (GameContainer gc : containers)
				if (gc.isActive()) {
					int type = 0;
					if (gc.getObjType() == BARREL)
						type = 1;
					g.drawImage(containerImgs[type][gc.getAniDex()], (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset), (int) (gc.getHitbox().y - gc.getyDrawOffset()), CONTAINER_WIDTH,
							CONTAINER_HEIGHT, null);
				}
		}

		private void drawPotions(Graphics g, int xLvlOffset) {
			for (Potion p : potions)
				if (p.isActive()) {
					int type = 0;
					if (p.getObjType() == RED_POTION)
						type = 1;
					g.drawImage(potionImgs[type][p.getAniDex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT,
							null);
				}
		}
		
		public void resetAllObject() {
			
			loadObjects(playing.getLevelManager().getCurrentlevel());
			
			for (Potion p : potions)
				p.reset();

			for (GameContainer gc : containers)
				gc.reset();
		}
	}
