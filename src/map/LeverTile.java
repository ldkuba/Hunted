package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import action.ToggleDoorAction;
import action.ToggleLampAction;
//import action.PullLeverAction;
import gui.Button;
import main.Globals;
import main.Main;
import main.Player;

public class LeverTile extends Tile {
	
	Button pullLeverButton;
	
	private int refX;
	private int refY;
	
	public LeverTile(int type, int x, int y, int refX, int refY, Main app, GameContainer gc) {
		super(type, x, y, app, gc);
		this.walkable = false;
		
		this.pointCost = 2;
		
		this.refX = refX;
		this.refY = refY;
		
		try
		{
			tileImage = new Image("res/textures/tiles/lever.png");
		}catch (SlickException e)
		{
			e.printStackTrace();
		}
		
		String name = x + "" + y + "pullLeverButton";
		
		pullLeverButton = new Button(name, Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.2f,
				Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * Globals.ACTION_Y, Globals.ACTION_HEIGHT, Globals.ACTION_WIDTH, "res/textures/gui/pullLeverButton.png", gc, app)
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
						if(Main.currentLevel.getTileMap()[Main.selectedX][Main.selectedY].getPoints() <= app.getActionPoints())	{
							//Main.currentLevel.getTile(refX, refY).button.run()
							app.changeActionPoints(-getPoints());
							//MoveAction action = new MoveAction(dir);
							//Main.playerActions.add(action);
							
							if(Main.currentLevel.getTile(refX, refY).type == 10)
							{
								Main.currentLevel.getTileMap()[refX][refY].delete();
								
								Main.currentLevel.setTile(11, refX, refY);
								
								app.changeActionPoints(-getPoints());
								
								ToggleDoorAction action = new ToggleDoorAction(refX, refY);
								app.playerActions.add(action);
							}else if(Main.currentLevel.getTile(refX, refY).type == 11)
							{
								Main.currentLevel.getTileMap()[refX][refY].delete();
								
								Main.currentLevel.setTile(10, refX, refY);
								
								app.changeActionPoints(-getPoints());
								
								ToggleDoorAction action = new ToggleDoorAction(refX, refY);
								app.playerActions.add(action);
							}else if(Main.currentLevel.getTile(refX, refY).type == 20)
							{
								LampTile t = (LampTile) Main.currentLevel.getTile(refX, refY);
								if(t.isActive())
								{
									t.setActive(false);
								}else
								{
									t.setActive(true);
								}
								
								ToggleLampAction action = new ToggleLampAction(refX, refY);
								app.playerActions.add(action);
							}
							
							//Main.currentLevel.getTile(refX, refY).tileButtons.get(0).run();
							
						}
					}
					
				}
			};
									
			tileButtons.add(pullLeverButton);
			app.getGuiManager().addComponent(pullLeverButton);
			pullLeverButton.setActive(false);
		}
	
		public int getRefX()
		{
			return refX;
		}
		
		public int getRefY()
		{
			return refY;
		}
	}
		
		
		
	
	
		
	
	

