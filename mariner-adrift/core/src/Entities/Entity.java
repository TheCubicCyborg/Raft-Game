package Entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import WorldMap.Chunk;
import WorldMap.World;

public class Entity {
	
	protected static Vector2 flow = new Vector2(0.5f,0.5f);
	
	protected Chunk currentChunk;
	protected Vector2 pos;
	protected SpriteBatch batch;
	protected Rectangle hitBox;
	
	private int pauseDirection; 
	
	public Entity(Chunk c, float x, float y, float w, float h, SpriteBatch b)
	{
		currentChunk = c;
		c.addEntity(this);
		
		pos = new Vector2(x,y);
		hitBox = new Rectangle(x,y,w,h);
		batch = b;
		//in case an entity is trying to move into an unloaded chunk, it will just pause where it last was
		pauseDirection = 4;
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
		if(pauseDirection == 4)
		{
			hitBox.setPosition(pos);
			chunkMovementCheck();
		}
		else
		{
			switch(pauseDirection)
			{
			case 0:
				if(currentChunk.north() != null)
					pauseDirection = 4;
				break;
			case 1: 
				if(currentChunk.east() != null)
					pauseDirection = 4;
				break;
			case 2:
				if(currentChunk.south() != null)
					pauseDirection = 4;
				break;
			case 3:
				if(currentChunk.west() != null)
					pauseDirection = 4;
				break;
			}
		}
		
	}
	
	public void dispose() 
	{
		currentChunk.removeEntity(this);
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
			
			if(currentChunk.east() != null)
			{
				currentChunk.removeEntity(this);
				currentChunk.east().addEntity(this);
				currentChunk = currentChunk.east();
			}
			else
			{
				pauseDirection = 1;
			}
		}
		else if(pos.x < (currentChunk.getCoords().x) * Chunk.totalSize)
		{
			if(currentChunk.west() != null)
			{
				currentChunk.removeEntity(this);
				currentChunk.west().addEntity(this);
				currentChunk = currentChunk.west();
			}
			else
				pauseDirection = 3;
		}
		
		if(pos.y >= (currentChunk.getCoords().y +1) * Chunk.totalSize)
		{
			if(currentChunk.north() != null)
			{
				currentChunk.removeEntity(this);
				currentChunk.north().addEntity(this);
				currentChunk = currentChunk.north();
			}
			else
				pauseDirection = 0;
		}
		else if(pos.y < (currentChunk.getCoords().y) * Chunk.totalSize)
		{
			if(currentChunk.south() != null)
			{
				currentChunk.removeEntity(this);
				currentChunk.south().addEntity(this);
				currentChunk = currentChunk.south();
			}
			else
				pauseDirection = 2;
		}
		

		
	}
}
