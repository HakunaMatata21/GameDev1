package net.hollowbit.tutorialland.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.hollowbit.tutorialland.SpaceGame;

public class DesktopLauncher 
{
	public static void main (String[] arg) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SpaceGame(), config);
		config.foregroundFPS = 60;
		config.width = SpaceGame.WIDTH;
		config.height = SpaceGame.HEIGHT;
		config.resizable = false; //Дали потребителя може да разтяга прозореца(default e true) 
	}
}
