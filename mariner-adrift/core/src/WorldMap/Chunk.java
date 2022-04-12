package WorldMap;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import Entities.Entity;

public class Chunk {
	//tiles in a chunk
	private static final int chunkSize = 16;
	//pixels/units in a tile
	private static final int tileSize = 16;
	public static final int totalSize = chunkSize*tileSize;
	
	
	private Vector2 coords;
	private Tile[][] chunk;
	private Chunk north, east, south, west;
	private ArrayList<Entity> entities;
	
	public Chunk(int x, int z, Chunk n, Chunk e, Chunk s, Chunk w) {
		
		coords = new Vector2(x, z);
		chunk = new Tile[chunkSize][chunkSize];
		
		entities = new ArrayList<Entity>();
		
		north = n;
		east = e;
		south = s;
		west = w;
		
	}
	
	
	public void setTile(int ID, int x, int z) 
	{
		chunk[x][z] = new Tile(ID, x, z, this);
	}
	
	public void addEntity(Entity e)
	{
		entities.add(e);
	}
	
	public void removeEntity(Entity e)
	{
		entities.remove(e);
	}
	
	public void renderEntities(float delta)
	{
		for(Entity e : entities)
		{
			e.update(delta);
		}
	}
	
	
	public Chunk north(){return north;}
	public Chunk east() {return east;}
	public Chunk south(){return south;}
	public Chunk west(){return west;}
	public void setNorth(Chunk c) {north = c;}
	public void setEast(Chunk c) {east = c;}
	public void setSouth(Chunk c) {south = c;}
	public void setWest(Chunk c) {west = c;}
	
	
	public static Vector2 chunkToWorldPos(Vector2 v)
	{
		return v;
	}
	
	public Vector2 getCoords()
	{
		return coords;
	}

}