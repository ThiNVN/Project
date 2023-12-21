package utilz;

public class constant {
	
	public static class EnemyConstants {
		public static final int SKELETON = 0;
		
		public static final int IDLE = 0;
		public static final int WALK = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;
		public static final int REACT = 5;
		
		public static final int SKELETON_WIDTH_DEFAULT = 43;
		public static final int SKELETON_HEIGHT_DEFAULT = 37;
		
		public static final int SKELETON_WIDTH = (int)(SKELETON_WIDTH_DEFAULT * Game.SCALE);
		public static final int SKELETON_HEIGHT = (int)(SKELETON_HEIGHT_DEFAULT * Game.SCALE);
		
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch(enemy_type) {
			case SKELETON:
				switch(enemy_state) {
				case IDLE:
					return 11;
				case WALK:
					return 13;
				case ATTACK:
					return 18;
				case HIT:
					return 8;
				case DEAD:
					return 15;
				case REACT:
					return 4;
				}
			}
			
			return 0;
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
}
