package Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu {
	SpriteBatch batch;
	Sprite img;
	
	public PauseMenu(SpriteBatch batch)
	{
		this.batch = batch;
		img = new Sprite(new Texture("paused.png"));
		img.setSize(600, 282);
		img.setPosition(Gameplay.camera.position.x - img.getWidth()/2 , Gameplay.camera.position.y - img.getHeight()/2);
		
		
	}
	
	public void render(float delta)
	{
		img.setPosition(Gameplay.camera.position.x - img.getWidth()/2 , Gameplay.camera.position.y - img.getHeight()/2);
		img.draw(batch);
	}
}
