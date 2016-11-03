package method;

import java.util.ArrayList;

import action.Action;
import main.Main;

public class StartNewTurnMethod extends Method
{
	private ArrayList<Action> actions;
	Main app;
	
	public StartNewTurnMethod(ArrayList<Action> actions, Main app)
	{
		this.actions = actions;
		this.app = app;
	}
	
	public void execute()
	{
		app.startNewTurn(actions);
	}
}
