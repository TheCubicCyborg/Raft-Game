package Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import Inventory.Inventory;
import WorldMap.World;

public class Gameplay {
	
	private boolean isPaused;
	private Player player;
	private World world;
	private Inventory inventory;
	private boolean inventoryOpen, pauseOpen;
	
	public Gameplay()
	{
		isPaused = false;
		world = new World();
		inventory = new Inventory();
		inventoryOpen = false;
		pauseOpen = false;
		player = new Player(world, inventory, 0, 0);
	}
	
	public void update(float delta)
	{
		processInputs();
		if(inventoryOpen)
		{
			inventory.Render(delta);
			isPaused = true;
		}
		else if(!pauseOpen)
			isPaused = false;
		
		if(pauseOpen)
		{
			isPaused = true;
		}
		else if(!inventoryOpen)
			isPaused = false;
		
		
		if(!isPaused)
		{
			player.update(delta);
		}
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
	}
	
}
