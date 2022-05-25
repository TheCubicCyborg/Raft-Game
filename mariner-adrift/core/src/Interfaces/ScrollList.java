package Interfaces;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Filter;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.csds.marineradrift.Entry;
import com.csds.marineradrift.Map;
import com.csds.marineradrift.MarinerInputProcessor;

import Gameplay.Gameplay;
import Inventory.Item;
import Inventory.ItemPropertiesManager;
import WorldMap.TilePropertiesManager;

public class ScrollList {

	
	private ArrayList<Item> items;
	private SpriteBatch batch;
	private int x,y,w,h,scrollY = 80;
	private Pixmap listMap,shadeMap;
	private Texture list, shade, background;
	private Rectangle hitBox;
	private ArrayList<Rectangle> hitBoxes;
	private Map<Rectangle, Item> itemBoxes;
	private Item selected;
	private float scale;
	
	Vector2 mousePos,screenPos;
	
	BitmapFont font;
	
	public ScrollList(ArrayList<Item> items, int x, int y, int width, int height, float scale, SpriteBatch batch)
	{
		
		this.scale = scale;
		selected = null;
		this.batch = batch;
		this.items = items;
		screenPos = new Vector2();
		screenPos.x = x;
		screenPos.y = y;
		this.x = x;
		this.y = y;
		w = width;
		h = height;
		hitBox = new Rectangle(x,y,width * scale,height*scale);
		hitBoxes = new ArrayList<Rectangle>();
		itemBoxes = new Map<Rectangle, Item>();
		scrollY= 0;
		
		Pixmap temp = new Pixmap(width, height, Format.RGBA8888);
		temp.setColor(125/255f,125/255f,125/255f,1f);
		temp.fill();
		temp.setColor(67/255f,237/255f,240/255f, 1f);
//		67, 237, 240, 255
		temp.fillRectangle(2, 2, width-4, height-4);
		background = new Texture(temp);
		
		temp = new Pixmap(width - 4, 20*(int)scale, Format.RGBA8888);
		temp.setColor(0,0,0,.1f);
		temp.fill();
		shadeMap = temp;
		shade = new Texture(temp);
		
		initiateList();
		
		mousePos = new Vector2();
		
		font = new BitmapFont();
	}
	
	public void initiateList()
	{
		Pixmap temp = new Pixmap(84, (20*items.size()) + (2 * (items.size()-1)), Format.RGBA8888);
		
		TextureRegion tempReg;
		Texture tempText;
		Pixmap tempPix;
		Rectangle tempHit;
		int n = 0;
		for(Item i : items)
		{
			tempReg = ItemPropertiesManager.textures[i.getTextureY()][i.getTextureX()];
			tempText = tempReg.getTexture();
			if(!tempText.getTextureData().isPrepared())
				tempText.getTextureData().prepare();
			tempPix = tempText.getTextureData().consumePixmap();
			for(int X = 0; X < tempReg.getRegionWidth(); X++)
			{
				for(int Y = 0; Y < tempReg.getRegionHeight(); Y++)
				{
					temp.drawPixel(X,(20*n)+(2*n) + Y, tempPix.getPixel(tempReg.getRegionX() + X, tempReg.getRegionY() + Y));
				}
			}
			
			//do rectangle stuff here
			tempHit = new Rectangle(x + 2,y + (h*scale-4) - ((40*scale) * (n+1)) - ((4*scale)*n) , w * scale, 40 * scale);
			hitBoxes.add(tempHit);
			itemBoxes.add(tempHit,i);
			
			n++;
		}
		Pixmap temp2 = new Pixmap((int)(temp.getWidth() * scale), (int)(temp.getHeight() * scale), temp.getFormat());
		temp2.setFilter(Filter.NearestNeighbour);
		temp2.drawPixmap(temp, 0,0,temp.getWidth(), temp.getHeight(),0,0,temp2.getWidth(),temp2.getHeight());
		list = new Texture(temp2);
		
		listMap = temp2;
		temp.dispose();
		
		
	}
	
	public Item getSelected()
	{
		return selected;
	}
	
	public void render()
	{
		x = (int) (Gameplay.camera.position.x + screenPos.x/scale);
		y = (int)(Gameplay.camera.position.y + screenPos.y/scale);
		
		mousePos.x = Gdx.input.getX()-Gdx.graphics.getWidth()/2;
		mousePos.y = -(Gdx.input.getY()-Gdx.graphics.getHeight()/2);
		
		font.draw(batch, Gdx.input.getX() + "," + Gdx.input.getY() + "\n" + mousePos,100,100);
		if(hitBox.contains(mousePos.x,mousePos.y))
		{
			if(Gdx.input.isButtonJustPressed(Buttons.LEFT))
			{
				for(Rectangle r : hitBoxes)
				{
					if(r.contains(mousePos.x,mousePos.y))
					{
						selected = itemBoxes.getElement(r);
					}
				}
			}
			
			int prev = scrollY;
			if(((MarinerInputProcessor)Gdx.input.getInputProcessor()).scroll() > 0 && scrollY >0)
			{
				scrollY -= 5;
				
			}
			else if(((MarinerInputProcessor)Gdx.input.getInputProcessor()).scroll() < 0 && scrollY < list.getHeight() - (h-4))
			{
				scrollY += 5;
			}
			if(scrollY > list.getHeight()-(h-4))
				scrollY = list.getHeight()-(h-4);
			if(scrollY < 0)
				scrollY = 0;
			
			for(Rectangle r : hitBoxes)
			{
				r.setY(r.getY() - (prev - scrollY)*scale);
			}
		}
		
		Pixmap tempPixmap = new Pixmap(listMap.getWidth(),listMap.getHeight(),Format.RGBA8888);
		tempPixmap.drawPixmap(listMap,0,0);
		if(selected != null)
		{
			tempPixmap.drawPixmap(shadeMap,0, 22*(int)scale * items.indexOf(selected));
		}
		
		batch.draw(background, x,y);
		if(list.getHeight()<h)
		{
			batch.draw(new Texture(tempPixmap), x +2, y-2 + (h - list.getHeight()), 0, scrollY, w-4, list.getHeight());
		}
		else
		{
			batch.draw(new Texture(tempPixmap), x +2, y+2, 0, scrollY, w-4, h-4);
		}
		
		
	}
	
}
