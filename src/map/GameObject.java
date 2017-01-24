package map;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import gui.Button;
import main.Globals;
import main.Main;

public class GameObject
{
	int x, y;
	int rotation; //0 forward, 1 facing left, 2 backward, 3 facing right
	int type;
	
	protected Image objectImage;
	
	protected ArrayList<Button> objectButtons;
	
	boolean selected = false;
	
	protected boolean walkable;
	
	int pointCost;
	
	Main app;
	
	GameContainer gc;
	
	public GameObject(int type, int x, int y, Main app, GameContainer gc)
	{
		this.app = app;
		this.gc = gc;
		this.x = x;
		this.y = y;
		this.type = type;	
		
		pointCost = 0;
		
		objectButtons = new ArrayList<Button>();
		
	}
	
	public ArrayList<Button> getTileButtons()
	{
		return objectButtons;
	}
	
	public void draw(int xOffset, int yOffset)
	{
		int imageX = this.x*Globals.TILE_WIDTH - xOffset;
		int imageY = this.y*Globals.TILE_HEIGHT - yOffset;
		
		if(imageX > -Globals.TILE_WIDTH && imageX < Globals.SCREEN_WIDTH && imageY > -Globals.TILE_HEIGHT && imageY < Globals.SCREEN_HEIGHT)
		{
			objectImage.setColor(Image.TOP_RIGHT, Main.currentLevel.getLightValue()[x+1][y][0], Main.currentLevel.getLightValue()[x+1][y][1], Main.currentLevel.getLightValue()[x+1][y][2], 1);
			objectImage.setColor(Image.TOP_LEFT, Main.currentLevel.getLightValue()[x][y][0], Main.currentLevel.getLightValue()[x][y][1], Main.currentLevel.getLightValue()[x][y][2], 1);
			objectImage.setColor(Image.BOTTOM_RIGHT, Main.currentLevel.getLightValue()[x+1][y+1][0], Main.currentLevel.getLightValue()[x+1][y+1][1], Main.currentLevel.getLightValue()[x+1][y+1][2], 1);
			objectImage.setColor(Image.BOTTOM_LEFT, Main.currentLevel.getLightValue()[x][y+1][0], Main.currentLevel.getLightValue()[x][y+1][1], Main.currentLevel.getLightValue()[x][y+1][2], 1);
			
			objectImage.draw(imageX, imageY, 50, 50);
		}
	}
	
	public void setSelected(boolean selected)
	{
		if(this.selected && !selected)
		{
			this.selected = false;
			
			for(int i = 0; i < objectButtons.size(); i++)
			{	
				objectButtons.get(i).setActive(false);
				System.out.println("Button " + objectButtons.get(i).getName());	
			}
		}else if(!this.selected && selected)
		{
			this.selected = true;
			
			for(int i = 0; i < objectButtons.size(); i++)
			{	
				if(app.getIsTurning())
				{
					objectButtons.get(i).setActive(true);
				}
			}
		}
	}
	
	public int getType()
	{
		return this.type;
	}
	
	public boolean isWalkable()
	{
		return this.walkable;
	}

	public int getPoints()
	{
		return this.pointCost;
	}
	
	public void delete()
	{
		setSelected(false);
	}
	
}
