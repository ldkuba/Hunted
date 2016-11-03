package action;

public class MoveAction extends Action
{
	int dir;
	/*
	 * 0 - up
	 * 1 - left
	 * 2 - down
	 * 3 - right
	
	*/
	public MoveAction(int dir)
	{
		this.dir = dir;
	}
	
	public void setDir(int dir)
	{
		this.dir = dir;
	}
	
	public int getDir()
	{
		return this.dir;
	}
}
