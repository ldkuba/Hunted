package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import action.MoveAction;
import gui.Button;
import main.Globals;
import main.Main;
import main.Player;

public class GrassTile extends Tile
{
	
	Button moveToButton;

	public GrassTile(int type, int x, int y, Main app, GameContainer gc)
	{
		super(type, x, y, app, gc);
		this.walkable = true;

		this.pointCost = 1;

		try
		{
			tileImage = new Image("res/textures/tiles/grass.png");
		}catch (SlickException e)
		{
			e.printStackTrace();
		}

		moveToButton = new Button(x + "" + y + "moveToButton", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.2f,
				Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * Globals.ACTION_Y, Globals.ACTION_WIDTH, Globals.ACTION_HEIGHT, "res/textures/gui/moveToButton.png", gc, app)
		{
			@Override
			public void run()
			{				

				Player localPlayer = null;
				
				if(app.isHunter())
				{
					localPlayer = Main.hunter;
				}else
				{
					localPlayer = Main.prey;
				}
				
				if(Math.abs(Main.selectedX - localPlayer.getXCoordinate()) + Math.abs(Main.selectedY - localPlayer.getYCoordinate()) <= 1)
				{					
					
					if(Main.currentLevel.getTileMap()[Main.selectedX][Main.selectedY].getPoints() <= app.getActionPoints())
					{
						int dir = -1;
		
						if(Main.selectedX > localPlayer.getXCoordinate())
						{
							dir = 3;
						}else if(Main.selectedX < localPlayer.getXCoordinate())
						{
							dir = 1;
						}else if(Main.selectedY > localPlayer.getYCoordinate())
						{
							dir = 2;
						}else if(Main.selectedY < localPlayer.getYCoordinate())
						{
							dir = 0;
						}
		
						if(app.isHunter())
						{
							Main.hunter.move(dir);
						}else
						{
							Main.prey.move(dir);
						}
		
							
						app.changeActionPoints(-getPoints());
						//System.out.println("Changing points");
						
						// HERE MOVEACTION, CAN BE DIFFERENT
						MoveAction action = new MoveAction(dir);
						Main.playerActions.add(action);
					}
			
				}
				
			}
		};
		//THESE THREE LINES ARE ALSO VITAL
		
		tileButtons.add(moveToButton);
		app.getGuiManager().addComponent(moveToButton);
		moveToButton.setActive(false);
	}

	private void chooseImage() {
		
		try	{
			//dirt
			
			//sides
			
	
				
			
			//Water
			
			//sides
			if (Main.currentLevel.getType(x, y-1) == 30) {
			
				tileImage = new Image("res/textures/tiles/GrassWaterSide3.png");
			
			} else if (Main.currentLevel.getType(x-1, y) == 30) {
			
				tileImage = new Image("res/textures/tiles/GrassWaterSide2.png");
			
			} else if (Main.currentLevel.getType(x, y+1) == 30) {
			
				tileImage = new Image("res/textures/tiles/GrassWaterSide1.png");
			
			} else if (Main.currentLevel.getType(x+1, y) == 30) {
			
				tileImage = new Image("res/textures/tiles/GrassWaterSide4.png");
			
			} else 
				//outer corners 
				
				if (Main.currentLevel.getType(x+1, y-1) == 30) {
				
					//top left corner
					tileImage = new Image("res/textures/tiles/GrassWaterOutCorner4.png");
					
				} else if (Main.currentLevel.getType(x-1, y-1) == 30) {
				
					//top right corner
					tileImage = new Image("res/textures/tiles/GrassWaterOutCorner3.png");
					
				} else if (Main.currentLevel.getType(x-1, y+1) == 30) {
				
					//bottom right  corner
					tileImage = new Image("res/textures/tiles/GrassWaterOutCorner2.png");
					
				} else if (Main.currentLevel.getType(x+1, y+1) == 30) {
				
					//bottom left corner
					tileImage = new Image("res/textures/tiles/GrassWaterOutCorner1.png");
					
				}
				//inner corners
				
				if ((Main.currentLevel.getType(x+1, y) == 30) && (Main.currentLevel.getType(x+1, y+1) == 30) && (Main.currentLevel.getType(x, y+1) == 30)) {
				
					tileImage = new Image("res/textures/tiles/GrassWaterInCorner4.png");
					
				} else if ((Main.currentLevel.getType(x-1, y) == 30) && (Main.currentLevel.getType(x-1, y+1) == 30) && (Main.currentLevel.getType(x, y+1) == 30)) {
				
					tileImage = new Image("res/textures/tiles/GrassWaterInCorner3.png");
					
				} else if ((Main.currentLevel.getType(x-1, y) == 30) && (Main.currentLevel.getType(x-1, y-1) == 30) && (Main.currentLevel.getType(x, y-1) == 30)) {
				
					tileImage = new Image("res/textures/tiles/GrassWaterInCorner2.png");
					
				} else if ((Main.currentLevel.getType(x+1, y) == 30) && (Main.currentLevel.getType(x+1, y-1) == 30) && (Main.currentLevel.getType(x, y-1) == 30)) {
				
					tileImage = new Image("res/textures/tiles/GrassWaterInCorner1.png");
					
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
