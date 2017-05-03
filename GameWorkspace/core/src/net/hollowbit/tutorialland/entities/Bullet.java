package net.hollowbit.tutorialland.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.hollowbit.tutorialland.tools.CollisionRect;

public class Bullet 
{
public static final int SPEED = 500;
public static final int DEFAULT_Y = 40;
public static final int WIDTH = 3;
public static final int HEIGHT = 12;
private static Texture texture; //за да е по ефективно го правим статичен за да може куршума всеки път да използва една и съща текстура,а не всеки път да създава нова.

float x,y;
CollisionRect rect; // създаване на инстанция на кол.рект за да може да се следи за допир-а на куршума с други обекти.
public boolean remove = false; // бул който да показва дали трябва да се задейства премахването на куршума от екрана.


public Bullet(float x) //конструктора на куршума
{
this.x = x;
this.y = DEFAULT_Y;

this.rect = new CollisionRect(x,y,WIDTH,HEIGHT); // създава се кол.рект за да може да се следи за допир с други обекти.

if(texture == null) //ако не е заредена текстурата
texture = new Texture("bullet.png"); //зареди изображението от файла.

}

//update метод-а ще проверява постоянно за унищожаването на куршума след излизането му от екрана и т.н
public void update(float deltatime)
{
y += SPEED * deltatime; // куршума ще пътува със 500 пиксела в секунда.
if(y > Gdx.graphics.getHeight()) //ако куршума е излезнал от екрана.
remove = true; //задействай унищожаването му.
rect.move(x, y); //това ще премества кол.рект-а редом с куршума за да са на една и съща локация

}

public void render(SpriteBatch batch) 
{
batch.draw(texture, x,y);//рисуване на куршума
}
public CollisionRect getCollisionRect ()
{
return rect;	
}

}
