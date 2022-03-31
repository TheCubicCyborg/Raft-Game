package Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Inventory {
	
	private Item[][] inventory;
	private SpriteBatch batch;
	private Texture img;
	
	
	public Inventory()
	{
		//first 3 rows are main inventory, row 4 is hotbar, row 5 is miscelaneous slots ie. Armor, equippables
		inventory = new Item[5][9];
		batch = new SpriteBatch();
		img = new Texture("inventory-temp.png");
		
	}
	
	public void Render(float delta)
	{
		batch.begin();
		batch.draw(img, Gdx.graphics.getWidth()/2 - 600, Gdx.graphics.getHeight()/2 - 282, 1200, 564);
		batch.end();
	}
	
	public void dispose()
	{
		batch.dispose();
		img.dispose();
		
	}
	
	
}
