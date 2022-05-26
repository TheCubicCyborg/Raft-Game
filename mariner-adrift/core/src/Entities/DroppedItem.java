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
	private boolean playerDropped;
	private float timer;
	
	public DroppedItem(Chunk c, float x, float y, int w, int h, SpriteBatch b, Item i, boolean pd)
	{
		super(c, x, y, w, h, b);
		item = i;
		playerDropped = pd;
		sprite = new Sprite(ItemPropertiesManager.textures[i.getTextureY()][i.getTextureX()], (int)x, (int)y, w, h);

	}
	
	public void update(float delta)
	{
		if(!playerDropped)
		{
			timer += delta;
			if(timer > 120)
				dispose();
			
			pos.add(flow);
		}
			
		
		super.update(delta);
		render(delta);
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
