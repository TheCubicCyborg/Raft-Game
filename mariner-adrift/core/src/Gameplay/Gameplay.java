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

import Entities.Raft;
import Interfaces.ScrollList;
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
	private boolean inventoryOpen;
	
	SpriteBatch batch;
	public static OrthographicCamera camera;
	
	Sprite sprite;
	
	private Raft raft;
	
	ScrollList tempList;
	
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
		pauseOpen = false;
		

		raft = new Raft(world.getFocused(), 0,0, 3 * Chunk.tileSize, 3 * Chunk.tileSize, batch);
		
		player = new Player(world, inventory, raft, 0, 0, batch);
		
		sprite = new Sprite(new Texture("badlogic.jpg"));
		
		ArrayList<Item> temp = new ArrayList<Item>();
		temp.add(new Item(0,5));
		temp.add(new Item(0,5));
		temp.add(new Item(0,5));
		temp.add(new Item(1,4));
		temp.add(new Item(2, 2));
		tempList = new ScrollList(temp, 200,-200, 86, 100, 2f, batch);
	}
	
	public void update(float delta)
	{
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		
		world.update(delta);
		
		raft.render();
		
		processInputs();
		player.update(delta);
		
		tempList.render();
		
		Vector2 temp = player.getPos();
		camera.position.set(temp.x,temp.y,0);
		camera.update();
		

		
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
		else if(!inventoryOpen)
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
			if(!pauseOpen)
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
	
	
}
