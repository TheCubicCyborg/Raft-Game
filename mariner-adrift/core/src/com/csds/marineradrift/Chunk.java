package com.csds.marineradrift;

import com.badlogic.gdx.math.Vector2;

public class Chunk {
	private Vector2 coords;
	private Tile[][] chunk;
	public Chunk(int x, int z) {
		coords = new Vector2(x, z);
		chunk = new Tile[16][16];
	}
	public void setTile(int ID, int x, int z) {
		chunk[x][z] = new Tile(ID, x, z, this);
	}
	
}
