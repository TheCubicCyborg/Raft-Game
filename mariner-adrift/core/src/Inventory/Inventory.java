package Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.csds.marineradrift.Cursor;
import com.csds.marineradrift.MarinerAdrift;

import Screens.GameScreen;

public class Inventory {
	
	private Item[][] inventory;
	private Vector2 screenPos;
	private Vector2 dim;
	private SpriteBatch batch;
	private Texture img;
	TextureRegion[][] textures, digits;
	private Cursor cursor;
	
	
	public Inventory()
	{
		//first 3 rows are main inventory, row 4 is hotbar, row 5 is miscelaneous slots ie. Armor, equippables
		inventory = new Item[5][9];
		batch = new SpriteBatch();
		img = new Texture("inventory-temp2.png");
		textures = new TextureRegion(new Texture("ItemMap.png")).split(20, 20);
		digits = new TextureRegion(new Texture("digits.png")).split(5, 7);
		dim = new Vector2(img.getWidth() * GameScreen.scalar, img.getHeight() *GameScreen.scalar);
		screenPos = new Vector2(Gdx.graphics.getWidth()/2 - dim.x/2, Gdx.graphics.getHeight()/2 - dim.y/2);
		cursor = MarinerAdrift.cursor;
	}
	
	public void Render(float delta)
	{
		batch.begin();
		batch.draw(img, screenPos.x, screenPos.y, dim.x, dim.y);
		batch.end();
		renderItems();
		
	}
	
	public void dispose()
	{
		batch.dispose();
		img.dispose();
	}
	
	private void renderItems()
	{
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				Item item = inventory[i][j];
				if(item != null)
				{
					Vector2 temp = getCoords(i+1,j+1);
					TextureRegion tempText = textures[item.getTextureX()][item.getTextureY()];
					batch.begin();
					batch.draw(tempText, temp.x, temp.y, tempText.getRegionWidth() * GameScreen.scalar, tempText.getRegionHeight() * GameScreen.scalar);
					
					int num = item.getAmt();
					if(num > 1)
					{
						int x = (int)temp.x + (int)(18 * GameScreen.scalar);
						while(num > 0)
						{
							int front = num % 10;
							x -= 4 * GameScreen.scalar;
							batch.draw(digits[0][front], x, temp.y + GameScreen.scalar, digits[0][front].getRegionWidth() * GameScreen.scalar, digits[0][front].getRegionHeight() * GameScreen.scalar);
							num = num/10;
						}
					}
					batch.end();
					
				}
			}
		}
	}
	
	//given row and column of inventory, returns coordinates
	private Vector2 getCoords(int r, int c)
	{
		Vector2 ret = new Vector2();
		ret.x = 2 + (22*(c-1));
		ret.x *= GameScreen.scalar;
		if(r == 1)
			ret.y = 2;
		else
			ret.y = 6 + (22*(r-1));
		ret.y *= GameScreen.scalar;
		
		ret.x = screenPos.x + ret.x;
		ret.y = screenPos.y + ret.y;
		
		return ret;
	}
	
	public Item[][] getInventory()
	{
		return inventory;
	}
	
	
}
