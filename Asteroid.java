package net.hollowbit.tutorialland.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.hollowbit.tutorialland.tools.CollisionRect;

public class Asteroid 
{
	public static final int SPEED = 250;
	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	private static Texture texture; 
	float x,y;
	public boolean remove = false;
	CollisionRect rect;


	public Asteroid(float x)
	{
	this.x = x;
	this.y = Gdx.graphics.getHeight();
	this.rect = new CollisionRect(x,y,WIDTH,HEIGHT);
			
	if(texture == null)
	texture = new Texture("asteroid.png");

	}

	
	public void update(float deltatime)
	{
	y -= SPEED * deltatime; 
	
	if(y < -HEIGHT)
	remove = true;
	rect.move(x, y);


	}
	
	public void render(SpriteBatch batch) 
	{
	batch.draw(texture, x,y);
	}
	
	public CollisionRect getCollisionRect ()
	{
	return rect;	
	}
}
