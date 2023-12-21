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
//	public final static String LEVEL_ONE_DATA = "level_one_data.png";
	public final static String LEVEL_ONE_DATA = "level_one_data_long.png";
	public final static String NIGHTBORNE_SPRITE = "Nightborne_sprite.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_button.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String MENU_BACKGROUND_IMG = "menu_bg.png";
	
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
			
			BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
			int[][] lvlData = new int[img.getHeight()][img.getWidth()];
			
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
