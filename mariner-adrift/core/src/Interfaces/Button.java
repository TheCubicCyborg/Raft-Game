package Interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button {

	Rectangle hitbox;
	Sprite sprite;
	SpriteBatch batch;
	String text;
	BitmapFont font;
	
	public Button(int x, int y, int width, int height, FileHandle background, String text, SpriteBatch batch)
	{
		hitbox = new Rectangle(x,y,width,height);
		this.batch = batch;
		
		font = new BitmapFont();
		this.text = text;
		sprite = new Sprite(new Texture(background));
		sprite.setPosition(x, y);
	}
	
	public boolean isJustPressed()
	{
		if(hitbox.contains(Gdx.input.getX(), Gdx.input.getY()) && Gdx.input.isButtonJustPressed(Buttons.LEFT))
		{
			return true;
		}
		else
			return false;
	}
	
	public void render()
	{
		font.draw(batch, text, hitbox.x, hitbox.y + hitbox.height/2);
	}
}
