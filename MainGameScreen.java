package net.hollowbit.tutorialland.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.hollowbit.tutorialland.SpaceGame;
import net.hollowbit.tutorialland.entities.Asteroid;
import net.hollowbit.tutorialland.entities.Bullet;

public class MainGameScreen implements Screen {
	
	public static final float SPEED = 300;
	
	public static final float SHIP_ANIMATION_SPEED = 0.5f;
	public static final int SHIP_WIDTH_PIXEL = 17;
	public static final int SHIP_HEIGHT_PIXEL = 32;
	public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
	public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3;
	
	public static final float ROLL_TIMER_SWITCH_TIME = 0.25f;
	public static final float SHOOT_WAIT_TIME = 0.3f;
	
	public static final float MIN_ASTEROID_SPAWN_TIME = 0.3f;
	public static final float MAX_ASTEROID_SPAWN_TIME = 0.6f;
	
	   Animation<TextureRegion>[] rolls;
	
	float x;
	float y;
	int roll;
	float rollTimer;
	float stateTime;
	float shootTimer;
	float asteroidSpawnTimer;
	
	Random random;
	
	SpaceGame game;
	
	ArrayList<Bullet> bullets;
	ArrayList<Asteroid> asteroids;
	BitmapFont scoreFont;
	int score;
	
	public MainGameScreen (SpaceGame game) 
	{
		this.game = game;
		y = 15;
		x = SpaceGame.WIDTH / 2 - SHIP_WIDTH / 2;
		bullets = new ArrayList<Bullet>();
		asteroids = new ArrayList<Asteroid>();
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
		
		score = 0;
		
		random = new Random();
		asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
		
		shootTimer = 0;
		
		roll = 2;
		rollTimer = 0;
		rolls = new Animation[5];
		
		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);
		
		rolls[0] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[2]);//All left
		rolls[1] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[1]);
		rolls[2] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]);//No tilt
		rolls[3] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[3]);
		rolls[4] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[4]);//Right
	}
	
	@Override
	public void show () {
		
	}

	@Override
	public void render (float delta) {
		//Shooting code
		shootTimer += delta;
		if (Gdx.input.isKeyPressed(Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME) {
			shootTimer = 0;
			
			int offset = 4;
			if (roll == 1 || roll == 3)//Slightly tilted
				offset = 8;
			
			if (roll == 0 || roll == 4)//Fully tilted
				offset = 16;
			
			bullets.add(new Bullet(x + offset));
			bullets.add(new Bullet(x + SHIP_WIDTH - offset));
		}
		
		//Asteroid spawn code
		asteroidSpawnTimer -= delta;
		if (asteroidSpawnTimer <= 0) {
			asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
			asteroids.add(new Asteroid(random.nextInt(Gdx.graphics.getWidth() - Asteroid.WIDTH)));
		}
		
		//Update asteroids
		ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();
		for (Asteroid asteroid : asteroids) {
			asteroid.update(delta);
			if (asteroid.remove)
				asteroidsToRemove.add(asteroid);
		}
		
		//Update bullets
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		for (Bullet bullet : bullets) {
			bullet.update(delta);
			if (bullet.remove)
				bulletsToRemove.add(bullet);
		}
		
		//Movement code
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {//Left
			x -= SPEED * Gdx.graphics.getDeltaTime();
			
			if (x < 0)
				x = 0;
			
			//Update roll if button just clicked
			if (Gdx.input.isKeyJustPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT) && roll > 0) {
				rollTimer = 0;
				roll--;
			}
			
			//Update roll
			rollTimer -= Gdx.graphics.getDeltaTime();
			if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
				rollTimer -= ROLL_TIMER_SWITCH_TIME;
				roll--;
			}
		} else {
			if (roll < 2) {
				//Update roll to make it go back to center
				rollTimer += Gdx.graphics.getDeltaTime();
				if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
					rollTimer -= ROLL_TIMER_SWITCH_TIME;
					roll++;
				}
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {//Right
			x += SPEED * Gdx.graphics.getDeltaTime();
			
			if (x + SHIP_WIDTH > Gdx.graphics.getWidth())
				x = Gdx.graphics.getWidth() - SHIP_WIDTH;
			
			//Update roll if button just clicked
			if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT) && roll > 0) {
				rollTimer = 0;
				roll--;
			}
			
			//Update roll
			rollTimer += Gdx.graphics.getDeltaTime();
			if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
				rollTimer -= ROLL_TIMER_SWITCH_TIME;
				roll++;
			}
		} else {
			if (roll > 2) {
				//Update roll
				rollTimer -= Gdx.graphics.getDeltaTime();
				if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
					rollTimer -= ROLL_TIMER_SWITCH_TIME;
					roll--;
				}
			}
		}
		
		//After all updates, check for collisions
		for (Bullet bullet : bullets) {
			for (Asteroid asteroid : asteroids) {
				if (bullet.getCollisionRect().collidesWith(asteroid.getCollisionRect())) {//Collision occured
					bulletsToRemove.add(bullet);
					asteroidsToRemove.add(asteroid);
					score += 100;
				}
			}
		}
		asteroids.removeAll(asteroidsToRemove);
		bullets.removeAll(bulletsToRemove);
		
		stateTime += delta;

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
		scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - scoreLayout.height - 10);
		
		for (Bullet bullet : bullets) {
			bullet.render(game.batch);
		}
		
		for (Asteroid asteroid : asteroids) {
			asteroid.render(game.batch);
		}
		
		game.batch.draw(rolls[roll].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);
		
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