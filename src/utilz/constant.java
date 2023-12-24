package utilz;

import main.Game;

public class constant {
	public static class Environments {
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		
		public static final int SMOL_CLOUD_WIDTH_DEFAULT = 75;
		public static final int SMOL_CLOUD_HEIGHT_DEFAULT = 25;
			
		public static final int BIG_CLOUD_WIDTH = (int)(BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int)(BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int SMOL_CLOUD_WIDTH = (int)(BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int SMOL_CLOUD_HEIGHT = (int)(BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
	}
	
	public static class UI {
		
		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}
		
		public static class URMButtons {
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

		}
		
		public static class VolumeButtons {
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
		
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}
	}
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class playerConstants{
		public static final int RUNNING = 1;
		public static final int IDLE = 0;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int GROUND = 4;
		public static final int HIT = 5;
		public static final int ATTACK_1 = 6;
		public static final int ATTACK_JUMP_1 = 7;
		public static final int ATTACK_JUMP_2 = 8;
		
		public static int GetSpriteAmount(int player_action) {
			switch(player_action) {
			
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
			case JUMP:
			case ATTACK_1:
			case ATTACK_JUMP_1:
			case ATTACK_JUMP_2:
				return 3;
			case GROUND:
				return 2;
			case FALLING:
			default:
				return 1;
			
			}
		}
	}
	
	public static class EnemyConstants {
		public static final int NIGHTBORNE = 0;
		
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HURT = 3;
		public static final int DEAD = 4;
		
		public static final int NIGHTBORNE_WIDTH_DEFAULT = 80;
		public static final int NIGHTBORNE_HEIGHT_DEFAULT = 80;
		
		public static final int NIGHTBORNE_WIDTH = (int)(NIGHTBORNE_WIDTH_DEFAULT * Game.SCALE);
		public static final int NIGHTBORNE_HEIGHT = (int)(NIGHTBORNE_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int NIGHTBORNE_DRAWOFFSET_X = (int)(21 * Game.SCALE);
		public static final int NIGHTBORNE_DRAWOFFSET_Y = (int)(9 * Game.SCALE);
		
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch(enemy_type) {
			case NIGHTBORNE:
				switch(enemy_state) {
				case IDLE:
					return 9;
				case RUNNING:
					return 6;
				case ATTACK:
					return 12;
				case HURT:
					return 5;
				case DEAD:
					return 23;
				}
			}
			
			return 0;
		}
	}
	
}
