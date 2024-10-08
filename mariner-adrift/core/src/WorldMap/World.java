package WorldMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.csds.marineradrift.Entry;
import com.csds.marineradrift.Map;

import Entities.DroppedItem;
import Inventory.Item;

public class World {
	
	public static final int NORTH = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 3;
	public static final int WEST = 4;
	
	public static int renderDistance = 2;
	
	private Map<Vector2, Chunk> rendered;
	
	//chunk the player is in, will be updated in player movement
	private Chunk focused;
	
	private SpriteBatch batch;
	
	public World(SpriteBatch batch)
	{
		rendered = new Map<Vector2, Chunk>();
		this.batch = batch;
		initiateWorld();
	}
	
	public void update(float delta)
	{
		for(Entry<Vector2, Chunk> e : rendered)
		{
			if(e.getElement() != null)
			{
				e.getElement().render(delta);
			}
		}
			
	}
	
	public void setFocused(Chunk c)
	{
		focused = c;
	}
	
	public Chunk getFocused()
	{
		return focused;
	}
	
	public Map<Vector2, Chunk> getRendered()
	{
		return rendered;
	}
	
	private void initiateWorld()
	{
		Chunk temp = new Chunk(0,0,null,null,null,null, batch);
		rendered.add(new Vector2(0,0),temp);
		focused = temp;
		Chunk inner;
		for(int i = 0; i < renderDistance; i++)
		{
			inner = temp;
			temp.setEast(new Chunk((int)temp.getCoords().x +1, (int)temp.getCoords().y,null,null,null,temp, batch));
			temp = temp.east();
			rendered.add(new Vector2(i+1,0), temp);
			for(int j = 0; j < i + 1; j++)
			{
				temp.setNorth(new Chunk((int)temp.getCoords().x, (int)temp.getCoords().y+1, null,null,temp,null, batch));
				temp = temp.north();
				rendered.add(new Vector2(temp.getCoords()), temp);
				if(j != i)
				{
					inner = inner.north();
					temp.setWest(inner);
					inner.setEast(temp);
				}
			}
			for(int j = 0; j < (i+1)*2; j++)
			{
				temp.setWest(new Chunk((int)temp.getCoords().x -1, (int)temp.getCoords().y, null, temp, null, null, batch));
				temp = temp.west();
				rendered.add(new Vector2(temp.getCoords()), temp);
				if(j < (i+1)*2 -1)
				{
					temp.setSouth(inner);
					inner.setNorth(temp);
					if(j<(i+1)*2 -2)
					{
						inner = inner.west();
					}
				}
			}
			for(int j = 0; j< (i+1)*2;j++)
			{
				temp.setSouth(new Chunk((int)temp.getCoords().x, (int)temp.getCoords().y-1, temp,null,null,null, batch));
				temp = temp.south();
				rendered.add(new Vector2(temp.getCoords()), temp);
				if(j<(i+1)*2 -1)
				{
					temp.setEast(inner);
					inner.setWest(temp);
					if(j<(i+1)*2-2)
						inner = inner.south();
				}
			}
			for(int j = 0; j<(i+1)*2; j++)
			{
				temp.setEast(new Chunk((int)temp.getCoords().x+1, (int)temp.getCoords().y,null,null,null,temp, batch));
				temp = temp.east();
				rendered.add(new Vector2(temp.getCoords()), temp);
				if(j<(i+1)*2-1)
				{
					temp.setNorth(inner);
					inner.setSouth(temp);
					if(j<(i+1)*2-2)
						inner=inner.east();
				}
			}
			for(int j = 0; j < i; j++)
			{
				temp.setNorth(new Chunk((int)temp.getCoords().x, (int)temp.getCoords().y+1, null,null,temp,null, batch));
				temp = temp.north();
				rendered.add(new Vector2(temp.getCoords()), temp);
					temp.setWest(inner);
					inner.setEast(temp);
					inner = inner.north();
			}
			temp.setNorth(inner.east());
			temp.north().setSouth(temp);
			temp = temp.north();
		}
	}
	
