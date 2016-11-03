package main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import gui.Button;
import gui.GuiManager;
import gui.MyTextField;

public class Menu
{
	Image background;
	
	public static GuiManager menuGuiManager;
	Button hostButton;
	
	Button hostHunterButton;
	Button hostPreyButton;
	
	Button playButton;
	Button quitButton;
	
	MyTextField ipTextField;
	MyTextField mapTextField;
	
	GameContainer gc;
	
	
	Main app;
	
	public Menu(GameContainer gc, Main app)
	{
		this.gc = gc;
		
		this.app = app;
		
		try
		{
			background = new Image("res/textures/gui/menu_background.png");
		}catch (SlickException e)
		{
			e.printStackTrace();
		}
		
		menuGuiManager = new GuiManager();
		hostButton = new Button("hostButton", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.2f, Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * 0.5f, 125, 36, "res/textures/gui/menuHostButton.png", this.gc, app)
		{
			@Override
			public void run()
			{
				Button b1 = (Button) Menu.menuGuiManager.getComponent("hostHunterButton");
				Button b2 = (Button) Menu.menuGuiManager.getComponent("hostPreyButton");
				b1.setActive(true);
				b2.setActive(true);
			}
		};
		menuGuiManager.addComponent(hostButton);
		
		hostHunterButton = new Button("hostHunterButton", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.35f, Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * 0.5f, 125, 36, "res/textures/gui/menuHostHunterButton.png", this.gc, app)
		{
			@Override
			public void run()
			{
				//app.menu = false;
				app.hostGame(true, gc);
			}
		};
		hostHunterButton.setActive(false);
		menuGuiManager.addComponent(hostHunterButton);
		
		hostPreyButton = new Button("hostPreyButton", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.5f, Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * 0.5f, 125, 36, "res/textures/gui/menuHostPreyButton.png", this.gc, app)
		{
			@Override
			public void run()
			{
				//app.menu = false;
				app.hostGame(false, gc);
				
			}
		};
		hostPreyButton.setActive(false);
		menuGuiManager.addComponent(hostPreyButton);
		
		playButton = new Button("playButton", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.2f, Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * 0.6f, 125, 36, "res/textures/gui/menuPlayButton.png", this.gc, app)
		{
			@Override
			public void run()
			{
				app.joinGame(ipTextField.getText(), gc);
			}
		};
		menuGuiManager.addComponent(playButton);
		
		quitButton = new Button("quitButton", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.2f, Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * 0.7f, 125, 36, "res/textures/gui/menuQuitButton.png", this.gc, app)
		{
			@Override
			public void run()
			{
				gc.exit();
			}
		};
		menuGuiManager.addComponent(quitButton);
		
		ipTextField = new MyTextField("ipTextField", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.35f, Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * 0.6f, 150, 30, this.gc, app);
		menuGuiManager.addComponent(ipTextField);
		
		mapTextField = new MyTextField("mapTextField", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.35f, Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * 0.7f, 150, 30, this.gc, app);
		//menuGuiManager.addComponent(mapTextField);
	}
	
	public void update()
	{
		
	}
	
	public void render(GameContainer gc, Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
		
		background.draw(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
		
		menuGuiManager.draw(gc, g);
	}
	
	public GuiManager getGuiManager()
	{
		return this.menuGuiManager;
	}
}
