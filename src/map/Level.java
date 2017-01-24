package map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import lighting.Light;
import main.Main;

public class Level
{
	int lvlWidth = 0;
	int lvlHeight = 0;

	Tile[][] tileMap;
	GameObject[][] objectMap;

	Main app;
	GameContainer gc;

	public float[][][] lightValue;

	ArrayList<Vector2f> pairsLever;

	public Level(String filepath, Main app, GameContainer gc)
	{
		this.app = app;
		this.gc = gc;

		pairsLever = new ArrayList<Vector2f>();
		readMap(filepath);
		createMap();
	}

	public void display(int xOffset, int yOffset)
	{
		for (int j = 0; j < lvlHeight; j++)
		{
			for (int i = 0; i < lvlWidth; i++)
			{
				tileMap[i][j].draw(xOffset, yOffset);
				objectMap[i][j].draw(xOffset, yOffset);
			}
		}
	}

	public ArrayList<Light> getLights()
	{
		ArrayList<Light> lights = new ArrayList<Light>();

		for (int j = 0; j < lvlHeight; j++)
		{
			for (int i = 0; i < lvlWidth; i++)
			{
				if(tileMap[i][j].getType() == 21)
				{
					LampTile t = (LampTile) tileMap[i][j];
					if(t.isActive())
					{
						lights.add(new Light(i * 50 + 25, j * 50 + 25, 200.0f, Color.white));
					}
				}
			}
		}

		return lights;

	}

	public int getLvlWidth()
	{
		return lvlWidth;
	}

	public void setLvlWidth(int lvlWidth)
	{
		this.lvlWidth = lvlWidth;
	}

	public int getLvlHeight()
	{
		return lvlHeight;
	}

	public void setLvlHeight(int lvlHeight)
	{
		this.lvlHeight = lvlHeight;
	}

	public void readMap(String filepath)
	{
		BufferedReader br = null;

		try
		{

			String sCurrentLine;

			br = new BufferedReader(new FileReader(filepath));

			int index = -1;
			sCurrentLine = br.readLine();
			String[] size = sCurrentLine.split(" ");
			lvlWidth = Integer.parseInt(size[0]);
			lvlHeight = Integer.parseInt(size[1]);

			index++;

			while ((sCurrentLine = br.readLine()) != null)
			{
				String[] tileStr = sCurrentLine.split(" ");

				if(tileStr.length == 3)
				{
					if(tileStr[0].equals("18"))
					{
						pairsLever.add(new Vector2f(Integer.parseInt(tileStr[1]), Integer.parseInt(tileStr[2])));
					}
				}else
				{

					for (int i = 0; i < lvlWidth; i++)
					{
						int tileType = Integer.parseInt(tileStr[i]);

						
					}
					index++;
				}
			}

		}catch (IOException e)
		{
			e.printStackTrace();
		}

		lightValue = new float[lvlWidth + 1][lvlHeight + 1][3];

	}

	public float[][][] getLightValue()
	{
		return this.lightValue;
	}

	public Tile getTile(int x, int y)
	{
		return tileMap[x][y];
	}

	public void setTile(int id, int x, int y)
	{
		Tile t = null;

		if(id == 0)
		{
			t = new GrassTile(id, x, y, app, gc);
			
		}else if(id == 2)
		{
			t = new DirtTile(id, x, y, app, gc);
		}else if(id == 10)
		{
			t = new DoorOpenTile(id, x, y, app, gc);
		}else if(id == 11)
		{
			t = new DoorClosedTile(id, x, y, app, gc);
		}else if(id == 1)
		{
			t = new WoodTile(id, x, y, app, gc);

		}else if(id == 12)
		{
			t = new WallHorizontalTile(id, x, y, app, gc);
			
		}else if(id == 13)
		{
			t = new WallVerticalTile(id, x, y, app, gc);
			
		}else if(id == 14)
		{
			t = new WallCornerTile1(id, x, y, app, gc);
			
		}else if(id == 15)
		{
			t = new WallCornerTile2(id, x, y, app, gc);

		}else if(id == 16)
		{
			t = new WallCornerTile3(id, x, y, app, gc);

		}else if(id == 17)
		{
			t = new WallCornerTile4(id, x, y, app, gc);
		}else if(id == 18)
		{
			t = new LeverTile(id, x, y, (int) pairsLever.get(0).x, (int) pairsLever.get(0).y, app, gc);
			pairsLever.remove(0);
		}else if(id == 19)
		{
			t = new TreeTile(id, x, y, app, gc);
		}else if(id == 21)
		{
			t = new LampTile(id, x, y, app, gc);
		}else if(id == 20)
		{
			t = new StoneTile(id, x, y, app, gc);

		}else if(id == 30)
		{
			t = new WaterTile(id, x, y, app, gc);

		}else if(id == 51)
		{
			t = new TableTopTile(id, x, y, app, gc);

		}else if(id == 52)
		{
			t = new TableMidTile(id, x, y, app, gc);

		}else if(id == 53)
		{
			t = new TableBotTile(id, x, y, app, gc);

		}else if(id == 54)
		{
			t = new ChairLTile(id, x, y, app, gc);

		}else if(id == 55)
		{
			t = new ChairRTile(id, x, y, app, gc);

		}

		tileMap[x][y] = t;
	}

	public Tile[][] getTileMap()
	{
		return this.tileMap;
	}

	public int getType(int x, int y)
	{
		return tileMap[x][y].type;
	}

	private void createMap()
	{
		tileMap = new Tile[lvlWidth][lvlHeight];
		for (int i = 0; i < lvlHeight; i++)
		{
			for (int j = 0; j < lvlHeight; j++)
			{

				setTile(getType(j, i), j, i);

			}

		}

	}

}
