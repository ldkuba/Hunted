package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import action.ToggleDoorAction;
import gui.Button;
import main.Globals;
import main.Main;
import main.Player;

public class DoorClosedTile extends Tile {

	private static final int AP_COST = 2;
	
	Button openDoorButton;
	
	public DoorClosedTile(int type, int x, int y, Main app, GameContainer gc) {
		super(type, x, y, app, gc);
		this.walkable = false;
		
		this.pointCost = 2;
		
		openDoorButton = new Button(x + "" + y + "openDoorButton", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.2f,
				Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * Globals.ACTION_Y, Globals.ACTION_WIDTH, Globals.ACTION_HEIGHT, "res/textures/gui/openDoorButton.png", gc, app)
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
						Main.currentLevel.getTile(Main.selectedX, Main.selectedY).setSelected(false);
						
						Main.currentLevel.getTileMap()[Main.selectedX][Main.selectedY].delete();
						
						Main.currentLevel.setTile(10, Main.selectedX, Main.selectedY);
						
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
						
						ToggleDoorAction action = new ToggleDoorAction(tileX, tileY);
						app.playerActions.add(action);
						
					}
				}
			}
		};
		tileButtons.add(openDoorButton);
		app.getGuiManager().addComponent(openDoorButton);
		openDoorButton.setActive(false);
		
		try
		{
			tileImage = new Image("res/textures/tiles/doorClosed.png");
		}catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
}
