package net.hollowbit.tutorialland.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameCamera 
{
private OrthographicCamera cam; //Контролира какво вижда потребителя.
private StretchViewport viewport; //Без значение на какво устройство потребителя ще отвори играта този код ще се погрижи изображенията да се разтегнат на зададените от нас,демек 720х480.	

public GameCamera(int width,int height)
{
	cam = new OrthographicCamera(); //Инициализиране.
	viewport = new StretchViewport(width, height, cam);//Инициализиране.
	viewport.apply();
	cam.position.set(width / 2, height / 2 , 0); //камерата по подразбиране не е наместена затова я центрираме.
	cam.update(); // камерата няма да се центрира докато не се изпълни този код.
}

public Matrix4 combined()
{
return cam.combined;
}

public void update(int width,int height)
{
viewport.update(width, height);// ако екрана се променя ние трябва да кажем на viewport-a да се смени.
}

public Vector2 getInputInGameWorld()
{
	Vector3 inputScreen = new Vector3(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY(),0);
    Vector3 unprojected = cam.unproject(inputScreen);
    return new Vector2(unprojected.x,unprojected.y);
}


}
