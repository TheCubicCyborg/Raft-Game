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
import Entities.Raft;
import Interfaces.Button;
import Interfaces.Crafting;
import Interfaces.ScrollList;
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
	private static boolean inventoryOpen, craftingOpen;
	
	SpriteBatch batch;
	public static OrthographicCamera camera;
	
	Sprite sprite;
	
	private Raft raft;
	
	ArrayList<Item> craftableItems;
	private Crafting craftingInterface;
	
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
	}
	
	public void update(float delta)
	{
		batch.setProjectionMatrix(camera.combined);
		
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
			if(!pauseOpen && !craftingOpen)
			{
			inventoryOpen = !inventoryOpen;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			pauseOpen = !pauseOpen;
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
	
	public static void setCraftingOpen(boolean b)
	{
		craftingOpen = b;
	}
	
}
