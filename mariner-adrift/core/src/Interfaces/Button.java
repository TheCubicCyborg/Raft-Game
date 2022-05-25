package Interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import Gameplay.Gameplay;

public class Button {

	Rectangle hitbox;
	Sprite sprite;
	SpriteBatch batch;
	Sprite text;
	Vector2 screenPos, mousePos;
	int x,y;
	Texture shaded, clicked;
	
	public Button(int x, int y, int width, int height, String text, SpriteBatch batch)
	{
		screenPos = new Vector2(x,y);
		mousePos = new Vector2();
		this.x = x;
		this.y = y;
		
		hitbox = new Rectangle(x*2,y*2,width*2,height*2);
		this.batch = batch;
		
		this.text = new Sprite(new Texture(new Text(text).getPixmap()));
		this.text.setPosition(x + 50, y + 50);
		Pixmap temp = new Pixmap(width,height,Format.RGBA8888);
		temp.setColor(125/255f,125/255f,125/255f,1f);
		temp.fill();
		temp.setColor(67/255f,237/255f,240/255f, 1f);
		temp.fillRectangle(2, 2, width-4, height-4);
		sprite = new Sprite(new Texture(temp));
		sprite.setPosition(x, y);
		
		Pixmap tempPix = new Pixmap(width - 4, height-4, Format.RGBA8888);
		tempPix.setColor(0,0,0,.1f);
		tempPix.fill();
		shaded = new Texture(tempPix);
		tempPix.setColor(0,0,0,.3f);
		tempPix.fill();
		clicked = new Texture(tempPix);
		tempPix.dispose();
	}
	
	public boolean isJustPressed()
	{
		if(hitbox.contains(mousePos) && Gdx.input.isButtonJustPressed(Buttons.LEFT))
		{
			return true;
		}
		else
			return false;
	}
	
	public void render()
	{	
		x = (int) (Gameplay.camera.position.x + screenPos.x);
		y = (int)(Gameplay.camera.position.y + screenPos.y);
		
		mousePos.x = Gdx.input.getX()-Gdx.graphics.getWidth()/2;
		mousePos.y = -(Gdx.input.getY()-Gdx.graphics.getHeight()/2);
		
		batch.draw(sprite.getTexture(),x,y);
		batch.draw(text.getTexture(),x+50,y+hitbox.getHeight()/6);
		
		if(hitbox.contains(mousePos))
			batch.draw(shaded,x+2,y+2);
		
		if(isJustPressed())
		{
			batch.draw(clicked,x+2,y+2);
		}
	}
	
}
