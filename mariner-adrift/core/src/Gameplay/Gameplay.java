package Gameplay;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.csds.marineradrift.MarinerInputProcessor;

import Crafting.RecipeManager;
import Entities.DroppedItem;
import Entities.Fish;
import Entities.Raft;
import Interfaces.Button;
import Interfaces.Crafting;
import Interfaces.ScrollList;
import Interfaces.Skills;
import Interfaces.Text;
import Inventory.Inventory;
import Inventory.Item;
import Inventory.ItemPropertiesManager;
import Screens.GameScreen;
import WorldMap.Chunk;
import WorldMap.TilePropertiesManager;
import WorldMap.World;

public class Gameplay {
	
	public static boolean isPaused;
	public static Player player;
	private World world;
	private Inventory inventory;
	private PauseMenu pauseMenu;
	public static boolean pauseOpen;
	private static boolean inventoryOpen, craftingOpen, skillsOpen;
	
	SpriteBatch batch;
	public static OrthographicCamera camera;
	
	Sprite sprite;
	
	private Raft raft;
	
	ArrayList<Item> craftableItems;
	private Crafting craftingInterface;
	private Skills skillInterface;
	
	
	public Gameplay()
	{
		Gdx.input.setInputProcessor(new MarinerInputProcessor());
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.zoom = (float)(1.0/2.0);
		batch = new SpriteBatch();
		TilePropertiesManager tileProp = new TilePropertiesManager();
		
		isPaused = false;
		world = new World(batch);
		ItemPropertiesManager ItemProp = new ItemPropertiesManager();
		
		
		inventory = new Inventory(batch);
		pauseMenu = new PauseMenu(batch);
		inventoryOpen = false;
		craftingOpen = false;
		pauseOpen = false;
		skillsOpen = false;
		
		RecipeManager RecipeMan = new RecipeManager(inventory);

		raft = new Raft(world.getFocused(), 0,0, 3 * Chunk.tileSize, 3 * Chunk.tileSize, batch);
		
		player = new Player(world, inventory, raft, 0, 0, batch);
		
		sprite = new Sprite(new Texture("badlogic.jpg"));
		
		craftableItems = new ArrayList<Item>();
		craftableItems.add(new Item(4, 1));
		craftableItems.add(new Item(5, 1));
		craftableItems.add(new Item(24, 1));
		craftableItems.add(new Item(25, 1));
		craftableItems.add(new Item(26, 1));
		craftableItems.add(new Item(27, 1));
		craftableItems.add(new Item(28, 1));
		craftableItems.add(new Item(29, 1));
		
		craftingInterface = new Crafting(batch,craftableItems);
		skillInterface = new Skills(inventory,batch);
	}
	
	public void update(float delta)
	{
		batch.setProjectionMatrix(camera.combined);
		
		attemptSpawn();
		
		batch.begin();
		
		
		world.update(delta);
		
		raft.render();
		
		processInputs();
		player.update(delta);
		
		
		Vector2 temp = player.getPos();
		camera.position.set(temp.x,temp.y,0);
		camera.update();
		
		if(craftingOpen)
		{
			craftingInterface.render();
			isPaused = true;
		}
		else if(!pauseOpen)
			isPaused = false;
		
		if(inventoryOpen)
		{
			inventory.Render(delta);
			isPaused = true;
		}
		else if(!pauseOpen)
			isPaused = false;
		
		if(skillsOpen)
		{
			skillInterface.render();
			isPaused = true;
		}
		else if(!pauseOpen)
			isPaused = false;
		
		
		if(pauseOpen)
		{
			pauseMenu.render(delta);
			isPaused = true;
		}
		else if(!inventoryOpen || !craftingOpen)
			isPaused = false;
		
		batch.end();
		
		((MarinerInputProcessor)Gdx.input.getInputProcessor()).update();
	}
	
	public void dispose()
	{
		inventory.dispose();
		player.dispose();
	}
	
	public void processInputs()
	{
		if(Gdx.input.isKeyJustPressed(Keys.E))
		{
			if(!pauseOpen && !craftingOpen && !skillsOpen)
			{
			inventoryOpen = !inventoryOpen;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			pauseOpen = !pauseOpen;
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.I))
		{
			if(!pauseOpen && !craftingOpen && !inventoryOpen)
			{
				skillsOpen = !skillsOpen;
			}
		}
		
		
		
		if(Gdx.input.isKeyPressed(Keys.BACKSPACE))
		{
			Gdx.app.exit();
		}
		
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			raft.moveRaft(new Vector2(-5, 0));
		}
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			raft.moveRaft(new Vector2(5, 0));
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			raft.moveRaft(new Vector2(0, 5));
		}
		
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			raft.moveRaft(new Vector2(0, -5));
		}
		
	}
	
	public void attemptSpawn()
	{
		if((int)(Math.random()*150) == 0)
		{
			int numChunks = (world.renderDistance * 2 + 1) *2 -1;
			int temp = (int)(Math.random()*numChunks);
			Chunk c;
			if(temp <= numChunks/2)
			{
				c = world.getRendered().getElement(new Vector2(-World.renderDistance,World.renderDistance - temp));
			}
			else
			{
				c = world.getRendered().getElement(new Vector2(temp-(numChunks/2) - World.renderDistance, -World.renderDistance));
			}
			
			temp = (int)(Math.random()*4);
			if(temp != 3)
			{
				new DroppedItem(c,c.getCoords().x*Chunk.totalSize,c.getCoords().y*Chunk.totalSize,
						Chunk.tileSize,Chunk.tileSize, batch, new Item(temp,1), false);
			}
			else
			{
				new Fish(c,c.getCoords().x*Chunk.totalSize,c.getCoords().y*Chunk.totalSize,
						Chunk.tileSize,Chunk.tileSize, batch);
			}
			
		}
	}
	
	public static void setCraftingOpen(boolean b)
	{
		craftingOpen = b;
	}
	
}
