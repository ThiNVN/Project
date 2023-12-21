package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
	
	public static final String PlAYER_ATLAS ="player_sprites.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public final static String LEVEL_ONE_DATA = "level_one_data.png";
	public final static String ENEMY_ATTACK_SPRITE = "Skeleton Attack.png";
	public final static String ENEMY_DEAD_SPRITE = "Skeleton Dead.png";
	public final static String ENEMY_HIT_SPRITE = "Skeleton Hit.png";
	public final static String ENEMY_IDLE_SPRITE = "Skeleton Idle.png";
	public final static String ENEMY_REACT_SPRITE = "Skeleton React.png";
	public final static String ENEMY_WALK_SPRITE = "Skeleton Walk.png";
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);

		try {
			 img = ImageIO.read(is);	
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				}
		}
		return img;
	}
		public static int[][] GetLevelData(){
			int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
			BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
			for (int j = 0;j < img.getHeight(); j++)
				for (int i = 0;i < img.getTileWidth(); i++) {
					Color color  = new Color(img.getRGB(i, j)); 
					int value = color.getRed();
					if ( value >= 48)
						value = 0;
					lvlData [j][i] = value;
				}
				return lvlData;	
		}
	
}
