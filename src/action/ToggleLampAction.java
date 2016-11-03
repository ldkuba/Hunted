package action;

public class ToggleLampAction extends Action
{
	int tileX, tileY;
	
	public ToggleLampAction(int tileX, int tileY)
	{
		this.tileX = tileX;
		this.tileY = tileY;
	}

	public int getTileX()
	{
		return tileX;
	}

	public void setTileX(int tileX)
	{
		this.tileX = tileX;
	}

	public int getTileY()
	{
		return tileY;
	}

	public void setTileY(int tileY)
	{
		this.tileY = tileY;
	}
}
	
