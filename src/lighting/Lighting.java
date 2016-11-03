package lighting;

import java.util.ArrayList;

import main.Globals;
import main.Main;

public class Lighting
{
	public static void updateLightMap(ArrayList<Light> lights, boolean lightingOff) {
		// for every vertex on the map (notice the +1 again accounting for the trailing vertex)
		for (int y=0;y<Main.currentLevel.getLvlHeight()+1;y++) {
			for (int x=0;x<Main.currentLevel.getLvlWidth()+1;x++) {
				// first reset the lighting value for each component (red, green, blue)
				for (int component=0;component<3;component++) {
					Main.currentLevel.lightValue[x][y][component] = 0;
				}
				
				// next cycle through all the lights. Ask each light how much effect
				// it'll have on the current vertex. Combine this value with the currently
				// existing value for the vertex. This lets us blend coloured lighting and 
				// brightness
				for (int i=0;i<lights.size();i++) {
					float[] effect = ((Light) lights.get(i)).getEffectAt(x*Globals.TILE_HEIGHT, y*Globals.TILE_HEIGHT, true);
					//System.out.println("Effect" + effect[0]);
					for (int component=0;component<3;component++) {
						Main.currentLevel.lightValue[x][y][component] += effect[component];
					}
				}
				
				// finally clamp the components to 1, since we don't want to 
				// blow up over the colour values
				for (int component=0;component<3;component++) {
					if (Main.currentLevel.lightValue[x][y][component] > 1) {
						Main.currentLevel.lightValue[x][y][component] = 1;
					}
				}
				
				if(lightingOff)
				{
					for (int component=0;component<3;component++) {
						Main.currentLevel.lightValue[x][y][component] = 1.0f;
					}
				}
			}
		}
	}
}
