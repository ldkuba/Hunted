package main;

public class Globals
{
	public static final int TILE_WIDTH = 50;
	public static final int TILE_HEIGHT = 50;
	
	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;
	public static boolean FULLSCREEN = false;
	
	public static int MAX_FPS = 60;
	
	public static int MOUSE_SCROLL_THRESHOLD = 5;
	public static float MOUSE_SCROLL_SPEED = 0.5f;
	

	public static int SCREEN_RES = SCREEN_WIDTH/SCREEN_HEIGHT;
	
	public static final int NETWORK_PORT = 45454;
	
	public static final int MAX_POINTS_PER_TURN = 5;
	
	public static float PLAYER_LIGHT_STRENGTH = 200.0f;
	
	public static boolean STATIC_CAMERA = false;
	
	public static final int GUI_WIDTH = 156;
	
	public static final int GUI_HEIGHT = 169;
	
	public static final int ACTION_WIDTH = 128;
	public static final int ACTION_HEIGHT = 64;
	public static final float ACTION_Y = (1080-GUI_HEIGHT+52.5f)/1080.0f;
}
