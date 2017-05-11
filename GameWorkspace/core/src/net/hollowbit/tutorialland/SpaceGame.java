package net.hollowbit.tutorialland;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.hollowbit.tutorialland.screens.GameOverScreen;
import net.hollowbit.tutorialland.screens.MainGameScreen;
import net.hollowbit.tutorialland.screens.MainMenuScreen;
import net.hollowbit.tutorialland.tools.ScrollingBackground;
@SuppressWarnings("unused")
public class SpaceGame extends Game 
{
public static final int WIDTH = 480; //широчина на екрана.
public static final int HEIGHT = 720; //височина на екрана.
	
public SpriteBatch batch;
public ScrollingBackground scrollingBackground;

	@Override
	public void create () 
	{
		batch = new SpriteBatch();
		this.setScreen(new GameOverScreen(this,0)); //кой да е първият прозорец който да изпълни.
		this.scrollingBackground = new ScrollingBackground();
	}

	@Override
	public void render () 
	{
		super.render();
		
	}
	public void resize(int width,int height)
	{
	this.scrollingBackground.resize(width,height);
	super.resize(width, height);
	}
	
}
