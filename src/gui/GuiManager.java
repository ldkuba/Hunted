package gui;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class GuiManager
{
	ArrayList<GuiElement> components;
	
 
	
	public GuiManager()
	{
		components = new ArrayList<GuiElement>();
		
	}
	
	public ArrayList<GuiElement> getComponents()
	{
		return this.components;
	}
	
	public void addComponent(GuiElement component)
	{
		components.add(component);
	}
	
	public void removeComponent(GuiElement component)
	{
		if(components.contains(component))
			components.remove(component);
	}
	
	public void draw(GameContainer gc, Graphics g)
	{
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i) instanceof Button)
			{
				Button b = (Button) components.get(i);
				if(b.isActive())
					b.draw(gc, g);
			}else if(components.get(i) instanceof MyTextField)
			{
				MyTextField t = (MyTextField) components.get(i);
				if(t.isActive())
				{
					t.draw(gc, g);
				}
			}if(components.get(i) instanceof Label)
			{
				Label l = (Label) components.get(i);
				if(l.isActive())
					l.draw(gc, g);
			}
		}
	}
	
	public GuiElement getComponent(String name)
	{
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).getName().equals(name))
			{
				return components.get(i);
			}
		}
		
		return null;
	}
}
