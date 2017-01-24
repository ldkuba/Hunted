package main;



import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;

import action.Action;
import action.MoveAction;
import action.ToggleDoorAction;
import action.ToggleLampAction;
import gui.Button;
import gui.GuiManager;
import gui.Label;
import gui.MyTextField;
import lighting.Light;
import lighting.Lighting;
import map.LampTile;
import map.Level;
import method.MethodExecutor;
import network.Client;
import network.ClientMessageHandler;
import network.Server;
import network.ServerMessageHandler;

public class Main extends BasicGame implements ComponentListener
{
	public static GameContainer mainGameContainer;
	static Graphics mainGraphics;
	
	Button endTurnButton;
	
	Input input;
	
	boolean lightingOn = true;
	
	int id;//0 - server, 1 - client
	
	boolean isHunter = true;
	boolean isTurning;
	
	public static ArrayList<Action> playerActions;
	int actionPoints = 0;
	
	int playerHealth = 0;
	
	Server server;
	Client client;
	
	public static Hunter hunter;
	public static Prey prey;
	
	public Thread serverMessageThread;
	public Thread clientMessageThread;
	ServerMessageHandler serverMessageHandler;
	ClientMessageHandler clientMessageHandler;
	
	public MethodExecutor methodExecutor;
	
	Level[] levels;
	public static Level currentLevel;
	public static int selectedX = -1;
	public static int selectedY = -1;

	Menu menuState;
	boolean menu = true;
	
	GuiManager gameGuiManager;
	Label actionPointsLabel;
	Label playerHealthLabel;
	Font labelFont;
	
	ArrayList<Button> selectionButtons;
	
	Image actionBarImage;
	Image inventoryBarImage;
	Image infoBarImage;
	
	int XOffset = 0;
	int YOffset = 0;
	
