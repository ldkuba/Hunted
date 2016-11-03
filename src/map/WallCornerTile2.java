package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import main.Main;

public class WallCornerTile2 extends Tile
{
	private static final int AP_COST = 0;

	public WallCornerTile2(int type, int x, int y, Main app, GameContainer gc)
	{
		super(type, x, y, app, gc);
		this.walkable = false;
		
		this.pointCost = 0;
		
		try
		{
			tileImage = new Image("res/textures/tiles/wallCorner2.png");
		}catch (SlickException e)
		{
			e.printStackTrace();
		}
	}


}