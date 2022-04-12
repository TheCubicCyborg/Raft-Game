package Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Inventory.Inventory;
import Inventory.Item;
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
	
	public Gameplay()
	{
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		
		isPaused = false;
		world = new World();
		world.initiateWorld();
		inventory = new Inventory(batch);
		pauseMenu = new PauseMenu();
		inventoryOpen = false;
		pauseOpen = false;
		player = new Player(world, inventory, 0, 0, batch);

		sprite = new Sprite(new Texture("badlogic.jpg"));
	}
	
	public void update(float delta)
	{
		batch.setProjectionMatrix(camera.combined);
		
		processInputs();
		player.update(delta);
		
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
		
		batch.begin();
		sprite.draw(batch);
		batch.end();
		
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
	
	
	public static Vector2 worldToScreenPos(Vector2 v)
	{
		
		
		return v;
	}
	
	
}
