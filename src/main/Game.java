package main;

import java.awt.Graphics;

import gamestates.GameOptions;
import gamestates.gamestate;
import gamestates.playing;
import ui.AudioOptions;
import gamestates.menu;

public class Game implements Runnable { 		

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private playing playing;
	private menu menu;
	private GameOptions gameOptions;
	private AudioOptions audioOptions;
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		
		startGameLoop();
//		menu = new menu(this);
//		playing = new playing(this);
	}

	

	private void initClasses() {
		menu = new menu(this);
		playing = new playing(this);
		audioOptions = new AudioOptions();
		gameOptions = new GameOptions(this);
	}


	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		switch (gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case OPTIONS:
			gameOptions.update();
			break;
		case QUIT:
		default:
			System.exit(0);
			break;

		}
	}
	
	public void render(Graphics g) {
		switch(gamestate.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		case OPTIONS:
			gameOptions.draw(g);
			break;
		default:
			break;
		}
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		
		
		long previous = System.nanoTime();

 		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;
		
		while (true) {

			
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previous) / timePerUpdate;
			deltaF += (currentTime - previous) / timePerFrame;
			previous = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
			

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + "|UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}

	}
	public void windowFocusLost() {
		if(gamestate.state == gamestate.PLAYING) {
			playing.GetPlayer().resetDirBooleans();
		}
		
	}
	public menu getMenu() {
		return menu;
	}
	public playing getPlaying() {
		return playing;
	}
	
	public GameOptions getGameOptions() {
		return gameOptions;
	}
	
	public AudioOptions getAudioOptions() {
		return audioOptions;
	}
	

}