package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Nightborne;
import static utilz.constant.EnemyConstants.NIGHTBORNE;

import main.Game;

public class LoadSave {
	
	public static final String PlAYER_ATLAS ="player_sprites.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public final static String LEVEL_ONE_DATA = "level_one_data.png";
	public final static String NIGHTBORNE_SPRITE = "Nightborne_sprite.png";
	
	
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
	
	public static ArrayList<Nightborne> GetNightbornes() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		ArrayList<Nightborne> list = new ArrayList<>();
		for (int j = 0;j < img.getHeight(); j++)
			for (int i = 0;i < img.getTileWidth(); i++) {
				Color color  = new Color(img.getRGB(i, j)); 
				int value = color.getGreen();
				if ( value == NIGHTBORNE) {
					list.add(new Nightborne(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
				}	
		}
		return list;
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
