package Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import Gameplay.Gameplay;

import com.badlogic.gdx.Input.Buttons;

import Screens.GameScreen;

public class Inventory {
	
	private Item[][] inventory;
	private Vector2 screenPos;
	private Vector2 mousePos;
	private SpriteBatch batch;
	private Sprite inv;

	private Item selected;
	private Texture select;
	
	private float timeClicked;
	private boolean didTake;
	
	BitmapFont font;
	
	public Inventory(SpriteBatch batch)
	{
		//first 3 rows are main inventory, row 4 is hotbar, row 5 is miscelaneous slots ie. Armor, equippables
		inventory = new Item[5][9];
		this.batch = batch;
		inv = new Sprite(new Texture("inventory-temp2.png"));
		screenPos = new Vector2(Gameplay.camera.position.x - inv.getWidth()/2 , Gameplay.camera.position.y - inv.getHeight()/2);
		inv.setPosition(screenPos.x, screenPos.y);
		inv.scale(GameScreen.scalar);
		timeClicked = 0;
		didTake = false;
		mousePos = new Vector2();
		
		Pixmap pm = new Pixmap((int)(20*GameScreen.scalar), (int)(20*GameScreen.scalar), Format.RGBA8888 );
		pm.setColor(0,0,0,.1f);
		pm.fill();
		select = new Texture(pm);
		pm.dispose();
		
		font = new BitmapFont();
	}
	
	public void Render(float delta)
	{
		screenPos.x = Gameplay.camera.position.x - inv.getWidth()/2;
		screenPos.y = Gameplay.camera.position.y - inv.getHeight()/2;
		
		mousePos.x = (int)Gameplay.camera.position.x + (-(Gdx.graphics.getWidth()/2 ) + Gdx.input.getX()) * Gameplay.camera.zoom;
		mousePos.y = (int)Gameplay.camera.position.y + ((Gdx.graphics.getHeight()/2 ) - Gdx.input.getY()) * Gameplay.camera.zoom;
		
		
		
		inv.setPosition(screenPos.x, screenPos.y);
		inv.draw(batch);
		renderItems();
		if(!Gameplay.pauseOpen)
		{
		processCursor(delta);
		renderSelected();
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.P))
		{
			inventory[2][4] = new Item(1,15);
			inventory[2][5] = new Item(0,20);
		}
		
		CharSequence str = mousePos.x + "," + mousePos.y;

		font.draw(batch, str, Gameplay.camera.position.x, Gameplay.camera.position.y);
	}
	
	public void dispose()
	{
		inv.getTexture().dispose();
		select.dispose();
	}
	
	public boolean add(Item item)
	{
		Vector2 emptySlot = null;
		int row = 0;
		int col = 0;
		for(Item[] i : inventory)
		{
			for(Item j : i)
			{
				if(emptySlot == null && j == null)
				{
					emptySlot = new Vector2(row, col);
				}
				else if(j != null)
				{
					if(j.getId() == item.getId() && j.getAmt() + item.getAmt() <= ItemPropertiesManager.getItemProps(j.getId()).getMaxSize())
					{
						j.setAmt(j.getAmt()+item.getAmt());
						return true;
					}
				}
				
				
				col++;
			}
			row++;
		}
		if(emptySlot != null)
		{
			inventory[(int)emptySlot.x][(int)emptySlot.y] = item;
			return true;
		}
		
		return false;
	}
	
	private void processCursor(float delta)
	{
		
		
		Vector2 tempCoords;
		Rectangle tempRect = new Rectangle();
		for(int r = 0; r < 4; r++)
		{
			for(int c = 0; c < 9; c++)
			{
				tempCoords = getCoords(r+1,c+1);
				
				tempRect.set(tempCoords.x, tempCoords.y, 20*GameScreen.scalar , 20*GameScreen.scalar);
				if(tempRect.contains(mousePos.x,mousePos.y))
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
	}
	
	private void renderSelected()
	{
		if(selected != null)
		{
			Sprite temp = new Sprite(ItemPropertiesManager.textures[selected.getTextureY()][selected.getTextureX()]);
			temp.setOrigin(temp.getWidth() * (1f/Gameplay.camera.zoom) / GameScreen.scalar,temp.getHeight() * (1f/Gameplay.camera.zoom) / GameScreen.scalar);
			temp.scale(1f + Gameplay.camera.zoom);
			temp.setPosition((Gameplay.camera.position.x - Gdx.graphics.getWidth()/2 + Gdx.input.getX())*(float)(Gameplay.camera.zoom), (Gameplay.camera.position.y + Gdx.graphics.getHeight()/2 - Gdx.input.getY())*(float)(Gameplay.camera.zoom));
			temp.draw(batch);
			int num = selected.getAmt();
			if(num > 1)
			{
				float x = temp.getX() + 6 * GameScreen.scalar;
				float y = temp.getY() - 6 * GameScreen.scalar;
				while(num > 0)
				{
					int front = num % 10;
					temp = new Sprite(ItemPropertiesManager.digits[0][front]);
					temp.scale(1f + Gameplay.camera.zoom);
					temp.setPosition(x, y);
					temp.draw(batch);
					num /= 10;
					x -= 4 * GameScreen.scalar;
					
				}
			}
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
					TextureRegion tempText = ItemPropertiesManager.textures[item.getTextureY()][item.getTextureX()];
					batch.draw(tempText, temp.x, temp.y, tempText.getRegionWidth() * GameScreen.scalar, tempText.getRegionHeight() * GameScreen.scalar);
					
					int num = item.getAmt();
					if(num > 1)
					{
						int x = (int)temp.x + (int)(18 * GameScreen.scalar);
						while(num > 0)
						{
							int front = num % 10;
							x -= 4 * GameScreen.scalar;
							batch.draw(ItemPropertiesManager.digits[0][front], x, temp.y + GameScreen.scalar, ItemPropertiesManager.digits[0][front].getRegionWidth() * GameScreen.scalar, ItemPropertiesManager.digits[0][front].getRegionHeight() * GameScreen.scalar);
							num = num/10;
						}
					}
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
