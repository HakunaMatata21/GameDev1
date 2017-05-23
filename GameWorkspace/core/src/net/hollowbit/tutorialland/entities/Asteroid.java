package net.hollowbit.tutorialland.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.hollowbit.tutorialland.SpaceGame;
import net.hollowbit.tutorialland.tools.CollisionRect;

public class Asteroid 
{
	public static final int SPEED = 250;
	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	private static Texture texture; //инстанция на текстура за да може да се нарисува астероида
	float x,y;
	public boolean remove = false; // бул който да показва дали трябва да се задейства премахването на астероида от екрана.
	CollisionRect rect; // създаване на инстанция на кол.рект за да може да се следи за допир-а на астероида с други обекти.


	public Asteroid(float x) //конструктора на астероида.
	{
	this.x = x;
	this.y = SpaceGame.HEIGHT; //
	this.rect = new CollisionRect(x,y,WIDTH,HEIGHT); //инициализиране на кол.рект
			
	if(texture == null) //ако не е заредена текстурата
	texture = new Texture("asteroid.png");  //зареждане на текстура от файл.

	}

	
	public void update(float deltatime) // update-a ще  следи за скорост,унищожение на астероида и т.н
	{
	y -= SPEED * deltatime; //скоростта на астероида
	
	if(y < -HEIGHT) //ако астероида излезе от екрана(от долната страна) 
	remove = true; //задействай унищожението му.
	rect.move(x, y); //това ще премества кол.рект-а редом с астероида за да са на една и съща локация


	}
	
	public void render(SpriteBatch batch) 
	{
	batch.draw(texture, x,y); //рисуване на астероида.
	}
	
	public CollisionRect getCollisionRect ()
	{
	return rect;	
	}
	
	public float getX() // 2 getter-a za x i y tyi kato sa private i nemojem da imame dostyp izvyn klasa asteroid.
	{
	return x;
	}
	
	public float getY()
	{
	return y;
	}
}
