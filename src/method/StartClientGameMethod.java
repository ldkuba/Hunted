package method;

import org.newdawn.slick.GameContainer;

import main.Main;

public class StartClientGameMethod extends Method
{
	boolean isHunter = false;
	Main app;
	GameContainer gc;
	
	public StartClientGameMethod(boolean isHunter, Main app, GameContainer gc)
	{
		this.isHunter = isHunter;
		this.app = app;
		this.gc = gc;
	}
	
	public void execute()
	{
		app.startClientGame(isHunter, gc);
	}
}
