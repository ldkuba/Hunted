package main;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Vakerrian on 13/10/2016.
 */
public class Player
{

	private float health;
	protected int[] coordinates = new int[2];

	private Item[] inventory;

	public boolean isTrapped;
	boolean isHunter = true;
	
	Image currentImage;
	
	SpriteSheet playerSprites;

	boolean isPlayerControlled = false;
	
	public Player(int inventorySize, float health, boolean isHunter, boolean isPlayerControlled)
	{
		setInventory(inventorySize);
		setHealth(health);

		this.isHunter = isHunter;

		isTrapped = false;	
		
		this.isPlayerControlled = isPlayerControlled;
	}

	/* ====== */

	public float getHealth()
	{
		return health;
	}

	public void setHealth(float health)
	{
		this.health = health;
	}

	/* ====== */

	private void setInventory(int inventorySize)
	{
		inventory = new Item[inventorySize];

		for (int i = 0; i < inventory.length; i++)
		{
			inventory[i] = Item.EMPTY;
		}
	}

	public Item[] getInventory()
	{
		return inventory;
	}

	public void addItem(Item item)
	{
		boolean hasSpace = false;
		for (int index = 0; index < inventory.length; index++)
		{
			if(inventory[index].equals(Item.EMPTY))
			{
				inventory[index] = item;
				hasSpace = true;
				break;
			}
		}
		if(!hasSpace)
		{
			System.out.println("\n\t[INVENTORY] Inventory is full");
		}
	}

	public void removeItem(Item item)
	{
		boolean hasRemoved = false;
		for (int index = 0; index < inventory.length; index++)
		{
			if(inventory[index].equals(item))
			{
				inventory[index] = Item.EMPTY;
				hasRemoved = true;
				break;
			}
		}
		if(!hasRemoved)
		{
			System.out.println("\n\t[INVENTORY] Item not found");
		}
	}

	public void draw(Graphics g, int xOffset, int yOffset)
	{
		currentImage.setColor(Image.TOP_LEFT, Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]][0], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]][1], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]][2], 1);
		currentImage.setColor(Image.TOP_RIGHT, Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]][0], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]][1], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]][2], 1);
		currentImage.setColor(Image.BOTTOM_RIGHT, Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]+1][0], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]+1][1], Main.currentLevel.getLightValue()[coordinates[0]+1][coordinates[1]+1][2], 1);
		currentImage.setColor(Image.BOTTOM_LEFT, Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]+1][0], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]+1][1], Main.currentLevel.getLightValue()[coordinates[0]][coordinates[1]+1][2], 1);
		
		currentImage.draw((this.getXCoordinate() * Globals.TILE_WIDTH) - xOffset, (this.getYCoordinate() * Globals.TILE_WIDTH) - yOffset, Globals.TILE_WIDTH, Globals.TILE_HEIGHT);
	}
	
	/* ====== */

	public void setCoordinates(int[] coordinates)
	{
		this.coordinates = coordinates;
	}
	
	public void move(int dir)
	{
		if(dir == 0)
		{
			this.coordinates[1]--;
		}else if(dir == 1)
		{
			this.coordinates[0]--;
		}else if(dir == 2)
		{
			this.coordinates[1]++;
		}else if(dir == 3)
		{
			this.coordinates[0]++;
		}
	}

	public void setCoordinates(int xCoords, int yCoords)
	{
		coordinates[0] = xCoords;
		coordinates[1] = yCoords;
	}

	public void setXCoordinate(int xCoords)
	{
		coordinates[0] = xCoords;
	}

	public void setYCoordinate(int yCoords)
	{
		coordinates[1] = yCoords;
	}

	public int[] getCoordinates()
	{
		return coordinates;
	}

	public int getXCoordinate()
	{
		return coordinates[0];
	}

	public int getYCoordinate()
	{
		return coordinates[1];
	}

	public boolean isHunter()
	{
		return this.isHunter;
	}



	
}