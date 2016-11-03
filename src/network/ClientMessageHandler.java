package network;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;

import action.Action;
import action.MoveAction;
import action.ToggleDoorAction;
import action.ToggleLampAction;
import main.Main;
import method.StartClientGameMethod;
import method.StartNewTurnMethod;

public class ClientMessageHandler implements Runnable
{
	Client client;
	Main app;
	
	GameContainer gc;
	
	public ClientMessageHandler(Client client, Main app, GameContainer gc)
	{
		this.app = app;
		this.client = client;
		this.gc = gc;
	}
	
	public void run()
	{
		while(true)
		{
			String input = "";
			
			try
			{
				input = client.getInput();
			}catch (IOException ex)
			{
				//ex.printStackTrace();
				continue;
			}
			
			String type = input.split(" ")[0];
				
			if(type.equals("Welcome"))
			{
				
				if(input.split(" ")[1].equals("Hunter"))
				{					
					StartClientGameMethod method = new StartClientGameMethod(true, app, gc);
					
					app.methodExecutor.add(method);					
			
				}else if(input.split(" ")[1].equals("Prey"))
				{	
					StartClientGameMethod method = new StartClientGameMethod(false, app, gc);
					app.methodExecutor.add(method);	
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
