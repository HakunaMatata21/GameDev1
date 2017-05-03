package net.hollowbit.tutorialland.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.hollowbit.tutorialland.SpaceGame;
import net.hollowbit.tutorialland.entities.Asteroid;
import net.hollowbit.tutorialland.entities.Bullet;
import net.hollowbit.tutorialland.entities.Explosion;
import net.hollowbit.tutorialland.tools.CollisionRect;

public class MainGameScreen implements Screen 
{
	
	public static final float SPEED = 300; //скорост на кораба.
	
	public static final float SHIP_ANIMATION_SPEED = 0.5f; //колко бърза ще бъде анимацията на кораба.
	public static final int SHIP_WIDTH_PIXEL = 17; //широчина на кораба.
	public static final int SHIP_HEIGHT_PIXEL = 32; //височина на кораба.
	public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
	public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3;
	
	public static final float ROLL_TIMER_SWITCH_TIME = 0.25f;//Колко време минава преди да смени sprite-a на кораба,тоест 1.5секунди преди смяна.
	public static final float SHOOT_WAIT_TIME = 0.3f; // таймер за куршумите за да не спрейва.
	
	public static final float MIN_ASTEROID_SPAWN_TIME = 0.3f;//минимум 0,3 секунди за създаване на нов астероид.
	public static final float MAX_ASTEROID_SPAWN_TIME = 0.6f;//максимум 0,6 секунди за създаване на нов астероид.
	
 Animation<TextureRegion>[] rolls; //създава обект rolls който ще бъде използван да държи различните състояния на спрайт-а на кораба.
	
	float x;
	float y;
	int roll;
	float rollTimer; //таймер за смяна на състоянието на спрайт-а на кораба.
	float stateTime;//колко време се върти анимацията(rolls е аним.обекта)  
	float shootTimer; //таймер за стреляне.
	float asteroidSpawnTimer;//таймер за създаване на астероиди.
	
	Random random;
	SpaceGame game;
	
	ArrayList<Bullet> bullets; //инициализиране на списък с масиви за куршумите.
	ArrayList<Asteroid> asteroids;//инициализиране на списък с масиви за астероидите.
	ArrayList<Explosion> explosions;//инициализиране на списък с масиви за експлозиите.
	
	Texture blank;//инициализиране на текстурата(изображението с един пиксел) който ще използваме за рисуването на кръвта.
	
	BitmapFont scoreFont;//инициализиране на шрифта който ще използваме за рисуването на резултата.
	
	CollisionRect playerRect; //инициализиране на кол.рект-а на играча.
	
	float health = 1; //0 = мъртъв,1 = пълна кръв.
	int score; // за изчисляване на резултата.
	
