package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import gui.Button;
import gui.GuiManager;

public class Hunter extends Player
{
	GuiManager guiManager;

	Button stunButton;
	
	boolean targeted;
	
	boolean stunned = false;
	
	Main app;

	Image selectionImage;
	
	public Hunter(int inventorySize, float health, boolean isHunter, boolean isPlayerControlled, GuiManager guiManager, Main app, GameContainer gc)
	{
		super(inventorySize, health, isHunter, isPlayerControlled);
	
		this.guiManager = guiManager;
		this.app = app;
		
		stunButton = new Button("HunterStunButton", Globals.SCREEN_WIDTH * Globals.SCREEN_RES * 0.2f,
				Globals.SCREEN_HEIGHT * Globals.SCREEN_RES * Globals.ACTION_Y, Globals.ACTION_WIDTH, Globals.ACTION_HEIGHT, "res/textures/gui/stunButton.png", gc, app)
		{
			@Override
			public void run()
			{
				app.prey.stunReadyTurns = 10;
			}
		};
		guiManager.addComponent(stunButton);
		stunButton.setActive(false);
		
		try
		{
			playerSprites = new SpriteSheet("res/textures/player/hunterSprites.png", 32, 32);
			selectionImage = new Image("res/textures/tiles/selection.png");
		}catch (SlickException e)
		{
			e.printStackTrace();
		}
		
		currentImage = playerSprites.getSubImage(0, 0);
	}
	
	public void draw(Graphics g, int xOffset, int yOffset)
	{
		currentImage.setColor(Image.TOP_LEFT, Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]][0], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]][1], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]][2], 1);
		currentImage.setColor(Image.TOP_RIGHT, Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]][0], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]][1], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]][2], 1);
		currentImage.setColor(Image.BOTTOM_RIGHT, Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]+1][0], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]+1][1], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]+1][2], 1);
		currentImage.setColor(Image.BOTTOM_LEFT, Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]+1][0], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]+1][1], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]+1][2], 1);
		
		currentImage.draw((this.getXCoordinate() * Globals.TILE_WIDTH) - xOffset, (this.getYCoordinate() * Globals.TILE_WIDTH) - yOffset, Globals.TILE_WIDTH, Globals.TILE_HEIGHT);
		
		if(this.targeted)
		{
			selectionImage.setColor(Image.TOP_LEFT, Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]][0], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]][1], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]][2], 1);
			selectionImage.setColor(Image.TOP_RIGHT, Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]][0], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]][1], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]][2], 1);
			selectionImage.setColor(Image.BOTTOM_RIGHT, Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]+1][0], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]+1][1], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]+1][2], 1);
			selectionImage.setColor(Image.BOTTOM_LEFT, Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]+1][0], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]+1][1], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]+1][2], 1);
			
			
			this.selectionImage.draw((this.getXCoordinate() * Globals.TILE_WIDTH) - xOffset, (this.getYCoordinate() * Globals.TILE_WIDTH) - yOffset, Globals.TILE_WIDTH, Globals.TILE_HEIGHT);
		}
		
	}
	
	public void setTargeted(boolean targeted)
	{
		this.targeted = targeted;
		
		if(targeted)
		{
			if(app.prey.isStunReady())
			{
				stunButton.setActive(true);
			}
		}else
		{
			stunButton.setActive(false);
		}
	}
	
	
	
	public void setStunned(boolean stunned)
	{
		this.stunned = stunned;
	}
	
	public boolean getStunned()
	{
		return this.stunned;
	}
	
	public boolean isTargeted()
	{
		return this.targeted;
	}
	
	public void update(boolean isTurning)
	{
		
	}
}
