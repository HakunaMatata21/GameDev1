package net.hollowbit.tutorialland.screens;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.hollowbit.tutorialland.SpaceGame;
import net.hollowbit.tutorialland.entities.Bullet;

public class MainGameScreen implements Screen

{
	public static final float SPEED = 300;
	
	public static final int SHIP_WIDTH_PIXEL = 17;
	public static final int SHIP_HEIGHT_PIXEL = 32;
	public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
	public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3; 
    public static final float SHIP_ANIMATION_SPEED = 0.5f;
    public static final float ROLL_TIMER_SWITCH_TIME = 0.25f; // kolko vreme minava predi da smeni sprite-a na ship-a toest 1.5sek za prevkliuchvane na sprite za po-nalqvo i t.n
    public static final float SHOOT_WAIT_TIME = 0.3f; // taimer za kurshumite
    
    Animation<TextureRegion>[] rolls;
	float x;
	float y;
	int roll;
	float stateTime; // kolko vreme se vyrti animaciqta no nie ciklim //rolls e animation obekta
    float rollTimer;
	float shootTimer;
    SpaceGame game;
	ArrayList<Bullet> bullets;

    
    public MainGameScreen(SpaceGame game)
{
	this.game = game;
	y = 15;
	x = SpaceGame.WIDTH / 2 - SHIP_WIDTH / 2;
	bullets = new ArrayList<Bullet>();
	
	roll = 2; // kogato zapochva
	rollTimer = 0;
	rolls = new Animation[5];
	shootTimer = 0;
	
	TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"),SHIP_WIDTH_PIXEL,SHIP_HEIGHT_PIXEL); 
	
	rolls[0]=new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[2]); //nai-lqvo
	rolls[1]=new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[1]); // po-nalqvo
	rolls[2]=new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[0]); // sprite kogato koraba e v centyra
	rolls[3]=new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[3]); //po-nadqsno
	rolls[4]=new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[4]); //nai-vdqsno


}
@Override
public void show() 
{


	
	
}

@Override
public void render(float delta) 
{
	shootTimer += delta;
	//kod strelqne
	if(Gdx.input.isKeyPressed(Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME ) // ako se natisne space-bar i igracha e izchakal poveche ili ravno na 0.3 sekundi izstrelqi kurshum
	{
    shootTimer = 0;
    int offset = 4;
	//ako koraba e leko naklonen nalqvo ili nadqsno toest 1 ili 3 izmesti tochkata na strelqne
    if(roll==1 || roll==3)
	{
	offset = 8;
	}
    //ako koraba e dosta naklonen toest 0 ili 4 izmesti tochkata na strelqne
    if(roll==0 || roll==4)
	{
	offset = 16;
	}
    
    bullets.add(new Bullet(x+offset));
	bullets.add(new Bullet(x+SHIP_WIDTH-offset));
    }
	
	//update bullets
	ArrayList<Bullet> bulletsToRemove =new ArrayList<Bullet>();
	for (Bullet bullet:bullets) 
	{
	bullet.update(delta);
	if(bullet.remove)
	bulletsToRemove.add(bullet);
	}
	bullets.removeAll(bulletsToRemove);
	
		
	
	
	//ako e natisnat leviq buton
	 if(Gdx.input.isKeyPressed(Keys.LEFT))
	{ 
	x -= SPEED * Gdx.graphics.getDeltaTime();
	// shte proveri dali koraba izliza ot ekrana otlqvo
	if(x<0) 
	 {
	x=0;
	 }
	// ako igracha samo e natisnal leviq buton i ne e natisnal desniq v syshtoto vreme i roll e po golqmo ot 0
	if(Gdx.input.isKeyJustPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT) && roll>0) 
	{
	rollTimer -= ROLL_TIMER_SWITCH_TIME;//restart na taimera
	roll--;
	}
	
	//update roll.kogato se zadyrja nalqvo shte namalqvame timer-a za da otiva kym negativnite chisla i da smenq sprite-ove(zashtoto po-gore na spriteSheet-a nai-levite sprite-ove otivat kym 0 a desnite kym 4)
	rollTimer -= Gdx.graphics.getDeltaTime(); // izchislqva kolko vreme e minalo
	if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll>0) //izchislqva absolutnata stoinost na rollTimer(zashtoto otgore shte poluchim negativna stoinost i proverqva dali e minalo vremeto koeto sme zadali za smqna)
	 {
	rollTimer -= ROLL_TIMER_SWITCH_TIME; //restartirame taimera 
	roll--;
	 }
	}
	 //ako ne e natisnat leviq buton
	 else 
	 {
	if(roll < 2) // ako roll e po malko ot 2,demek centyra znachi trqbva da go vyrnem(za celta kopirame koda za update na sprite-a nadqsno)
	{
	rollTimer += Gdx.graphics.getDeltaTime(); // izchislqva kolko vreme e minalo
		
		if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) //izchislqva absolutnata stoinost na rollTimer(zashtoto otgore shte poluchim negativna stoinost i proverqva dali e minalo vremeto koeto sme zadali za smqna)
		 {
		rollTimer -= ROLL_TIMER_SWITCH_TIME; //restartirame taimera 
		roll++;
		 }
	}
	 }
	 
	 
	 
	 
	 //ako e natisnat desniq buton
	 if(Gdx.input.isKeyPressed(Keys.RIGHT))
	{
	x += SPEED * Gdx.graphics.getDeltaTime();
	
	//proverqva dali koraba ne izliza ot ekrana otdqsno
	if(x + SHIP_WIDTH > Gdx.graphics.getWidth()) 
	 {
	x = Gdx.graphics.getWidth() - SHIP_WIDTH;	
	 }
	
	// ako igracha samo e natisnal desniq buton i ne e natisnal leviq v syshtoto vreme i roll e po malko ot 4	
	if(Gdx.input.isKeyJustPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT) && roll<4) 
	{
	rollTimer = 0;//restart na taimera
	roll++;
	}
	
//update roll desen	
		rollTimer += Gdx.graphics.getDeltaTime(); // izchislqva kolko vreme e minalo
		if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) //izchislqva absolutnata stoinost na rollTimer(zashtoto otgore shte poluchim negativna stoinost i proverqva dali e minalo vremeto koeto sme zadali za smqna)
		 {
		rollTimer -= ROLL_TIMER_SWITCH_TIME; //restartirame taimera 
		roll++;
		
		 }
	}
	 // ako ne e natisnat desniq buton
	 else
	 {
	if(roll > 2) // ako roll e poveche ot 2,demek centyra znachi trqbva da go vyrnem(za celta kopirame koda za update na sprite-a nalqvo)
	{
		rollTimer -= Gdx.graphics.getDeltaTime(); // izchislqva kolko vreme e minalo
		if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) //izchislqva absolutnata stoinost na rollTimer(zashtoto otgore shte poluchim negativna stoinost i proverqva dali e minalo vremeto koeto sme zadali za smqna)
		 {
		rollTimer -= ROLL_TIMER_SWITCH_TIME; //restartirame taimera 
		roll--;
		 }
	}
	 }
	    
	 
	 stateTime += delta;
	    Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		for(Bullet bullet : bullets)
		{
		bullet.render(game.batch);	
		}
		game.batch.draw(rolls[roll].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);
		
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
public void resume() 
{
	
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