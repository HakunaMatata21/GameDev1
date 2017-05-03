package net.hollowbit.tutorialland.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion 
{
	
	public static final float FRAME_LENGTH = 0.2f; //продължителност на експлозията.
	public static final int OFFSET = 8;
	public static final int SIZE = 32; 
	
	private static Animation<TextureRegion> anim = null; // това ще държи анимацията,по дефф.вътре няма да има нищо защото не е заредена.
	float x, y;
	float statetime;
	
	public boolean remove = false; //bool който ще показва дали трябва да се махне експлозията след изпълнението.
	
	public Explosion (float x, float y) //конструктора на експлозията
	{
		this.x = x - OFFSET;
		this.y = y - OFFSET;
		statetime = 0;
		
		if (anim == null) // ако анимацията не е заредена.
	anim = new Animation<TextureRegion>(FRAME_LENGTH,TextureRegion.split(new Texture("explosion.png"),SIZE,SIZE)[0]); //разцепи изображението explosion.png и зареди анимацията.
	}
	
	public void update (float deltatime)  
	{
		statetime += deltatime; //кара анимацията да се сменя според изминалото време.
		if (anim.isAnimationFinished(statetime)) //ако анимацията е свършила
			remove = true; //задействай командите по премахването на петното от екрана.
	}
	
	public void render (SpriteBatch batch) 
	{
		batch.draw(anim.getKeyFrame(statetime), x, y);
	}
	
}

/*
 
при оригиналния код дава грешка в рендер-а използва се същият фикс който се използва и в maingamescreen 
The method draw(Texture, float, float) in the type SpriteBatch is not applicable for the arguments (Object, float, float) 
in the Explosion class you need to change:
 private static Animation anim = null; --->  private static Animation<TextureRegion> anim = null;
 anim = new Animation(FRAME_LENGTH,TextureRegion.split(new Texture("explosion.png"),SIZE,SIZE)[0]); --> anim = new Animation<TextureRegion>(FRAME_LENGTH,TextureRegion.split(new Texture("explosion.png"),SIZE,SIZE)[0]); 
 The Animation class is generic (see docs) - meaning you should supply the type of object being animated which in your case would be TextureRegion since the keyframes are of type TextureRegion.﻿

*/