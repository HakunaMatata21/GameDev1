package net.hollowbit.tutorialland.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

import net.hollowbit.tutorialland.SpaceGame;
import net.hollowbit.tutorialland.tools.ScrollingBackground;

public class GameOverScreen implements Screen 
{
	SpaceGame game;//за да можем да използваме batch-a за да рисуваме.
	int score,highscore;
	private static final int BANNER_WIDTH = 350;
	private static final int BANNER_HEIGHT = 100;
	Texture gameOverBanner;
	BitmapFont scoreFont;
	
	
	
	public GameOverScreen(SpaceGame game,int score) //резултата от maingamescreen ще бъде прехвърлен на този екран.
	{
		this.game=game;
		this.score=score;
		
		//Взима резултата от save файла.
		Preferences prefs = Gdx.app.getPreferences("SpaceGame"); //влиза в файла на играта.
		this.highscore = prefs.getInteger("highscore",0); //търси highscore,ако не намери тогава задава 0 на highscore.
		
		
		if(score>highscore)//проверка дали резултата на играча бие най-високия резултат
		{
		prefs.putInteger("highscore",score); //записва резултата като новият най-висок резултат
		prefs.flush(); //Запазва файла(без тази команда нищо няма да бъде запазено).
		}
	
	   //Зареждане на текстури и шрифтове. 
	gameOverBanner = new Texture("game_over.png");
	scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
	
	game.scrollingBackground.setSpeedFixed(false);
	game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

	
	
	}
	
	
	@Override
	public void show()
	{
	
	
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 1); //RGB стойности държащи цветовете на фон-а,това са GL стойности така,че 0 = 0,1 = 255.(нагласят се чрез float).
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.scrollingBackground.updateAndRender(delta,game.batch); // създаване на звездният фон.

		
		game.batch.draw(gameOverBanner,Gdx.graphics.getWidth() / 2 - BANNER_WIDTH / 2,Gdx.graphics.getHeight() - BANNER_HEIGHT - 15,BANNER_WIDTH,BANNER_HEIGHT);//Ще постави банер-а в средата и 15 пиксела под горната граница.
		
		GlyphLayout scoreLayout = new GlyphLayout(scoreFont,"Score: \n" + score,Color.WHITE,0,Align.left,false); // ще изпише резултата,0 е широчината която няма да използваме затова е 0,false е bool asking wheter to wrap it or no?
		GlyphLayout highscoreLayout = new GlyphLayout(scoreFont,"Highscore: \n" + highscore,Color.WHITE,0,Align.left,false); //ще изпише най-високият резултат.
		scoreFont.draw(game.batch,scoreLayout,Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2,Gdx.graphics.getHeight()-BANNER_HEIGHT - 15 * 2);
		scoreFont.draw(game.batch,highscoreLayout,Gdx.graphics.getWidth() / 2 - highscoreLayout.width / 2,Gdx.graphics.getHeight()-BANNER_HEIGHT - scoreLayout.height - 15 * 3);

		GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont,"Try Again");
		GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont,"Main Menu");
		
		float tryAgainX = Gdx.graphics.getWidth() / 2 - tryAgainLayout.width / 2; //Позиционира Х на "Try Again" във GameOverScreen
		float tryAgainY = Gdx.graphics.getHeight() /2 - tryAgainLayout.height / 2; //Позиционира Y на "Try Again" във GameOverScreen
		float mainMenuX = Gdx.graphics.getWidth() / 2 - mainMenuLayout.width / 2; //Позиционира Х на "Main Menu" във GameOverScreen
		float mainMenuY = Gdx.graphics.getHeight() /2 - mainMenuLayout.height / 2 - tryAgainLayout.height - 15; //Позиционира Y на "Main Menu" във GameOverScreen
		
		
		float touchX = Gdx.input.getX();
		float touchY=Gdx.graphics.getHeight()- Gdx.input.getY();
		
		
		//Код за изпълнение при натискане на tryAgain или mainMenu
		if(Gdx.input.isTouched())
		{
		
			//TryAgain
		if(touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY)
		  {
		this.dispose(); 
		game.batch.end(); // край на рисуването.
		game.setScreen(new MainGameScreen(game)); // прехвърли в екрана за игра.
		return; 
		  }
		
		//mainMenu
		if(touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY)
		  {
		this.dispose(); 
		game.batch.end(); // край на рисуването.
		game.setScreen(new MainMenuScreen(game)); // прехвърли в екрана за игра.
		return; 
		  }
		}
		
		//Рисуване на бутоните
		scoreFont.draw(game.batch,tryAgainLayout,tryAgainX,tryAgainY);
		scoreFont.draw(game.batch,mainMenuLayout,mainMenuX,mainMenuY);
		game.batch.end();
        }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
