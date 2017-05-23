package net.hollowbit.tutorialland;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import net.hollowbit.tutorialland.screens.GameOverScreen;
import net.hollowbit.tutorialland.screens.MainGameScreen;
import net.hollowbit.tutorialland.screens.MainMenuScreen;
import net.hollowbit.tutorialland.tools.GameCamera;
import net.hollowbit.tutorialland.tools.ScrollingBackground;
@SuppressWarnings("unused")
public class SpaceGame extends Game 
{
public static final int WIDTH = 480; //широчина на екрана.
public static final int HEIGHT = 720; //височина на екрана.
public static boolean IS_MOBILE = false; //bool който ще проверява дали играта е пусната на андроид,по подразбиране е на НЕ.




public SpriteBatch batch;
public ScrollingBackground scrollingBackground;
public GameCamera cam;
	@Override
	public void create () 
	{
		
		cam = new GameCamera(WIDTH,HEIGHT);
		batch = new SpriteBatch();
		
		if (Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS) //дали играта е пусната на андроид или IOS.
			IS_MOBILE = true;
		IS_MOBILE = true;

		
		this.scrollingBackground = new ScrollingBackground();
		this.setScreen(new MainMenuScreen(this)); //кой да е първият прозорец който да изпълни.
		
	}

	@Override
	public void render () 
	{
		batch.setProjectionMatrix(cam.combined()); 
		super.render();
		
		
	}
	public void resize(int width,int height)
	{
	cam.update(width,height); 
	super.resize(width, height);
	}
	
}
