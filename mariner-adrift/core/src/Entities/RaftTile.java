package Entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import WorldMap.Chunk;
import WorldMap.TilePropertiesManager;

public class RaftTile {

	private SpriteBatch batch;
	private Sprite sprite;
	Vector2 pos, raftPos;
	private Raft raft;
	private CraftingStation station;
	
	private RaftTile north, east, south, west;
	
	public RaftTile(Raft r, int b, int f, int x, int y, SpriteBatch batch, RaftTile n, RaftTile e, RaftTile s, RaftTile w)
	{
		raft = r;
		this.batch = batch;
		raftPos = new Vector2(x,y);
		pos = new Vector2(raft.pos.x + x, raft.pos.y + y);
		
		north = n;
		east = e;
		south = s;
		west = w;
		station = null;
		
		if(b != 0)
		{
			TextureRegion tempReg = TilePropertiesManager.backTexts[b/12][b%12];
			Texture tempText = tempReg.getTexture();
			if(!tempText.getTextureData().isPrepared())
				tempText.getTextureData().prepare();
			Pixmap tempPix = tempText.getTextureData().consumePixmap();
			Pixmap temp = new Pixmap(tempReg.getRegionWidth(), tempReg.getRegionHeight(), Format.RGBA8888);
			for (int X = 0; X < tempReg.getRegionWidth(); X++) {
			    for (int Y = 0; Y < tempReg.getRegionHeight(); Y++) {
			        int colorInt = tempPix.getPixel(tempReg.getRegionX() + X, tempReg.getRegionY() + Y);
			        temp.drawPixel(X, Y, colorInt);
			    }
			}
			tempReg = TilePropertiesManager.foreTexts[f/12][f%12];
			tempText = tempReg.getTexture();
			if(!tempText.getTextureData().isPrepared())
				tempText.getTextureData().prepare();
			tempPix = tempText.getTextureData().consumePixmap();
			for (int X = 0; X < tempReg.getRegionWidth(); X++) {
			    for (int Y = 0; Y < tempReg.getRegionHeight(); Y++) {
			        int colorInt = tempPix.getPixel(tempReg.getRegionX() + X, tempReg.getRegionY() + Y);
			        temp.drawPixel(X,Y,colorInt);
			    }
			}
			if(f == 1)
				station = new CraftingStation(raft.getCurrentChunk(),this,batch);
				
			sprite = new Sprite(new Texture(temp));
		}
	}
	
	public void render()
	{
		pos.x = raft.pos.x + raftPos.x*Chunk.tileSize; 
		pos.y = raft.pos.y + raftPos.y*Chunk.tileSize;
		sprite.setPosition(pos.x,pos.y);
		sprite.draw(batch);
		if(station != null)
			station.update();
	}
	
	
	public RaftTile north() {return north;}
	public RaftTile east() {return east;}
	public RaftTile south() {return south;}
	public RaftTile west() {return west;}
	public void setNorth(RaftTile c) {north = c;}
	public void setEast(RaftTile c) {east = c;}
	public void setSouth(RaftTile c) {south = c;}
	public void setWest(RaftTile c) {west = c;}
}