	public void refreshRendered(int dir)
	{
		
		Chunk temp = focused;
		if(dir == NORTH)
		{
			for(int i = 0; i < renderDistance; i++)
			{
				if(i<renderDistance -1)
					temp = temp.north();
				temp = temp.east();
			}
			if(temp.north() == null)
				temp.setNorth(new Chunk((int)temp.getCoords().x, (int)temp.getCoords().y + 1, null, null, temp, null, batch));
			temp = temp.north();
			for(int i = 0; i < 2*renderDistance; i++)
			{
				if(temp.west() == null)
				{
					temp.setWest(new Chunk((int)temp.getCoords().x -1, (int)temp.getCoords().y, null,temp,temp.south().west(),null, batch));
				}
				else
				{
					temp.west().setEast(temp);
				}
				temp = temp.west();
				temp.south().setNorth(temp);
			}
			
		}
		else if(dir == EAST)
		{
			for(int i = 0; i < renderDistance; i++)
			{
				if(i<renderDistance -1) {
					temp = temp.east();}
				temp = temp.south();
			}
			if(temp.east() == null)
				temp.setEast(new Chunk((int)temp.getCoords().x +1, (int)temp.getCoords().y , null, null, null, temp, batch));
			temp = temp.east();
			for(int i = 0; i < 2*renderDistance; i++)
			{
				if(temp.north() == null)
				{
					temp.setNorth(new Chunk((int)temp.getCoords().x, (int)temp.getCoords().y +1, null,null,temp,temp.west().north(), batch));
				}
				else
				{
					temp.north().setSouth(temp);
				}
				temp = temp.north();
				temp.west().setEast(temp);
				
			}
		}
		else if(dir == SOUTH)
		{
			for(int i = 0; i < renderDistance; i++)
			{
				if(i<renderDistance -1)
					temp = temp.south();
				temp = temp.west();
			}
			if(temp.south() == null)
				temp.setSouth(new Chunk((int)temp.getCoords().x, (int)temp.getCoords().y - 1, temp, null, null, null, batch));
			temp = temp.south();
			for(int i = 0; i < 2*renderDistance; i++)
			{
				if(temp.east() == null)
				{
					temp.setEast(new Chunk((int)temp.getCoords().x +1, (int)temp.getCoords().y, temp.north().east(),null,null,temp, batch));
				}
				else
				{
					temp.east().setWest(temp);
				}
				temp = temp.east();
				temp.north().setSouth(temp);
			}

		}
		else if(dir == WEST)
		{
			for(int i = 0; i < renderDistance; i++)
			{
				if(i<renderDistance -1)
					temp = temp.west();
				temp = temp.north();
			}
			if(temp.west() == null)
				temp.setWest(new Chunk((int)temp.getCoords().x -1, (int)temp.getCoords().y, null, temp, null, null, batch));
			temp = temp.west();
			for(int i = 0; i < 2*renderDistance; i++)
			{
				if(temp.south() == null)
				{
					temp.setSouth(new Chunk((int)temp.getCoords().x, (int)temp.getCoords().y -1, temp,temp.east().south(),null,null, batch));
				}
				else
				{
					temp.south().setNorth(temp);
				}
				temp = temp.south();
				temp.east().setWest(temp);
			}
		}
	}
		
	public void updateRendered(int dir)
		{
			Map<Vector2, Chunk> temp = new Map<Vector2, Chunk>();
			switch(dir) {
			case NORTH:
				for(Entry<Vector2,Chunk> e : rendered)
				{
					temp.add(e.getID(), e.getElement().north());
				}
				rendered = temp;
				break;
			case EAST:
				for(Entry<Vector2,Chunk> e : rendered)
				{
					temp.add(e.getID(), e.getElement().east());
				}
				rendered = temp;
				break;
			case SOUTH:
				for(Entry<Vector2,Chunk> e : rendered)
				{
					
					temp.add(e.getID(), e.getElement().south());
				}
				rendered = temp;
				break;
			case WEST:
				
				for(Entry<Vector2,Chunk> e : rendered)
				{
					temp.add(e.getID(), e.getElement().west());
				}
				rendered = temp;
				break;
			}
		}

}
