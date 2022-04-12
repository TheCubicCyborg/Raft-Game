package WorldMap;

import com.badlogic.gdx.math.Vector2;

public class World {
	
	public static final int NORTH = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 3;
	public static final int WEST = 4;
	
	private int renderDistance = 2;
	
	
	//chunk the player is in, will be updated in player movement
	private Chunk focused;
	
	public World()
	{
		focused = new Chunk(0,0, null, null, null, null);
	}
	
	public void update(float delta)
	{
		
	}
	
	public void setFocused(Chunk c)
	{
		focused = c;
	}
	
	public Chunk getFocused()
	{
		return focused;
	}
	
	
	public void initiateWorld()
	{
		
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
				temp.setNorth(new Chunk((int)temp.getCoords().x, (int)temp.getCoords().y + 1, null, null, temp, null));
			temp = temp.north();
			for(int i = 0; i < 2*renderDistance; i++)
			{
				if(temp.west() == null)
				{
					temp.setWest(new Chunk((int)temp.getCoords().x -1, (int)temp.getCoords().y, null,temp,temp.south().west(),null));
				}
				else
				{
					temp.west().setEast(temp);
				}
				temp = temp.west();
			}
			
		}
		else if(dir == EAST)
		{
			for(int i = 0; i < renderDistance; i++)
			{
				if(i<renderDistance -1)
					temp = temp.east();
				temp = temp.south();
			}
			if(temp.east() == null)
				temp.setEast(new Chunk((int)temp.getCoords().x +1, (int)temp.getCoords().y , null, null, null, temp));
			temp = temp.east();
			for(int i = 0; i < 2*renderDistance; i++)
			{
				if(temp.north() == null)
				{
					temp.setNorth(new Chunk((int)temp.getCoords().x, (int)temp.getCoords().y +1, null,null,temp,temp.west().north()));
				}
				else
				{
					temp.north().setSouth(temp);
				}
				temp = temp.north();
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
				temp.setSouth(new Chunk((int)temp.getCoords().x, (int)temp.getCoords().y - 1, temp, null, null, null));
			temp = temp.south();
			for(int i = 0; i < 2*renderDistance; i++)
			{
				if(temp.east() == null)
				{
					temp.setEast(new Chunk((int)temp.getCoords().x +1, (int)temp.getCoords().y, temp.north().east(),null,null,temp));
				}
				else
				{
					temp.east().setWest(temp);
				}
				temp = temp.east();
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
				temp.setWest(new Chunk((int)temp.getCoords().x -1, (int)temp.getCoords().y, null, temp, null, null));
			temp = temp.west();
			for(int i = 0; i < 2*renderDistance; i++)
			{
				if(temp.south() == null)
				{
					temp.setSouth(new Chunk((int)temp.getCoords().x, (int)temp.getCoords().y -1, temp,temp.east().south(),null,null));
				}
				else
				{
					temp.south().setNorth(temp);
				}
				temp = temp.south();
			}
		}
		
	}
	

	

}
