package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import main.Globals;
import main.Main;

public class WallVerticalTile extends Tile
{
	private static final int AP_COST = 0;

	public WallVerticalTile(int type, int x, int y, Main app, GameContainer gc)
	{
		
		super(type, x, y, app, gc);
			
		this.walkable = false;
		
		this.pointCost = 0;
		
	
	}
	
	public void chooseImage() {
		
		
		
		try
		{
			if (this.x == 0)  {
				tileImage = new Image("res/textures/tiles/wallVertical2.png");
			} else if (this.x == Main.currentLevel.getLvlWidth()-1) {
				tileImage = new Image("res/textures/tiles/wallVertical1.png");
			} else {
				if ((Main.currentLevel.getType(this.x-1, this.y) == 1) && ((Main.currentLevel.getType(this.x+1, this.y) == 0)))
				{
					//Wood Wall Grass
					tileImage = new Image("res/textures/tiles/wallVertical2.png");
				} else if ((Main.currentLevel.getType(this.x-1, this.y) == 0) && ((Main.currentLevel.getType(this.x+1, this.y) == 1)))
				{
					//Grass Wall Wood
					tileImage = new Image("res/textures/tiles/wallVertical1.png");
				} else 
				{
					//default
					tileImage = new Image("res/textures/tiles/wallVertical1.png");
				}
				
			}
			
			
			
		}catch (SlickException e)
		{
			e.printStackTrace();
		}
		
	}
	
	//Overrides
	public void draw(int xOffset, int yOffset)
	{
		int imageX = this.x*Globals.TILE_WIDTH - xOffset;
		int imageY = this.y*Globals.TILE_HEIGHT - yOffset;
		chooseImage();
		if(imageX > -Globals.TILE_WIDTH && imageX < Globals.SCREEN_WIDTH && imageY > -Globals.TILE_HEIGHT && imageY < Globals.SCREEN_HEIGHT)
		{
			tileImage.setColor(Image.TOP_LEFT, Main.currentLevel.getLightValue()[x][y][0], Main.currentLevel.getLightValue()[x][y][1], Main.currentLevel.getLightValue()[x][y][2], 1);
			tileImage.setColor(Image.TOP_RIGHT, Main.currentLevel.getLightValue()[x+1][y][0], Main.currentLevel.getLightValue()[x+1][y][1], Main.currentLevel.getLightValue()[x+1][y][2], 1);
			tileImage.setColor(Image.BOTTOM_RIGHT, Main.currentLevel.getLightValue()[x+1][y+1][0], Main.currentLevel.getLightValue()[x+1][y+1][1], Main.currentLevel.getLightValue()[x+1][y+1][2], 1);
			tileImage.setColor(Image.BOTTOM_LEFT, Main.currentLevel.getLightValue()[x][y+1][0], Main.currentLevel.getLightValue()[x][y+1][1], Main.currentLevel.getLightValue()[x][y+1][2], 1);
			
			tileImage.draw(imageX, imageY, 50, 50);
			
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