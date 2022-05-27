package Entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import WorldMap.Chunk;

public class Fish extends Entity {
	
	private Texture shadow;
	Sprite sprite;
	
	private float timer;

	public Fish(Chunk c, float x, float y, float w, float h, SpriteBatch b)
	{
		super(c,x,y,w,h,b);
		
		Pixmap temp = new Pixmap(Chunk.tileSize,Chunk.tileSize, Format.RGBA8888);
		temp.setColor(0,0,0,.3f);
		temp.fillCircle(temp.getWidth()/2, temp.getHeight()/2, temp.getWidth()/2 - 1);
		shadow = new Texture (temp);
		
		sprite = new Sprite(shadow,(int)x,(int)y,(int)w,(int)h);
	}
	
	public void update(float delta)
	{
		sprite.setPosition(pos.x, pos.y);
		timer += delta;
		if(timer > 120)
			dispose();
		pos.add(flow);
		super.update(delta);
		render();
	}
	
	public void render()
	{
		sprite.draw(batch);
	}
}
