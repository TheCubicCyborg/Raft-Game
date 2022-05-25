package Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import WorldMap.Chunk;


public class CraftingStation extends Entity{
	
	private RaftTile tile;
	private Vector2 mousePos;
	private Rectangle clickable;

	public CraftingStation(Chunk c, RaftTile t, SpriteBatch b)
	{
		
		super(c,t.pos.x,t.pos.y,3*Chunk.tileSize,3*Chunk.tileSize,b);
		clickable = new Rectangle(t.pos.x,t.pos.y,Chunk.tileSize,Chunk.tileSize);
		hitBox.setPosition(pos.x - Chunk.tileSize, pos.y - Chunk.tileSize);
		mousePos = new Vector2();
		super.pos = t.pos;
		this.tile = t;
		
	}
	
	public void update()
	{
		pos = tile.pos;
		mousePos.x = Gameplay.Gameplay.camera.position.x + (Gdx.input.getX() - Gdx.graphics.getWidth()/2) * (Gameplay.Gameplay.camera.zoom);
		mousePos.y = Gameplay.Gameplay.camera.position.y + (-Gdx.input.getY() + Gdx.graphics.getHeight()/2) * (Gameplay.Gameplay.camera.zoom);
		
		hitBox.setPosition(pos.x - Chunk.tileSize, pos.y - Chunk.tileSize);
		
		clickable.setPosition(pos);
		
		if(this.overlaps().contains(Gameplay.Gameplay.player) && clickable.contains(mousePos.x,mousePos.y)&&
				Gdx.input.isButtonJustPressed(Buttons.RIGHT))
		{
			Gameplay.Gameplay.setCraftingOpen(true);
		}
		
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE))
		{
			Gameplay.Gameplay.setCraftingOpen(false);
		}
			
	}
	
	
	
}
