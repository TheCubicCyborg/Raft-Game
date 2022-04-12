package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import Gameplay.Gameplay;


public class GameScreen extends MarinerScreen{

	public static float scalar = 6;
	SpriteBatch batch;
	Texture img;
	Gameplay gp;
	
	
	public GameScreen(Game game)
	{
		super(game);
		gp = new Gameplay();
	}
	
	@Override
	public void show()
	{

	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(49f, 120f, 193f, 0.5f);
		gp.update(delta);
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		gp.dispose();
	}
	
}
