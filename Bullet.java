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
private static Texture texture; // za da e po efektivno go pravim static za da moje kurshuma vseki pyt da izpolzva edin i sysht texture a ne vseki pyt da pravi nov

float x,y;
CollisionRect rect;
public boolean remove = false;


public Bullet(float x)
{
this.x = x;
this.y = DEFAULT_Y;

this.rect = new CollisionRect(x,y,WIDTH,HEIGHT);

if(texture == null)
texture = new Texture("bullet.png");

}

//update metoda shte proverqva za unishtojavaneto na kurshuma sled izlizaneto ot ekrana i t.n
public void update(float deltatime)
{
y += SPEED * deltatime; // kurshuma shte pytuva sys 500 piksela v sekunda.
//unishtoji kurshuma ako e izleznal ot ekrana
if(y > Gdx.graphics.getHeight())
remove = true;
rect.move(x, y);

}
//narisuvai kurshuma
public void render(SpriteBatch batch) //shte predadem spritebatch za da moje da risuvame. 
{
batch.draw(texture, x,y);
}
public CollisionRect getCollisionRect ()
{
return rect;	
}

}
