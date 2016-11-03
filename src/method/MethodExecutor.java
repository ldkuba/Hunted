package method;

import java.util.ArrayList;

public class MethodExecutor
{
	ArrayList<Method> list;
	
	public MethodExecutor()
	{
		list = new ArrayList<Method>();
	}
	
	public void execute()
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i) instanceof StartClientGameMethod)
			{
				StartClientGameMethod method = (StartClientGameMethod) list.get(i);
				
				method.execute();
				
				System.out.println("Executing method");
			}else if(list.get(i) instanceof StartNewTurnMethod)
			{
				StartNewTurnMethod method = (StartNewTurnMethod) list.get(i);
				
				method.execute();
			}
		}
		
		list.clear();
	}
	
	public void add(Method method)
	{
		list.add(method);
	}
}
