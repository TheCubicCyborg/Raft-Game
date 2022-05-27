package Interfaces;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.csds.marineradrift.Entry;
import com.csds.marineradrift.Map;

import Inventory.Inventory;
import Inventory.Item;
import Skills.Skill;
import Skills.SkillManager;
import Skills.SkillTree;

public class Skills {

	private SpriteBatch batch;
	private static SkillTree st;
	private Texture background, select, locked;
	private Button unlockButton;
	private Skill selected;
	private Vector2 screenPos, mousePos;
	int x,y;
	
	private ArrayList<Rectangle> hitboxes;
	private Map<Rectangle, Skill> skillMap;
	private static int scale = 2;
	private Text description;
	
	BitmapFont tempfont;
	
	public Skills(Inventory inventory, SpriteBatch batch)
	{
		this.batch = batch;
		background = new Texture("Skill Tree.png");
		st = SkillManager.skilltree;
		((Skill)st.getRoot().get()).unlock();
		
		Pixmap temp = new Pixmap(20*scale,20*scale,Format.RGBA8888);
		temp.setColor(0,0,0,0.1f);
		temp.fill();
		select = new Texture(temp);
		
		temp = new Pixmap(20*scale,20*scale,Format.RGBA8888);
		temp.setColor(0,0,0,0.25f);
		temp.fill();
		locked = new Texture(temp);
		
		screenPos = new Vector2(-background.getWidth()/2 *scale,-background.getHeight()/2*scale);
		mousePos = new Vector2();
		x = (int)screenPos.x;
		y = (int)screenPos.y;
		
		initiateHitBoxes();
		
		unlockButton = new Button(-100, -100, 200, 50, "unlock", batch);
		
		tempfont = new BitmapFont();
	}
	
	public void render()
	{
		x = (int) (Gameplay.Gameplay.camera.position.x + screenPos.x) * scale;
		y = (int) (Gameplay.Gameplay.camera.position.y + screenPos.y) * scale;
		
		mousePos.x = Gdx.input.getX()-Gdx.graphics.getWidth()/2;
		mousePos.y = -(Gdx.input.getY()-Gdx.graphics.getHeight()/2);
		
		updateHitBoxes();
		
		
		batch.draw(background,x/scale,y/scale,background.getWidth()*scale,background.getHeight()*scale);
		unlockButton.render();
		
		for(Rectangle r : hitboxes)
		{
			Skill temp = skillMap.getElement(r);
			if(!temp.isUnlocked())
				batch.draw(locked,r.x/scale,r.y/scale);
			
			if(selected != null && temp.equals(selected))
				batch.draw(locked,r.x/scale,r.y/scale);
			
			if(r.contains(mousePos))
			{
				batch.draw(select,r.x/scale,r.y/scale);
				description = new Text(temp.getDescription());
				batch.draw(new Texture(description.getPixmap()),mousePos.x/scale,mousePos.y/scale);
				if(Gdx.input.isButtonJustPressed(Buttons.LEFT))
				{
					selected = temp;
				}
			}
		}
		
		
		if(unlockButton.isJustPressed() && selected != null)
		{
			selected.unlock();
			selected = null;
		}
		
	}

	
	private void initiateHitBoxes()
	{
		skillMap = new Map<Rectangle, Skill>();
		hitboxes = new ArrayList<Rectangle>();
		Rectangle temp = new Rectangle(x + 94*scale, y + 112*scale, 20*scale*2, 20*scale*2);
		hitboxes.add(temp);
		skillMap.add(temp,st.get("Wood 1").get());
		Rectangle temp2 = new Rectangle(x+94*scale, y+80*scale,20*scale*2,20*scale*2);
		hitboxes.add(temp2);
		skillMap.add(temp2,st.get("Wood 2").get());
		Rectangle temp3 = new Rectangle(x + 190*scale,y + 112*scale,20*scale*2,20*scale*2);
		hitboxes.add(temp3);
		skillMap.add(temp3,st.get("Stone 1").get());
		Rectangle temp4 = new Rectangle(x + 190*scale,y + 80*scale,20*scale*2,20*scale*2);
		hitboxes.add(temp4);
		skillMap.add(temp4,st.get("Stone 2").get());
		Rectangle temp5 = new Rectangle(x+286*scale,y+112*scale,20*scale*2,20*scale*2);
		hitboxes.add(temp5);
		skillMap.add(temp5,st.get("Fiber 1").get());
		Rectangle temp6 = new Rectangle(x+286*scale, y+80*scale,20*scale*2,20*scale*2);
		hitboxes.add(temp6);
		skillMap.add(temp6,st.get("Fiber 2").get());
		
		Rectangle temp7 = new Rectangle(x + 190*scale, y+154*scale, 20*scale*2, 20*scale*2);
		hitboxes.add(temp7);
		skillMap.add(temp7,st.get("Base").get());
		
				//286
	}
	
	public void updateHitBoxes()
	{
		for(Entry<Rectangle,Skill> e : skillMap)
		{
			switch(e.getElement().getName())
			{
			case "Wood 1":
				e.getID().setPosition(x + 94*scale*2,y + 112*scale*2);
				break;
			case "Wood 2":
				e.getID().setPosition(x + 94*scale*2,y + 80*scale*2);
				break;
			case "Stone 1":
				e.getID().setPosition(x + 190*scale*2,y + 112*scale*2);
				break;
			case "Stone 2":
				e.getID().setPosition(x + 190*scale*2,y + 80*scale*2);
				break;
			case "Fiber 1":
				e.getID().setPosition(x + 286*scale*2,y + 112*scale*2);
				break;
			case "Fiber 2":
				e.getID().setPosition(x + 286*scale*2,y + 80*scale*2);
				break;
			case "Base":
				e.getID().setPosition(x + 190*scale*2,y + 154*scale*2);
				break;			}
		}
	}
	
	
}
