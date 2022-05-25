package Interfaces;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Crafting.Recipe;
import Crafting.RecipeManager;
import Inventory.Item;

public class Crafting {

	private Texture background;
	private ScrollList itemList;
	private Button craftButton;
	private Recipe selected;
	private SpriteBatch batch;
	private Vector2 screenPos;
	ArrayList<Item> recipes;
	
	int x,y;
	private static int scale = 2;
	
	public Crafting(SpriteBatch batch, ArrayList<Item> recipes)
	{
		this.batch = batch;
		this.recipes = recipes;
		//400x188
		background = new Texture("CraftingInterface.png");
		screenPos = new Vector2(-background.getWidth()/2 *scale,-background.getHeight()/2*scale);
		x = (int)screenPos.x;
		y = (int)screenPos.y;
		itemList = new ScrollList(recipes, ((int)screenPos.x+(16*scale))*scale,((int)screenPos.y+16*scale)*scale , 90*scale,156*scale,2,batch);
		craftButton = new Button(((int)screenPos.x+304*scale),((int)screenPos.y+16*scale),80*scale,20*scale,"Craft",batch);
		
	}
	
	public void render()
	{
		x = (int) (Gameplay.Gameplay.camera.position.x + screenPos.x);
		y = (int) (Gameplay.Gameplay.camera.position.y + screenPos.y);
		
		
		
		batch.draw(background,x,y,background.getWidth()*scale,background.getHeight()*scale);
		itemList.render();
		craftButton.render();
	}
	
	public boolean craft(Item i)
	{
		if (craftButton.isJustPressed())
			RecipeManager.craft(i.getId());
		return true;
	}
	
}
