package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends MarinerScreen{

	SpriteBatch batch;
	Texture img;
	
	public GameScreen(Game game)
	{
		super(game);
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
		batch.begin();
		batch.draw(img, 0, 0, 540, 540);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	
}
