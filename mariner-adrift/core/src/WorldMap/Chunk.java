package WorldMap;

import com.badlogic.gdx.math.Vector2;

public class Chunk {
	private Vector2 coords;
	private Tile[][] chunk;
	private Chunk north, east, south, west;
	public Chunk(int x, int z) {
		coords = new Vector2(x, z);
		chunk = new Tile[16][16];
	}
	public void setTile(int ID, int x, int z) {
		chunk[x][z] = new Tile(ID, x, z, this);
	}
	
	public Chunk north(){return north;}
	public Chunk east() {return east;}
	public Chunk south(){return south;}
	public Chunk west(){return west;}
	public void setNorth(Chunk c) {north = c;}
	public void setEast(Chunk c) {east = c;}
	public void setSouth(Chunk c) {south = c;}
	public void setWest(Chunk c) {west = c;}
	
}
