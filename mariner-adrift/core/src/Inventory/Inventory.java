package Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Buttons;

import Screens.GameScreen;

public class Inventory {
	
	private Item[][] inventory;
	private Vector2 screenPos;
	private Vector2 dim;
	private SpriteBatch batch;
	private Texture img;
	TextureRegion[][] textures, digits;
	private Item selected;
	private Texture select;
	
	private float timeClicked;
	private boolean didTake;
	
	
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
		timeClicked = 0;
		didTake = false;
		
		Pixmap pm = new Pixmap((int)(20*GameScreen.scalar), (int)(20*GameScreen.scalar), Format.RGBA8888 );
		pm.setColor(0,0,0,.1f);
		pm.fill();
		select = new Texture(pm);
		pm.dispose();
	}
	
	public void Render(float delta)
	{
		
		
		batch.begin();
		batch.draw(img, screenPos.x, screenPos.y, dim.x, dim.y);
		batch.end();
		renderItems();
		processCursor(delta);
		renderSelected();
		
		if(Gdx.input.isKeyJustPressed(Keys.P))
		{
			inventory[2][4] = new Item(1,15);
			inventory[2][5] = new Item(0,20);
		}
	}
	
	public void dispose()
	{
		batch.dispose();
		img.dispose();
		select.dispose();
	}
	
	private void processCursor(float delta)
	{
		
		
		Vector2 tempCoords;
		Rectangle tempRect = new Rectangle();
		batch.begin();
		for(int r = 0; r < 4; r++)
		{
			for(int c = 0; c < 9; c++)
			{
				tempCoords = getCoords(r+1,c+1);
				
				tempRect.set(tempCoords.x, Gdx.graphics.getHeight() - (tempCoords.y + 20*GameScreen.scalar), 20*GameScreen.scalar, 20*GameScreen.scalar);
				if(tempRect.contains(Gdx.input.getX(), Gdx.input.getY()))
				{
					batch.draw(select, tempCoords.x, tempCoords.y, 20*GameScreen.scalar, 20*GameScreen.scalar);
					if(inventory[r][c] != null)
					{
						Item item = inventory[r][c];
						if(Gdx.input.isButtonJustPressed(Buttons.LEFT))
						{
							if(selected == null)
							{
							selected = item.pickUp();
							inventory[r][c] = null;
							}
							else
							{
								if(item.getId() == selected.getId())
								{
									selected.combineItems(item);
									selected = null;
								}
								else
								{
									Item tempItem = selected;
									selected = item;
									inventory[r][c] = tempItem;
								}
							}
							timeClicked = 0;
						}
						else if(Gdx.input.isButtonPressed(Buttons.RIGHT))
						{
							timeClicked += delta;
							if(selected == null)
							{
								selected = new Item(item.getId(), 0);
								item.putOne(selected);
								if(item.getAmt() == 0)
								{
									inventory[r][c] = null;
								}
								didTake = true;
							}
							else if(item.getId() == selected.getId())
							{
								if(didTake)
								{
									if(timeClicked >= .15f)
									{
										item.putOne(selected);
										if(item.getAmt() == 0)
										{
											inventory[r][c] = null;
										}
										didTake = true;
										timeClicked = 0;
									}
								}
								else
								{
									item.putOne(selected);
									if(item.getAmt() == 0)
									{
										inventory[r][c] = null;
									}
									didTake = true;
								}
							}
						}
						else
						{
							timeClicked = 0;
							didTake = false;
						}
					}
					else if(selected != null)
					{
						if(Gdx.input.isButtonJustPressed(Buttons.LEFT))
						{
						inventory[r][c] = selected;
						selected = null;
						}
					}
				}
			}
		}
		batch.end();
	}
	
	private void renderSelected()
	{
		if(selected != null)
		{
			batch.begin();
			TextureRegion temp = textures[selected.getTextureY()][selected.getTextureX()];
			batch.draw(temp, Gdx.input.getX() - (temp.getRegionWidth()/2 * GameScreen.scalar), Gdx.graphics.getHeight() - (Gdx.input.getY() + 20*GameScreen.scalar) + (temp.getRegionHeight()/2 * GameScreen.scalar),20 * GameScreen.scalar, 20 * GameScreen.scalar);
			int num = selected.getAmt();
			if(num > 1)
			{
				int x = Gdx.input.getX() + (int)(8  * GameScreen.scalar);
				int y = Gdx.graphics.getHeight() - (Gdx.input.getY() + (int)(9 * GameScreen.scalar));
				while(num > 0)
				{
					int front = num % 10;
					x -= 4 * GameScreen.scalar;
					batch.draw(digits[0][front], x, y, digits[0][front].getRegionWidth() * GameScreen.scalar, digits[0][front].getRegionHeight() * GameScreen.scalar);
					num = num/10;
				}
			}
			batch.end();
		}
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
					TextureRegion tempText = textures[item.getTextureY()][item.getTextureX()];
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
