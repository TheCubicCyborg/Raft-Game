package Entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import WorldMap.Chunk;

public class Entity {
	
	protected Chunk currentChunk;
	protected Vector2 pos;
	protected SpriteBatch batch;
	protected Rectangle hitBox;
	
	public Entity(Chunk c, float x, float y, float w, float h, SpriteBatch b)
	{
		currentChunk = c;
		c.addEntity(this);
		
		pos = new Vector2(x,y);
		hitBox = new Rectangle(x,y,w,h);
		batch = b;
	}
	
	public ArrayList<Entity> overlaps()
	{
		ArrayList<Entity> temp = new ArrayList<Entity>();
		for(Entity e : currentChunk.getEntities())
		{
			if(this.hitBox.overlaps(e.hitBox))
			{
				temp.add(e);
			}
		}
		if(temp.size() == 0)
			return null;
		return temp;
	}
	
	public void update(float delta)
	{
		
	}
	
	public void dispose() 
	{
		
	}
}
