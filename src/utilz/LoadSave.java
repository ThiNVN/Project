package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import entities.Nightborne;
import static utilz.constant.EnemyConstants.NIGHTBORNE;

import main.Game;

public class LoadSave {
	
	public static final String PlAYER_ATLAS ="player_sprites.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public final static String NIGHTBORNE_SPRITE = "Nightborne_sprite.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background_edit.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_button.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String MENU_BACKGROUND_IMG = "menuu_bg.png";
	public static final String PLAYING_BG_IMG = "game_bg.png";
	public static final String BIG_CLOUDS = "big_clouds.png";
	public static final String SMOL_CLOUDS = "small_clouds.png";
	public static final String STATUS_BAR = "health_power_bar.png";
	public static final String DEATH_SCREEN = "death_screen.png";
	public static final String OPTIONS_MENU = "options_background.png";
	public static final String COMPLETED_IMG = "completed_stage.png";
	
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);

		try {
			 img = ImageIO.read(is);	
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				}
		}
		return img;
	}
	
	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/lvls"); 
		File file = null;
		
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} 
		
		File[] files = file.listFiles();
		File[] fileSorted = new File[files.length];
		
		for(int i = 0; i < fileSorted.length; i++)
			for(int j = 0; j < files.length; j++) {
				if(files[j].getName().equals("" + (i + 1) + ".png"))
					fileSorted[i] = files[j]; 
				
			}
		
		BufferedImage[] imgs = new BufferedImage[fileSorted.length];
		for(int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(fileSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return imgs;
	}
	

		
	
}
