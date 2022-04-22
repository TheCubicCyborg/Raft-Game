package WorldMap;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import Entities.Entity;
import Gameplay.Player;

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
	private SpriteBatch batch;
	private Sprite back;
	
	public Chunk(int x, int z, Chunk n, Chunk e, Chunk s, Chunk w, SpriteBatch b) {
		
		coords = new Vector2(x, z);
		chunk = new Tile[chunkSize][chunkSize];
		
		entities = new ArrayList<Entity>();
		
		north = n;
		east = e;
		south = s;
		west = w;
		
		batch = b;
		back = new Sprite(new Texture("Chunk.png"));
	}
	
	public void render(float delta)
	{
		batch.draw(back,coords.x * totalSize+1, coords.y * totalSize+1, 254,254);
		renderEntities(delta);
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
			if(!(e instanceof Player))
				e.update(delta);
		}
	}
	
	public ArrayList<Entity> getEntities()
	{
		return entities;
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
	
	public void dispose()
	{
		back.getTexture().dispose();
	}

}