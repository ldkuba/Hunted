package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import main.Globals;
import main.Main;

public class WallCornerTile1 extends Tile
{


	public WallCornerTile1(int type, int x, int y, Main app, GameContainer gc)
	{
		super(type, x, y, app, gc);
		this.walkable = false;
		
		this.pointCost = 0;
		
		try
		{
			tileImage = new Image("res/textures/tiles/wallCorner2Way1.png");
		}catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	public void chooseImage() {
		
		try	{
			//4 corners of map
			if ((this.x == 0) && (this.y ==0)) {
			
				tileImage = new Image("res/textures/tiles/wallCorner2Way1.png");
			
			}else if ((this.x == 0)&&(this.y ==Main.currentLevel.getLvlHeight())) {
			
				tileImage = new Image("res/textures/tiles/wallCorner2Way4.png");
			
			}else if ((this.x == Main.currentLevel.getLvlWidth())&&(this.y ==0)) {
			
				tileImage = new Image("res/textures/tiles/wallCorner2Way2.png");
			
			}else if ((this.x == Main.currentLevel.getLvlWidth())&&(this.y ==Main.currentLevel.getLvlHeight())) {
			
				tileImage = new Image("res/textures/tiles/wallCorner2Way3.png");
			
			} else {
			// normal corners
			
				 if (((Main.currentLevel.getType(x, y-1) == 13) && (Main.currentLevel.getType(x, y + 1) == 13))&&((Main.currentLevel.getType(x-1, y) == 12) && (Main.currentLevel.getType(x-1, y) == 12))) {
				
					 //4 way
					 tileImage = new Image("res/textures/tiles/wallCorner4Way.png");
				 
				 } else if (((Main.currentLevel.getType(x, y-1) == 13) && (Main.currentLevel.getType(x, y + 1) == 13))&&((Main.currentLevel.getType(x-1, y) == 12) )) {
				
					 //3 way V 1
					 tileImage = new Image("res/textures/tiles/wallCorner3WayV1.png");
			
				 }else if (((Main.currentLevel.getType(x, y-1) == 13) && (Main.currentLevel.getType(x, y + 1) == 13))&&((Main.currentLevel.getType(x+1, y) == 12) )) {
				
					 //3 way V 1
					 tileImage = new Image("res/textures/tiles/wallCorner3WayV2.png");
			
				 } else if (((Main.currentLevel.getType(x, y + 1) == 13))&&((Main.currentLevel.getType(x-1, y) == 12) && (Main.currentLevel.getType(x+1, y) == 12))) {
				
					 //3 way H 1
					 tileImage = new Image("res/textures/tiles/wallCorner3WayH1.png");
				 
				 } else if (((Main.currentLevel.getType(x, y - 1) == 13))&&((Main.currentLevel.getType(x-1, y) == 12) && (Main.currentLevel.getType(x+1, y) == 12))) {
				
					 //3 way H 1
					 tileImage = new Image("res/textures/tiles/wallCorner3WayH2.png");
				 
				 } else {
				 	
				 	//2way tiles
				 	if ((((Main.currentLevel.getType(x, y - 1) == 13))&&((Main.currentLevel.getType(x+1, y) == 12)))) {
				 		
				 		//top left corner
				 		tileImage = new Image("res/textures/tiles/wallCorner2Way1.png");
				 	} else if ((((Main.currentLevel.getType(x, y - 1) == 13))&&((Main.currentLevel.getType(x-1, y) == 12)))) {
				 		
				 		//top right corner
				 		tileImage = new Image("res/textures/tiles/wallCorner2Way2.png");
				 		
				 	} if ((((Main.currentLevel.getType(x, y + 1) == 13))&&((Main.currentLevel.getType(x+1, y) == 12)))) {
				 		
				 		//bottm left corner
				 		tileImage = new Image("res/textures/tiles/wallCorner2Way1.png");
				 		
				 	} else if ((((Main.currentLevel.getType(x, y + 1) == 13))&&((Main.currentLevel.getType(x-1, y) == 12)))) {
				 		
				 		//bottom right corner
				 		tileImage = new Image("res/textures/tiles/wallCorner2Way2.png");
				 		
				 	}
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
