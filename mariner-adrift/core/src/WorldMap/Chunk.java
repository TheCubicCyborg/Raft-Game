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
	public  static final int tileSize = 20;
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
		back = new Sprite(new Texture("ChunkBack.png"));
		initiateTiles();
	}
	
	public void initiateTiles()
	{
		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				setTile(0, 0, i, j);
			}
		}
	}
	
	public void render(float delta)
	{
		batch.draw(back,coords.x * totalSize+1, coords.y * totalSize+1, totalSize, totalSize);
		renderEntities(delta);
		renderTiles(delta);
	}
	
	public void setTile(int backID,int foreID, int x, int y) 
	{
		chunk[x][y] = new Tile(backID, foreID, (int)(coords.x * chunkSize + x), (int)(coords.y * chunkSize + y), this, batch);
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
	
	public void renderTiles(float delta)
	{
		for(int i = 0; i < chunk.length; i++)
		{
			for(int j = 0; j < chunk[i].length; j++)
			{
				if(chunk[i][j] != null)
					chunk[i][j].render();
			}
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