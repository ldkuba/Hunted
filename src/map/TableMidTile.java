package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import action.MoveAction;
import gui.Button;
import main.Globals;
import main.Main;
import main.Player;

public class TableMidTile extends Tile
{
	
	public TableMidTile(int type, int x, int y, Main app, GameContainer gc)
	{
		super(type, x, y, app, gc);
		this.walkable = false;

		this.pointCost = 0;

		try
		{
			tileImage = new Image("res/textures/tiles/tableMid.png");
		}catch (SlickException e)
		{
			e.printStackTrace();
		}

		
	}

}
