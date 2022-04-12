package Entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import Inventory.Item;

public class DroppedItem implements Entity{

	Vector2 pos;
	Vector2 dim;
	Rectangle hitbox;
	Item item;
	
	public DroppedItem(float x, float y, int w, int h)
	{
		pos = new Vector2(x,y);
		dim = new Vector2(w,h);
		hitbox = new Rectangle(pos.x,pos.y,dim.x,dim.y);
	}
	
	public void update(float delta)
	{
		
		hitbox = new Rectangle(pos.x,pos.y,dim.x,dim.y);
		
		render(delta);
	}
	
	public void dispose()
	{
		
	}
	
	public void render(float delta)
	{
		
	}
	
	public Item getItem()
	{
		return item;
	}
	
}
