package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import Gameplay.Gameplay;


public class GameScreen extends MarinerScreen{

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
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
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
