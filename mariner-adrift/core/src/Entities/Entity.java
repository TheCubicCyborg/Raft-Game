package Entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import WorldMap.Chunk;
import WorldMap.World;

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
	
	public Rectangle hitBox()
	{
		return hitBox;
	}
	
	public Chunk getCurrentChunk()
	{
		return currentChunk;
	}
	
	public void chunkMovementCheck()
	{		
		
		if(pos.x >= (currentChunk.getCoords().x +1) * Chunk.totalSize)
		{
			currentChunk.removeEntity(this);
			currentChunk.east().addEntity(this);
			currentChunk = currentChunk.east();
		}
		else if(pos.x < (currentChunk.getCoords().x) * Chunk.totalSize)
		{
			currentChunk.removeEntity(this);
			currentChunk.west().addEntity(this);
			currentChunk = currentChunk.west();
		}
		
		if(pos.y >= (currentChunk.getCoords().y +1) * Chunk.totalSize)
		{
			currentChunk.removeEntity(this);
			currentChunk.north().addEntity(this);
			currentChunk = currentChunk.north();
		}
		else if(pos.y < (currentChunk.getCoords().y) * Chunk.totalSize)
		{
			currentChunk.removeEntity(this);
			currentChunk.south().addEntity(this);
			currentChunk = currentChunk.south();
		}
		

		
	}
}
