package Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
	SpriteBatch batch;
	Texture img;
	
	public PauseMenu()
	{
		batch = new SpriteBatch();
		img = new Texture("paused.png");
	}
	
	public void render(float delta)
	{
		batch.begin();
		batch.draw(img, Gdx.graphics.getWidth()/2 - 600, Gdx.graphics.getHeight()/2 - 282, 1200, 564);
		batch.end();
	}
}
