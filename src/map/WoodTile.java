package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import action.MoveAction;
import gui.Button;
import main.Globals;
import main.Main;
import main.Player;

public class WoodTile extends Tile
{
	Button moveToButton;

	public WoodTile(int type, int x, int y, Main app, GameContainer gc)
	{
		super(type, x, y, app, gc);
		this.walkable = true;
		
		this.pointCost = 1;
		
		try
		{
			tileImage = new Image("res/textures/tiles/planks.png");
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
						MoveAction action = new MoveAction(dir);
						Main.playerActions.add(action);
					}
			
				}
				
					}
				};
								
				tileButtons.add(moveToButton);
				app.getGuiManager().addComponent(moveToButton);
				moveToButton.setActive(false);
	}
}