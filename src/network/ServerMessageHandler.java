package network;

import java.io.IOException;
import java.util.ArrayList;

import action.Action;
import action.MoveAction;
import action.ToggleDoorAction;
import action.ToggleLampAction;
import main.Main;
import method.StartNewTurnMethod;

public class ServerMessageHandler implements Runnable
{
	Server server;
	Main app;
	
	public ServerMessageHandler(Server server, Main app)
	{
		this.app = app;
		this.server = server;
	}
	
	
	
	public void run()
	{
		while(true)
		{
			String input = "";
			try
			{
				input = server.getInput();
			}catch (IOException e)
			{
				continue;
				//e.printStackTrace();
			}
			
			String type = input.split(" ")[0];
			
			if(type.equals("Joined"))
			{
				System.out.println(input.split(" ")[1]);
				
				if(app.isHunter())
				{
					this.server.send("Welcome Prey");
				}else
				{
					this.server.send("Welcome Hunter");
				}
			}else if(type.equals("NewTurn"))
			{
				int actionNum = Integer.parseInt(input.split(" ")[1]);
				int index = 2;
				
				ArrayList<Action> newActions = new ArrayList<Action>();
				
				String[] params = input.split(" ");
				
				for(int i = 0; i < actionNum; i++)
				{
					if(params[index].equals("MoveAction"))
					{
						index++;
						MoveAction action = new MoveAction(Integer.parseInt(params[index]));
						index++;
						
						newActions.add(action);
					}else if(params[index].equals("ToggleDoorAction"))
					{
						index++;
						ToggleDoorAction action = new ToggleDoorAction(Integer.parseInt(params[index]), Integer.parseInt(params[index+1]));
						index += 2;
						
						newActions.add(action);
					}else if(params[index].equals("ToggleLampAction"))
					{
						index++;
						ToggleLampAction action = new ToggleLampAction(Integer.parseInt(params[index]), Integer.parseInt(params[index+1]));
						index += 2;
						
						newActions.add(action);
					}
				}
				
				StartNewTurnMethod method = new StartNewTurnMethod(newActions, app);
				app.methodExecutor.add(method);
			}
		}
	}
}
