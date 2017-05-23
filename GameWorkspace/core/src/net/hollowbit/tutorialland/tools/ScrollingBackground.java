package net.hollowbit.tutorialland.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.hollowbit.tutorialland.SpaceGame;

public class ScrollingBackground 
{
public static final int DEFAULT_SPEED = 80;
public static final int ACCELERATION = 50;
public static final int GOAL_REACH_ACCELERATION = 200; 
	
Texture image; //Инициализиране на текстурата.
float y1,y2;//имаме 2 Y,защото начина по който ще се сменя фон-а ще е изображение 1 да слиза надолу,след като излезе от екрана ние ще виждаме изображение 2,след това изображение 1 се поставя над изображение 2 и така когато изображение 2 също изчезне от екрана изобр.1 ще заеме неговото място и така ще се направи цикъл.
int speed; //измерва се във пиксели в секунда. ,ще намалява или ще се ускорява за да успее да стигне до goalSpeed точно навреме.
int goalSpeed; //целта на изображенията.
float imageScale;
boolean speedFixed; // за да може ефекта с забързването на фон-а да се случва само в gameScreen. демек ще е fixed там където НЕ искаме да има такъв ефект.


public ScrollingBackground() //конструктор.
{
image = new Texture("stars_background.png"); //зареждане на изображението.
y1 = 0;
y2 = image.getHeight();
goalSpeed = DEFAULT_SPEED;
imageScale = SpaceGame.WIDTH / image.getWidth();
speedFixed = true;

}

public void updateAndRender(float deltatime,SpriteBatch batch)
{
//Балансиране на скоростта.
if(speed < goalSpeed)
{
speed +=GOAL_REACH_ACCELERATION * deltatime;
if(speed > goalSpeed)
speed = goalSpeed;

}
else if (speed > goalSpeed)
{
speed -=GOAL_REACH_ACCELERATION * deltatime;	
if(speed < goalSpeed)
  speed = goalSpeed;
 
}

if(!speedFixed) //ако искаме да създадем ефекта на фон-а.
speed += ACCELERATION * deltatime;
	
y1 -= speed * deltatime; //за да може изображението да се движи надолу.
y2 -= speed * deltatime;

//ако изображението е на дъното на екрана и вече не се вижда,го сложи над другото изображение.
if(y1 + image.getHeight() * imageScale <= 0)
y1 = y2 + image.getHeight() * imageScale;

if(y2 + image.getHeight() * imageScale <= 0)
y2 = y1 + image.getHeight() * imageScale;

//Render,рисуване на 2те изображения с различни Y стойности(X-овете винаги ще са 0)
batch.draw(image, 0, y1, SpaceGame.WIDTH, image.getHeight() * imageScale);
batch.draw(image, 0, y2, SpaceGame.WIDTH, image.getHeight() * imageScale);

}

public void setSpeed(int goalSpeed)
{
this.goalSpeed = goalSpeed;
}

public void setSpeedFixed(boolean speedFixed)
{
this.speedFixed = speedFixed;
}

}
