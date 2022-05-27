package Interfaces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Inventory.Inventory;
import Inventory.Item;
import Skills.Skill;
import Skills.SkillTree;

public class Skills {

	private SpriteBatch batch;
	private static SkillTree st;
	private Texture background;
	private Button unlockButton;
	private Skill selected;
	private Vector2 screenPos;
	int x,y;
	
	private static int scale = 2;
	
	
	public Skills(Inventory inventory, SpriteBatch batch)
	{
		this.batch = batch;
		background = new Texture("Skill Tree.png");
		st = new SkillTree(inventory);
		
		
		screenPos = new Vector2(-background.getWidth()/2 *scale,-background.getHeight()/2*scale);
		x = (int)screenPos.x;
		y = (int)screenPos.y;
	}
	
	public void render()
	{
		x = (int) (Gameplay.Gameplay.camera.position.x + screenPos.x);
		y = (int) (Gameplay.Gameplay.camera.position.y + screenPos.y);
		
		batch.draw(background,x,y,background.getWidth()*scale,background.getHeight()*scale);
	}
	
	
	
}
