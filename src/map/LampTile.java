package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import action.ToggleLampAction;
import gui.Button;
import main.Globals;
import main.Main;
import main.Player;

public class LampTile extends Tile
{
	private static final int AP_COST = 0;

	boolean active;
	
	Button toggleLampButton;
	
	public LampTile(int type, int x, int y, Main app, GameContainer gc)
	{
		super(type, x, y, app, gc);
		this.walkable = false;
		
		this.type = 20;
		this.pointCost = 3;
		
		toggleLampButton = new Button(x + "" + y + "toggleLampButton", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.2f,
				Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * Globals.ACTION_Y, Globals.ACTION_WIDTH, Globals.ACTION_HEIGHT, "res/textures/gui/toggleLampButton.png", gc, app)
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
						active = !active;
						
						int tileX = -1;
						int tileY = -1;
						
						if(Main.selectedX > localPlayer.getXCoordinate())
						{
							tileX = localPlayer.getXCoordinate() + 1;
							tileY = localPlayer.getYCoordinate();
						}else if(Main.selectedX < localPlayer.getXCoordinate())
						{
							tileX = localPlayer.getXCoordinate() - 1;
							tileY = localPlayer.getYCoordinate();
						}else if(Main.selectedY > localPlayer.getYCoordinate())
						{
							tileX = localPlayer.getXCoordinate();
							tileY = localPlayer.getYCoordinate() + 1;
						}else if(Main.selectedY < localPlayer.getYCoordinate())
						{
							tileX = localPlayer.getXCoordinate();
							tileY = localPlayer.getYCoordinate() - 1;
						}
						
						app.changeActionPoints(-getPoints());
						
						ToggleLampAction action = new ToggleLampAction(tileX, tileY);
						app.playerActions.add(action);
						
					}
				}
			}
		};
		tileButtons.add(toggleLampButton);
		app.getGuiManager().addComponent(toggleLampButton);
		toggleLampButton.setActive(false);
		
		try
		{
			tileImage = new Image("res/textures/tiles/lampOn.png");
		}catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	
}