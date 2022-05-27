package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MainMenu extends MarinerScreen{

	SpriteBatch batch;
	Texture img;
	
	
	public MainMenu(Game game)
	{
		super(game);
	}
	
	@Override
	public void show()
	{
		batch = new SpriteBatch();
		img = new Texture("TitleScreen.png");
	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(49f, 120f, 193f, 1f);
		batch.begin();
		batch.draw(img, 0, -50, 1920, 1080 );
		batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			game.setScreen(new GameScreen(game));
		}
		
		if(Gdx.input.isKeyPressed(Keys.BACKSPACE))
		{
			Gdx.app.exit();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
