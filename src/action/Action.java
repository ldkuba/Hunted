package action;

public class Action
{
	public Action()
	{
		
	}
	
	public static String parseAction(Action action)
	{
		if(action instanceof MoveAction)
		{
			MoveAction m = (MoveAction) action;
			
			return "MoveAction " + m.getDir();
		}else if(action instanceof ToggleDoorAction)
		{
			ToggleDoorAction m = (ToggleDoorAction) action;
			
			return "ToggleDoorAction " + m.getTileX() + " " + m.getTileY();
		}else if(action instanceof ToggleLampAction)
		{
			ToggleLampAction m = (ToggleLampAction) action;
			
			return "ToggleLampAction " + m.getTileX() + " " + m.getTileY();
		}
		
		return "";
	}
}
