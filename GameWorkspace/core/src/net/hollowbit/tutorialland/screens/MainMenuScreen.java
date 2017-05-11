package net.hollowbit.tutorialland.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import net.hollowbit.tutorialland.SpaceGame;
import net.hollowbit.tutorialland.tools.ScrollingBackground;

import com.badlogic.gdx.graphics.Texture;


public class MainMenuScreen implements Screen
{
	private static final int EXIT_BUTTON_WIDTH = 250; //широчина на изход бутона.
	private static final int EXIT_BUTTON_HEIGHT = 120; //височина на изход бутона.
	private static final int PLAY_BUTTON_WIDTH = 300; //широчина на играй бутона.
	private static final int PLAY_BUTTON_HEIGHT = 120; //височина на играй бутона.
	private static final int EXIT_BUTTON_Y = 100; // на каква височина да нарисува бутона.
	private static final int PLAY_BUTTON_Y = 230; // на каква височина да нарисува бутона.

	SpaceGame game;

Texture exitButtonActive;
Texture exitButtonInactive;
Texture playButtonActive;
Texture playButtonInactive;

public MainMenuScreen(SpaceGame game) // конструктора на главното меню
{
this.game=game;

playButtonActive = new Texture("play_button_active.png"); //инициализиране на текстура
playButtonInactive = new Texture("play_button_inactive.png"); //инициализиране на текстура
exitButtonActive = new Texture("exit_button_active.png"); //инициализиране на текстура
exitButtonInactive = new Texture("exit_button_inactive.png"); //инициализиране на текстура

game.scrollingBackground.setSpeedFixed(true);
game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);
}
	@Override
	public void show() 
	{
		
		
	}

	@Override
	public void render(float delta) 
	{

	    Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1); //Цвят на фон-а(RGB стойностите с който се задава са от 0 до 1 вместо от 0 до 255 защото е GL,демек 0 = 0,а 1=255)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		game.scrollingBackground.updateAndRender(delta,game.batch); // създаване на звездният фон.

		//Рисуване на бутона Изход.
		int x = SpaceGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2; 
		if(Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && SpaceGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && SpaceGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y)
		{
		game.batch.draw(exitButtonActive,x,EXIT_BUTTON_Y,EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
		if(Gdx.input.isTouched())
		Gdx.app.exit();
		}
		else
		{
		game.batch.draw(exitButtonInactive,x,EXIT_BUTTON_Y,EXIT_BUTTON_WIDTH,EXIT_BUTTON_HEIGHT);
		
		}
		
		//Рисуване на бутона Играй
		x = SpaceGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2; 
		if(Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && SpaceGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && SpaceGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) 
		{
		game.batch.draw(playButtonActive,x,PLAY_BUTTON_Y,PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);
		if(Gdx.input.justTouched())
		this.dispose();
		game.setScreen(new MainGameScreen(game));
		}
		else
		{
		game.batch.draw(playButtonInactive,x,PLAY_BUTTON_Y,PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);

		}
			
		game.batch.end();
		
	}

	@Override
	public void resize(int width, int height)  
	{
		
		
	}

	@Override
	public void pause() 
	{
	
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() 
	{
		
		
	}

	@Override
	public void dispose() 
	{
	
		
	}

}
