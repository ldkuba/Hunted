package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import action.MoveAction;
import action.ToggleDoorAction;
import gui.Button;
import main.Globals;
import main.Main;
import main.Player;

public class DoorOpenTile extends Tile {

	private static final int AP_COST = 2;
	
	Button closeDoorButton;
	Button moveToButton;
	
	public DoorOpenTile(int type, int x, int y, Main app, GameContainer gc) {
		super(type, x, y, app, gc);
		this.walkable = true;	
		
		this.pointCost = 2;
		
		closeDoorButton = new Button(x + "" + y + "closeDoorButton", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.4f,
				Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * Globals.ACTION_Y, Globals.ACTION_WIDTH, Globals.ACTION_HEIGHT, "res/textures/gui/closeDoorButton.png", gc, app)
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
						Main.currentLevel.getTileMap()[Main.selectedX][Main.selectedY].delete();
						
						Main.currentLevel.setTile(11, Main.selectedX, Main.selectedY);
						
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
		tileButtons.add(closeDoorButton);
		app.getGuiManager().addComponent(closeDoorButton);
		closeDoorButton.setActive(false);
		
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
				
				if(Math.abs(Main.selectedX - localPlayer.getXCoordinate()) <= 1 && Math.abs(Main.selectedY - localPlayer.getYCoordinate()) <= 1)
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
		
						// THESE 3 LINES BELOW ARE NECESSERY IN ALL BUTTON BEHAVIOURS
						// YOU JUST MAKE DIFFERENT ACTIONS FOR DIFFERENT TILES, EG.
						// DOORS USE TOGGLEDOORACTION.JAVA
		
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
		
		try
		{
			tileImage = new Image("res/textures/tiles/doorOpen.png");
		}catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	

	
}
