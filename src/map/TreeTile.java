package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import main.Globals;
import main.Main;

public class TreeTile extends Tile
{
	private static final int AP_COST = 0;

	public TreeTile(int type, int x, int y, Main app, GameContainer gc)
	{
		super(type, x, y, app, gc);
		this.walkable = false;
		
		this.pointCost = 0;
		
		try
		{
			tileImage = new Image("res/textures/tiles/tree.png");
		}catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	public int onClick()
	{
		return AP_COST;
	}
	
	@Override
	public void draw(int xOffset, int yOffset)
	{
		int imageX = this.x*50 - xOffset;
		int imageY = this.y*50 - yOffset;
		
		if(imageX > -Globals.TILE_WIDTH && imageX < Globals.SCREEN_WIDTH && imageY > -100 && imageY < Globals.SCREEN_HEIGHT)
		{
			tileImage.setColor(Image.TOP_LEFT, Main.currentLevel.getLightValue()[x][y-1][0], Main.currentLevel.getLightValue()[x][y-1][1], Main.currentLevel.getLightValue()[x][y-1][2], 1);
			tileImage.setColor(Image.TOP_RIGHT, Main.currentLevel.getLightValue()[x+1][y-1][0], Main.currentLevel.getLightValue()[x+1][y-1][1], Main.currentLevel.getLightValue()[x+1][y-1][2], 1);
			tileImage.setColor(Image.BOTTOM_RIGHT, Main.currentLevel.getLightValue()[x+1][y+1][0], Main.currentLevel.getLightValue()[x+1][y+1][1], Main.currentLevel.getLightValue()[x+1][y+1][2], 1);
			tileImage.setColor(Image.BOTTOM_LEFT, Main.currentLevel.getLightValue()[x][y+1][0], Main.currentLevel.getLightValue()[x][y+1][1], Main.currentLevel.getLightValue()[x][y+1][2], 1);
			
			tileImage.draw(imageX, imageY-Globals.TILE_HEIGHT, 50, 100);	
			
			if(this.selected)
			{
				selectionImage.setColor(Image.TOP_LEFT, Main.currentLevel.getLightValue()[x][y][0], Main.currentLevel.getLightValue()[x][y][1], Main.currentLevel.getLightValue()[x][y][2], 1);
				selectionImage.setColor(Image.TOP_RIGHT, Main.currentLevel.getLightValue()[x+1][y][0], Main.currentLevel.getLightValue()[x+1][y][1], Main.currentLevel.getLightValue()[x+1][y][2], 1);
				selectionImage.setColor(Image.BOTTOM_RIGHT, Main.currentLevel.getLightValue()[x+1][y+1][0], Main.currentLevel.getLightValue()[x+1][y+1][1], Main.currentLevel.getLightValue()[x+1][y+1][2], 1);
				selectionImage.setColor(Image.BOTTOM_LEFT, Main.currentLevel.getLightValue()[x][y+1][0], Main.currentLevel.getLightValue()[x][y+1][1], Main.currentLevel.getLightValue()[x][y+1][2], 1);
				this.selectionImage.draw(imageX, imageY, 50, 50);
			}
		}
		
	}
}