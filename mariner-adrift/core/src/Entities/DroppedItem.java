package Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import Inventory.Item;
import Inventory.ItemPropertiesManager;
import WorldMap.Chunk;

public class DroppedItem extends Entity{

	Item item;
	Sprite sprite;
	
	public DroppedItem(Chunk c, float x, float y, int w, int h, SpriteBatch b, Item i)
	{
		super(c, x, y, w, h, b);
		item = i;
		
		sprite = new Sprite(ItemPropertiesManager.textures[i.getTextureY()][i.getTextureX()], (int)x, (int)y, w, h);

	}
	
	public void update(float delta)
	{
		
		render(delta);
	}
	
	public void dispose()
	{
		currentChunk.removeEntity(this);
	}
	
	public void render(float delta)
	{
		sprite.draw(batch);
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public String toString()
	{
		return "DroppedItem (" + item.getId() + ")";
	}
}
