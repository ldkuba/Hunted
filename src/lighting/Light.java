package lighting;

import org.newdawn.slick.Color;

public class Light
{
	private float xpos;
	private float ypos;
	private float strength;
	private Color col;

	private boolean active;
	
	public Light(float x, float y, float str, Color col)
	{
		xpos = x;
		ypos = y;
		strength = str;
		this.col = col;
	}

	public void setLocation(float x, float y)
	{
		xpos = x;
		ypos = y;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	public boolean getActive()
	{
		return this.active;
	}
	
	
	public float[] getEffectAt(float x, float y, boolean colouredLights)
	{

		float dx = (x - xpos);
		float dy = (y - ypos);
		float distance2 = (dx * dx) + (dy * dy);
		float effect = 1 - (distance2 / (strength * strength));
		
		
		if(effect < 0)
		{
			effect = 0;
		}

		if(colouredLights)
		{
			return new float[] { col.r * effect, col.g * effect, col.b * effect };
		}else
		{
			return new float[] { effect, effect, effect };
		}
	}
}
