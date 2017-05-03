package net.hollowbit.tutorialland;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.hollowbit.tutorialland.screens.MainGameScreen;
import net.hollowbit.tutorialland.screens.MainMenuScreen;
@SuppressWarnings("unused")
public class SpaceGame extends Game 
{
public SpriteBatch batch;
public static final int WIDTH = 480; //широчина на екрана.
public static final int HEIGHT = 720; //височина на екрана.
	
	@Override
	public void create () 
	{
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this)); //кой да е първият прозорец който да изпълни.
		
	}

	@Override
	public void render () 
	{
		super.render();
		
	}
	
	
}