	public Main(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException
	{
		gameGuiManager = new GuiManager();
		
		levels = new Level[1];
		
		currentLevel = new Level("res/levels/mansion.txt", this, gc);
		
		input = gc.getInput();
		
		playerActions = new ArrayList<Action>();
		
		methodExecutor = new MethodExecutor();
		
		menuState = new Menu(gc, this);
		
		actionBarImage = new Image("res/textures/gui/actionBar.png");
		inventoryBarImage = new Image("res/textures/gui/Inventory.png");
		infoBarImage = new Image("res/textures/gui/infoBar.png");
		
		selectionButtons = new ArrayList<Button>();
		
		labelFont = new Font("Verdana", Font.BOLD, 20);
		endTurnButton = new Button("endTurnButton", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.935f, Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * 0.88f, 100, 30, "res/textures/gui/gameEndTurnButton.png", gc, this)
		{
			@Override
			public void run()
			{
				app.endTurn();
			}
		};
		this.gameGuiManager.addComponent(endTurnButton);
		
		actionPointsLabel = new Label("actionPointsLabel",  Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.05f, Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * 0.05f, 100, 30, gc, this, labelFont);
		this.gameGuiManager.addComponent(actionPointsLabel);
		
		playerHealthLabel = new Label("playerHealthLabel",  Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.05f, Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * 0.08f, 100, 30, gc, this, labelFont);
		this.gameGuiManager.addComponent(playerHealthLabel);
		
	
		changeActionPoints(Globals.MAX_POINTS_PER_TURN);
		changeHealth(100);
	}

	public void changeActionPoints(int delta)
	{
		actionPoints += delta;
		actionPointsLabel.setText("Points Left: " + actionPoints);
	}
	
	public int getActionPoints()
	{
		return this.actionPoints;
	}
	
	public void changeHealth(int delta)
	{
		this.playerHealth += delta;
		playerHealthLabel.setText("Health: " + playerHealth);
	}
	
	@Override
	public void update(GameContainer gc, int tpf) throws SlickException
	{
		if(menu)
		{
			menuState.update();
		}else
		{
			if(!Globals.STATIC_CAMERA)
			{
				if(input.getMouseX() < Globals.MOUSE_SCROLL_THRESHOLD || input.isKeyDown(Input.KEY_LEFT))
				{
					XOffset -= Globals.MOUSE_SCROLL_SPEED*tpf;
				}
				
				if(input.getMouseX() > Globals.SCREEN_WIDTH-Globals.MOUSE_SCROLL_THRESHOLD || input.isKeyDown(Input.KEY_RIGHT))
				{
					XOffset += Globals.MOUSE_SCROLL_SPEED*tpf;
				}
				
				if(input.getMouseY() < Globals.MOUSE_SCROLL_THRESHOLD || input.isKeyDown(Input.KEY_UP))
				{
					YOffset -= Globals.MOUSE_SCROLL_SPEED*tpf;
				}
				
				if(input.getMouseY() > Globals.SCREEN_HEIGHT-Globals.MOUSE_SCROLL_THRESHOLD || input.isKeyDown(Input.KEY_DOWN))
				{
					YOffset += Globals.MOUSE_SCROLL_SPEED*tpf;
				}
			}else
			{
				if(isHunter)
				{
					XOffset = hunter.getXCoordinate()*Globals.TILE_WIDTH + (Globals.TILE_WIDTH/2) - Globals.SCREEN_WIDTH/2;
					YOffset = hunter.getYCoordinate()*Globals.TILE_HEIGHT + (Globals.TILE_HEIGHT/2) - Globals.SCREEN_HEIGHT/2;
				}else
				{
					XOffset = prey.getXCoordinate()*Globals.TILE_WIDTH + (Globals.TILE_WIDTH/2) - Globals.SCREEN_WIDTH/2;
					YOffset = prey.getYCoordinate()*Globals.TILE_HEIGHT + (Globals.TILE_HEIGHT/2) - Globals.SCREEN_HEIGHT/2;
				}
			}
			
			if(input.isKeyDown(Input.KEY_L))
			{
				lightingOn = true;
			}else if(input.isKeyDown(Input.KEY_K))
			{
				lightingOn = false;
			}
			
			if(input.isKeyDown(Input.KEY_ESCAPE))
			{
				gc.exit();
			}
			
			//PLAYER UPDATE
			if(isHunter)
			{
				hunter.update(isTurning);
				prey.update(!isTurning);
			}else
			{
				hunter.update(!isTurning);
				prey.update(isTurning);
			}
			
		}
		
		if(hunter != null && prey != null)
		{
			
			
			ArrayList<Light> lights = currentLevel.getLights();
			if(isHunter)
			{	
				lights.add(new Light(hunter.getXCoordinate()*50 + 25, hunter.getYCoordinate()*50+25, Globals.PLAYER_LIGHT_STRENGTH*1.5f, new Color(0.945f, 0.306f, 0.184f, 1.0f).brighter(0.5f)));
			}else
			{
				lights.add(new Light(prey.getXCoordinate()*50 + 25, prey.getYCoordinate()*50+25, Globals.PLAYER_LIGHT_STRENGTH, Color.white.darker(0.4f)));
			}
			
			//System.out.println("LIGHTS: " + lights.size());
			
			Lighting.updateLightMap(lights, !lightingOn);
		}
		
		methodExecutor.execute();
	}
	
	

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		if(menu)
		{
			menuState.render(gc, g);
		}else
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
			
			if(currentLevel != null)
				currentLevel.display(XOffset, YOffset);
			
			if(hunter != null)
			{
				hunter.draw(g, XOffset, YOffset);
			}
			
			if(prey != null)
			{
				prey.draw(g, XOffset, YOffset);
			}
			
			this.actionBarImage.draw(0, Globals.SCREEN_HEIGHT-Globals.GUI_HEIGHT, Globals.SCREEN_WIDTH-Globals.GUI_WIDTH, Globals.GUI_HEIGHT);
			this.inventoryBarImage.draw(Globals.SCREEN_WIDTH-Globals.GUI_WIDTH, 0, Globals.GUI_WIDTH, Globals.SCREEN_HEIGHT-Globals.GUI_HEIGHT);
			this.infoBarImage.draw(Globals.SCREEN_WIDTH-Globals.GUI_WIDTH, Globals.SCREEN_HEIGHT-Globals.GUI_HEIGHT, Globals.GUI_WIDTH, Globals.GUI_HEIGHT);
			
			gameGuiManager.draw(gc, g);
		}
	}
	
	public boolean getIsTurning()
	{
		return this.isTurning;
	}
	
	public void startNewTurn(ArrayList<Action> actions)
	{
		System.out.println("Started new turn");
		
		changeActionPoints(-actionPoints);
		changeActionPoints(Globals.MAX_POINTS_PER_TURN);
		
		this.isTurning = true;
		
		for(int i = 0; i < actions.size(); i++)
		{
			if(actions.get(i) instanceof MoveAction)
			{
				MoveAction m = (MoveAction) actions.get(i);
				if(this.isHunter)
				{
					prey.move(m.getDir());
				}else
				{
					hunter.move(m.getDir());
				}
			}else if(actions.get(i) instanceof ToggleDoorAction)
			{
				ToggleDoorAction m = (ToggleDoorAction) actions.get(i);
				
				System.out.println("Recieved door toggle message");

				int tileX = m.getTileX();
				int tileY = m.getTileY();
				
					
				if(currentLevel.getTile(tileX, tileY).getType() == 10)
				{
					currentLevel.setTile(11, tileX, tileY);
				}else if(currentLevel.getTile(tileX, tileY).getType() == 11)
				{
					currentLevel.setTile(10, tileX, tileY);
				}		
			}else if(actions.get(i) instanceof ToggleLampAction)
			{
				ToggleLampAction m = (ToggleLampAction) actions.get(i);
				
				System.out.println("Recieved lamp toggle message");

				int tileX = m.getTileX();
				int tileY = m.getTileY();
					
				if(currentLevel.getTile(tileX, tileY).getType() == 20)
				{
					LampTile t = (LampTile) (currentLevel.getTile(tileX, tileY));
					if(t.isActive())
					{
						t.setActive(false);
					}else
					{
						t.setActive(true);
					}
				}
			}
			
			if(this.isHunter)
			{
				hunter.setStunned(false);
			}else
			{
				prey.nextTurn();
			}
			
		}
		
		endTurnButton.setActive(true);
	}
	
	public void endTurn()
	{
		endTurnButton.setActive(false);
		
		String msg = "NewTurn";
		msg += " " + playerActions.size();
		
		for(int i = 0; i < playerActions.size(); i++)
		{
			msg += " " + Action.parseAction(playerActions.get(i));
		}
		
		if(this.id == 0)
		{
			server.send(msg);
		}else
		{
			client.send(msg);
		}
		
		this.isTurning = false;
		
		for(int j = 0; j < currentLevel.getLvlHeight(); j++)
		{
			for(int i = 0; i < currentLevel.getLvlWidth(); i++)
			{
				currentLevel.getTile(i, j).setSelected(false);
			}
		}
		
		
		
		playerActions.clear();
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Main("Simple Slick Game"));
			appgc.setDisplayMode(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT, Globals.FULLSCREEN);
			appgc.setTargetFrameRate(Globals.MAX_FPS);
			appgc.setShowFPS(false);
			appgc.start();
			
			Main.mainGameContainer = appgc;
			Main.mainGraphics = appgc.getGraphics();
			
		}catch (SlickException ex)
		{
			Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}
	
	public void hostGame(boolean isHunter, GameContainer gc)
	{
		this.id = 0;
		
		try
		{
			server = new Server(Globals.NETWORK_PORT);
			server.run();
			
			
		}catch (IOException e)
		{
			e.printStackTrace();
		}
		
		serverMessageHandler = new ServerMessageHandler(server, this);
		serverMessageThread = new Thread(serverMessageHandler);
		serverMessageThread.setDaemon(false);
		serverMessageThread.start();
		
		startHostGame(isHunter, gc);
		
		System.out.println("HOST SETUP FINISHED");
	}
	
	public void startHostGame(boolean isHunter, GameContainer gc)
	{
		this.menu = false;
		this.isHunter = isHunter;
		if(isHunter)
		{
			hunter = new Hunter(5, 100, isHunter, true, this.gameGuiManager, this, gc);
			prey = new Prey(5, 100, isHunter, false, this.gameGuiManager, this, gc);
			
			hunter.setCoordinates(9, 3);
			prey.setCoordinates(4, 4);
			
			isTurning = false;
			
			endTurnButton.setActive(false);
			
			Globals.STATIC_CAMERA = true;
		}else
		{
			prey = new Prey(5, 100, isHunter, true, this.gameGuiManager, this, gc);
			hunter = new Hunter(5, 100, isHunter, true, this.gameGuiManager, this, gc);
			
			hunter.setCoordinates(9, 3);
			prey.setCoordinates(4, 4);
			
			isTurning = true;
			
			endTurnButton.setActive(true);
			
			Globals.STATIC_CAMERA = false;
		}
		
		System.out.println("Playing as " + isHunter + " on Host");
	}
	
	public void joinGame(String ip, GameContainer gc)
	{
		this.id = 1;
		
		client = new Client();
		
		if(client.connect(ip, Globals.NETWORK_PORT) == 0)
		{
			client.send("Joined hello!");
		}
		
		clientMessageHandler = new ClientMessageHandler(client, this, gc);
		clientMessageThread = new Thread(clientMessageHandler);
		clientMessageThread.setDaemon(false);
		clientMessageThread.start();
		System.out.println("PLAY SETUP FINISHED");
	}

	public void startClientGame(boolean isHunter, GameContainer gc)
	{
		this.menu = false;
		this.isHunter = isHunter;
		
		if(isHunter)
		{
			hunter = new Hunter(5, 100, isHunter, true, this.gameGuiManager, this, gc);
			prey = new Prey(5, 100, isHunter, false, this.gameGuiManager, this, gc);
			
			hunter.setCoordinates(9, 3);
			prey.setCoordinates(4, 4);
			
			isTurning = false;
			
			endTurnButton.setActive(false);
			
			Globals.STATIC_CAMERA = true;
		}else
		{
			prey = new Prey(5, 100, isHunter, true, this.gameGuiManager, this, gc);
			hunter = new Hunter(5, 100, isHunter, true, this.gameGuiManager, this, gc);
			
			hunter.setCoordinates(9, 3);
			prey.setCoordinates(4, 4);
			
			isTurning = true;
			
			endTurnButton.setActive(true);
			
			Globals.STATIC_CAMERA = false;
		}
		
		System.out.println("Playing as " + isHunter + " on Client");
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public boolean isHunter()
	{
		return this.isHunter;
	}
	
	public GuiManager getGuiManager()
	{
		return this.gameGuiManager;
	}
	
	public void endGame(boolean preyEscaped)
	{
		
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
		if(!menu)
		{
				
			int clickX = (x + XOffset) / Globals.TILE_WIDTH;
			//clickX++;
			int clickY = (y + YOffset) / Globals.TILE_HEIGHT;
			//clickY++;
			
			if(y > Globals.SCREEN_HEIGHT - Globals.GUI_HEIGHT || x > Globals.SCREEN_WIDTH - Globals.GUI_WIDTH)
			{
				return;
			}
			
			if(clickX > -1 && clickX < currentLevel.getLvlWidth() && clickY > -1 && clickY < currentLevel.getLvlHeight())
			{		
				System.out.println("X " + clickX + " Y " + clickY);
				System.out.println("SelX " + selectedX + " SelY " + selectedY);
				
				if(clickX != selectedX || clickY != selectedY)
				{
					if(selectedX != -1 && selectedY != -1)
					{
						currentLevel.getTile(selectedX, selectedY).setSelected(false);
					}
					
					if(!this.isHunter)
					{
						currentLevel.getTile(clickX, clickY).setSelected(true);
					}else
					{
						if(!this.hunter.getStunned())
						{
							currentLevel.getTile(clickX, clickY).setSelected(true);
						}
					}
					
					selectedX = clickX;
					selectedY = clickY;
					
					if(isHunter)
					{
						if(prey.getXCoordinate() == selectedX && prey.getYCoordinate() == selectedY)
						{
							currentLevel.getTile(clickX, clickY).setSelected(false);
							
							if(this.isTurning)
							{
								if(Math.abs(hunter.getXCoordinate() - prey.getXCoordinate()) <= 1 && Math.abs(hunter.getXCoordinate() - prey.getYCoordinate()) <= 1)
								{
									prey.setTargeted(true);
								}
							}
						}else if(prey.isTargeted())
						{
							prey.setTargeted(false);
						}
						
					}else
					{
						if(hunter.getXCoordinate() == selectedX && hunter.getYCoordinate() == selectedY)
						{
							currentLevel.getTile(clickX, clickY).setSelected(false);
							
							if(this.isTurning)
							{
								if(Math.abs(hunter.getXCoordinate() - prey.getXCoordinate()) <= 1 && Math.abs(hunter.getXCoordinate() - prey.getYCoordinate()) <= 1)
								{
									hunter.setTargeted(true);
								}
							}
						}else if(hunter.isTargeted())
						{
							hunter.setTargeted(false);
						}
					}
				}
			}else
			{
				if(selectedX != -1 && selectedY != -1)
				{
					currentLevel.getTile(selectedX, selectedY).setSelected(false);
					selectedX = -1;
					selectedY = -1;
				}
			}
		}
		/**
		if(button == Input.MOUSE_RIGHT_BUTTON && this.isTurning)
		{

			System.out.println("RIGHT CLICKED");
			
			int clickX = (x + XOffset) / Globals.TILE_WIDTH;
			int clickY = (y + YOffset) / Globals.TILE_HEIGHT;
			
			int playerX = 0;
			int playerY = 0;
			
			if(isHunter)
			{
				playerX = hunter.getXCoordinate();
				playerY = hunter.getYCoordinate();
			}else
			{
				playerX = prey.getXCoordinate();
				playerY = prey.getYCoordinate();
			}
			
			System.out.println("Clicked at x: " + clickX + " y: " + clickY);
			
			if(Math.abs(clickX - playerX) <= 1 && Math.abs(clickY - playerY) <= 1)
			{					
				System.out.println("CLicked in range");
				
				if(Main.currentLevel.getTileMap()[clickX][clickY].getPoints() <= this.actionPoints)
				{	
					changeActionPoints(-Main.currentLevel.getTileMap()[clickX][clickY].onClick());		
					
					int dir = 0;
					if(clickX > playerX)
					{
						dir = 3;
					}else if(clickX < playerX)
					{
						dir = 1;
					}else if(clickY > playerY)
					{
						dir = 2;
					}else if(clickY < playerY)
					{
						dir = 0;
					}
					
					if(Main.currentLevel.getTile(clickX, clickY).isWalkable())
					{
						if(isHunter)
						{
							hunter.move(dir);
						}else
						{
							prey.move(dir);
						}
						
						MoveAction action = new MoveAction(dir);
						Main.playerActions.add(action);
					}else if(Main.currentLevel.getTile(clickX, clickY).getType() == 10 || Main.currentLevel.getTile(clickX, clickY).getType() == 11)
					{
						ToggleDoorAction action = new ToggleDoorAction(dir);
						Main.playerActions.add(action);
					}
				}
			}
		}
		
		*/
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int button, int x, int y)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(int change)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputEnded()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAcceptingInput()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setInput(Input arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void componentActivated(AbstractComponent source)
	{
		if(menu)
		{
			for(int i = 0; i < menuState.getGuiManager().getComponents().size(); i++)
			{
				if(menuState.getGuiManager().getComponents().get(i) instanceof Button)
				{
					Button b = (Button) (menuState.getGuiManager().getComponents().get(i));
					if(source == b.getComponent())
					{
						b.run();
					}
				}else if(menuState.getGuiManager().getComponents().get(i) instanceof MyTextField)
				{
					MyTextField t = (MyTextField) (menuState.getGuiManager().getComponents().get(i));
					if(source == t.getComponent())
					{
						TextField field = (TextField) t.getComponent();
						t.setText(field.getText());
					}
				}
			}
		}else
		{
			for(int i = 0; i < gameGuiManager.getComponents().size(); i++)
			{
				if(gameGuiManager.getComponents().get(i) instanceof Button)
				{
					Button b = (Button) (gameGuiManager.getComponents().get(i));
					if(source == b.getComponent())
					{
						System.out.println(b.getJustPressed());
						
						b.run();
					}
				}
			}
		}
	}

}
