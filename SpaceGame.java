package net.hollowbit.tutorialland;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.hollowbit.tutorialland.screens.MainGameScreen;
import net.hollowbit.tutorialland.screens.MainMenuScreen;
@SuppressWarnings("unused")
public class SpaceGame extends Game 
{
public SpriteBatch batch;
public static final int WIDTH = 480;
public static final int HEIGHT = 720;
	
	@Override
	public void create () 
	{
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this)); //koi da e pyrviq prozorec koito shte se izpylni
		
	}

	@Override
	public void render () 
	{
		super.render();
		
	}
	
	
}
