package com.csds.marineradrift;

public class Tile {
	protected int ID, x, z;
	protected Chunk chunk;
	public Tile (int ID, int x, int z, Chunk chunk) {
		this.ID = ID;
		this.x = x;
		this.z = z;
		this.chunk = chunk;
		
	}
	public void destroy() {
		chunk.setTile(0, x, z);
		
	}
	
	
}