	public MainGameScreen (SpaceGame game) 
	{
		this.game = game;
		y = 15;
		x = SpaceGame.WIDTH / 2 - SHIP_WIDTH / 2;
		bullets = new ArrayList<Bullet>();//създаване на списъка който е инициализиран.
		asteroids = new ArrayList<Asteroid>();//създаване на списъка който е инициализиран.
		explosions = new ArrayList<Explosion>();//създаване на списъка който е инициализиран.
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));//създаване на шрифта който инициализирахме.
		playerRect = new CollisionRect(0,0,SHIP_WIDTH,SHIP_HEIGHT); //създаване на кол.рект-а на играча който инициализирахме.
		
		blank = new Texture("blank.png");//създаване на текстурата за рисуването на кръвта която инициализирахме.
		
		score = 0; //по подразбиране резултата започва от 0.
		
		random = new Random();
		asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;  // създава случайна стойност между 0,3 о 0,6 секунди.
		
		shootTimer = 0;
		
		roll = 2; // по подразбиране състоянието на спрайт-а на кораба ще е без наклон.
		rollTimer = 0; //нулиране на таймера.
		rolls = new Animation[5]; //създаването на масива който съдържа състоянията на спрайт-а на кораба.
		
		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL); //това ще разцепи sprite sheet-a който сме предоставили нарязвайки го на няколко спрайт-а на кораба(състояния)
		
		rolls[0] = new Animation<TextureRegion>(SHIP_ANIMATION_SPEED, rollSpriteSheet[2]);//sprite-a когато кораба е максимално наклонен наляво.
		rolls[1] = new Animation<TextureRegion>(SHIP_ANIMATION_SPEED, rollSpriteSheet[1]);//sprite-a когато кораба е малко наклонен наляво.(или бързо натиснат ляв бутон)
		rolls[2] = new Animation<TextureRegion>(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]);//когато кораба няма наклон.
		rolls[3] = new Animation<TextureRegion>(SHIP_ANIMATION_SPEED, rollSpriteSheet[3]);////sprite-a когато кораба е малко наклонен надясно.(или бързо натиснат десен бутон)
		rolls[4] = new Animation<TextureRegion>(SHIP_ANIMATION_SPEED, rollSpriteSheet[4]);//sprite-a когато кораба е максимално наклонен надясно.
	}
	
	@Override
	public void show () 
	{
		
	}

	@Override
	public void render (float delta) 
	{
		//Код за стреляне
		shootTimer += delta;
		if (Gdx.input.isKeyPressed(Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME) //ако се натисне бутона за стреляне и играча е изчакал повече или равно на 0,3 секунди изстреляй нов куршум.
		{
			shootTimer = 0;
		    int offset = 4;
		
			if (roll == 1 || roll == 3)//Ако кораба е леко наклонен наляво или надясно тоест в състояние на sprite-a 1 или 3:
				offset = 8; //измести точката на стреляне.
		
			if (roll == 0 || roll == 4)//ако кораба е максимално наклонен наляво или надясно тоест в състояние на sprite-a 0 или 4:
				offset = 16;//измести точката на стреляне.
			
			bullets.add(new Bullet(x + offset)); //създаване на нов куршум според това в кое състояние е sprite-a на кораба.(от лявото оръдие)
			bullets.add(new Bullet(x + SHIP_WIDTH - offset)); //създаване на нов куршум според това в кое състояние е sprite-a на кораба.(от дясното оръдие)
		}
		
		//Код за създаване на астероиди.
		asteroidSpawnTimer -= delta; //таймер.
		
		if (asteroidSpawnTimer <= 0) 
		{
			asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME; //оправя времето което ще бъде изминато преди създаването на астероида.
			asteroids.add(new Asteroid(random.nextInt(Gdx.graphics.getWidth() - Asteroid.WIDTH))); //създава астероида като се грижи за това астероида да бъде създаден на случайна локация,но да не излиза от екрана.
		}
		
		//Код за обновяване на астероидите.
		ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>(); //създава списък с масиви за астероиди които не са нужни.
		for (Asteroid asteroid : asteroids) //ще проверява всеки един създаден астероид. 
		{
			asteroid.update(delta); //извикване на метод от астерод класът.
			if (asteroid.remove) //ако булеана remove = true значи астероида трябва да бъде премахнат от екрана.
				asteroidsToRemove.add(asteroid); //вкарай астероида който трябва да се премахне във списъка с масиви за премахване.
		}
		
		//Код за обновяване на куршумите.
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>(); //създава списък с масиви за куршуми които не са нужни.
		for (Bullet bullet : bullets) //ще проверява всеки един създаден куршум. 
		{
			bullet.update(delta); //извикване на метод от куршум класът.
			if (bullet.remove) //ако булеана remove = true значи куршума трябва да бъде премахнат от екрана.
				bulletsToRemove.add(bullet);//вкарай куршума който трябва да се премахне във списъка с масиви за премахване.
		}
		
       	//Код за обновяване на експлозиите.
				ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();//създава списък с масиви за експлозии които не са нужни.
				for (Explosion explosion : explosions) //ще проверява всяка една създадена експлозия. 
				{
					explosion.update(delta); //извикване на метод от експлозия класът.
					if (explosion.remove) //ако булеана remove = true значи експлозията трябва да бъде премахната от екрана.
						explosionsToRemove.add(explosion);//вкарай експлозията която трябва да се премахне във списъка с масиви за премахване.
				}
				explosions.removeAll(explosionsToRemove); // премахва всички sprite-ове на експлозии които са приключили.
		
		//Код за движение.
		if (Gdx.input.isKeyPressed(Keys.LEFT)) //ако е натиснат левият бутон.
		{
			x -= SPEED * Gdx.graphics.getDeltaTime(); // премествай кораба наляво.
			
			if (x < 0) // ще се погрижи кораба да не излиза от лявата страна на екрана.
				x = 0;
			
			if (Gdx.input.isKeyJustPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT) && roll > 0) //Ако играча само е натиснал левият бутон без да го задържа и не е натиснал десният бутон едновременно.

			{
				rollTimer = 0; //рестартирай таймера за sprite-a на кораба.
				roll--; 
			}
			
			//Код за обновяване на въртенето на кораба.
			rollTimer -= Gdx.graphics.getDeltaTime(); 
			if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) //ще изчисли дали е минало времето за смяна на спрайт-а.
			{
				rollTimer -= ROLL_TIMER_SWITCH_TIME; 
				roll--; 
			}
		} 
		else // в случай,че не е натиснат левият бутон.
		{
			if (roll < 2) //и състоянието на спрайт-а е да 
			{
				
				rollTimer += Gdx.graphics.getDeltaTime();
				if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) //обнови състоянието на спрайт-а връщайки го към това да няма наклон.
				{
					rollTimer -= ROLL_TIMER_SWITCH_TIME;
					roll++;
				}
			}
		}
		
		
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) //Ако е натиснат десният бутон.
		{
			x += SPEED * Gdx.graphics.getDeltaTime();
			
			if (x + SHIP_WIDTH > Gdx.graphics.getWidth()) //ако кораба излиза от екрана отдясно.
				x = Gdx.graphics.getWidth() - SHIP_WIDTH; //грижи се за това кораба да не излиза отдясно на екрана.
			
			
			if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT) && roll < 4) //ако играча само е натиснал десния бутон и не е натиснал левия бутон едновременно.
			{
				rollTimer = 0; //рестарт на таймера.
				roll++;
			}
			
			//Код за обновяване на въртенето на кораба.
			rollTimer += Gdx.graphics.getDeltaTime();
			if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) //изчислява АБСОЛЮТНАТА стойност на rollTimer(защото отгоре ще получим негативна стойност)и проверява дали е минало времето което сме задали за смяна. 
			{
				rollTimer -= ROLL_TIMER_SWITCH_TIME; //рестартираме таймера.
				roll++;
			}
		} 
		else 
		{
			if (roll > 2) 
			{
				
				rollTimer -= Gdx.graphics.getDeltaTime();
				if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) 
				{
					rollTimer -= ROLL_TIMER_SWITCH_TIME;
					roll--;
				}
			}
		}
		
		playerRect.move(x, y);//това ще се погрижи кол.рект-а на играча да се движи заедно със самия обект т.е кораба.
		
		//След приключване на всички обновления на състоянията провери за допир.
		for (Bullet bullet : bullets) //провери всеки куршум.
		{
			for (Asteroid asteroid : asteroids)//провери всеки астероид. 
			{
				if (bullet.getCollisionRect().collidesWith(asteroid.getCollisionRect())) //ако има допир между куршум и астероид.
				{
					bulletsToRemove.add(bullet); //добави куршума в списъка с масиви за премахване.
					asteroidsToRemove.add(asteroid); //добави астероида в списъка с масиви за премахване.
					score += 100; //добави 100 точки към резултата.
					explosions.add(new Explosion(asteroid.getX(),asteroid.getY())); //създай експлозия там където се е случил допира.
				}
			}
		}
		
		bullets.removeAll(bulletsToRemove); //изчисти всички куршуми събрани във списъка с масиви на куршумите за премахване.
		
		
		for (Asteroid asteroid : asteroids) //Ще проверява всеки един създаден астероид всяка една секунда.
		{
			if (asteroid.getCollisionRect().collidesWith(playerRect))  //ако има допир между астероида и кораба.
			{
				asteroidsToRemove.add(asteroid); //добави астероида в списъка с масиви за премахване.
				health -= 0.1; //намали кръвта с 10%.
			}
}
		asteroids.removeAll(asteroidsToRemove); //изчисти всички астероиди събрани във списъка с масиви на астероидите за премахване.
		stateTime += delta;

		Gdx.gl.glClearColor(0, 0, 0, 1); //RGB стойности държащи цветовете на фон-а,това са GL стойности така,че 0 = 0,1 = 255.(нагласят се чрез float).
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
		scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - scoreLayout.height - 10); //Рисува резултата в центъра.
		
		for (Bullet bullet : bullets) //за всеки куршум.
		{
			bullet.render(game. batch);//извиквай метода рендер от неговия клас(рисуване)
		}
		
		for (Asteroid asteroid : asteroids) //за всеки астероид.
		{
			asteroid.render(game.batch); //извиквай метода рендер от неговия клас(рисуване)
		}
		
		for (Explosion explosion : explosions)//за всяка експлозия.
		{
		explosion.render(game.batch);//извиквай метода рендер от неговия клас(рисуване)
		}
		
		
	    //Рисуване на кръвта.
	    
	    if(health > 0.6f) //ако кръвта е по-малко от 60%
		game.batch.setColor(Color.GREEN); //цвета на лентата с кръв ще е зелен.
	    else if(health > 0.2f) //ако кръвта е между 20% и 60%
		game.batch.setColor(Color.YELLOW); //цвета на лентата с кръв ще е жълт.
	    else //ако кръвта е по-малко от 20%
		game.batch.setColor(Color.RED); //цвета й ще е червен.

	    game.batch.draw(blank,0,0,Gdx.graphics.getWidth() * health,5); //Рисува лентата с кръв като използва един бял пиксел,рисувайки го по цялата широчина на екрана умножено по стойността на кръвта,така когато кръвта намалява лентичката ще се смалява. 
	    game.batch.setColor(Color.WHITE);//бял цвят на лентата.
		
	    game.batch.draw(rolls[roll].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT); //рисува кораба.
		
		game.batch.end();
	}

	@Override
	public void resize (int width, int height) {
		
	}

	@Override
	public void pause () {
		
	}

	@Override
	public void resume () {
		
	}

	@Override
	public void hide () {
		
	}

	@Override
	public void dispose () {
		
	}

}	

/*
Във уроците на hollowbit са използвани стари команди които трябва да бъдат преправени за да работи кода
 Animation[] rolls; -->  Animation<TextureRegion>[] rolls;

//Update roll if right button just clicked --> оригиналният код не работи и при бързо натискане на десния бутон кораба се накланя леко наляво вместо надясно.
			if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT) && roll < 4) 
			{
				rollTimer = 0;
				roll++;
			}




*/