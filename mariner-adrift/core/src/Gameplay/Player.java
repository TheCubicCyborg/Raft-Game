package Gameplay;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import Entities.DroppedItem;
import Entities.Entity;
import Inventory.Inventory;
import Inventory.Item;
import WorldMap.Chunk;
import WorldMap.World;


public class Player extends Entity {

	Vector2 position, speed, acceleration;
	//STATES
	static final int IDLE = 0;
	static final int WALK = 1;
	static final int RUN = 2;
	static final int SLEEP = 4;
	static final int DYING = 5;
	static final int PICKUP = 6;
	static final int DEAD = 7;
	
	public static final float width = 16;
	public static final float height = 32;
	
	private boolean inWater; 
	
	//Directions
	static final int UP = 1;
	static final int DOWN = -1;
	static final int LEFT = -1;
	static final int RIGHT = 1;

	
	static float ACC = 400f;
	static final float WALK_VEL = 120;
	static final float RUN_VEL = 200;
	static float MAX_VEL = 120f;
	static final float DAMP = 0;
	
	Vector2 vel = new Vector2();
	Vector2 dir = new Vector2();
	
	int state;
	float stateTime;
	
	World world;
	Vector2 currentChunkCoords;
	
	private SpriteBatch batch;
	private Sprite player;
	
	Inventory inventory;
	
	public Player(World world, Inventory inventory, float x, float y, SpriteBatch batch)
	{
		super(world.getFocused(),x,y,width,height,batch);
		this.batch = batch;
		
		inWater = false;
		
		this.world = world;
		vel.x = 0;
		vel.y = 0;
		
		state = IDLE;
		stateTime = 0;

		player = new Sprite(new Texture("player.png"));
		
		this.inventory = inventory;
		
		currentChunkCoords = new Vector2(world.getFocused().getCoords());
	}
	
	public void update(float deltaTime)
	{
		
		if(!Gameplay.isPaused)
		{
			processInput();
			
			if(vel.x > MAX_VEL) vel.x = MAX_VEL;
			if(vel.x < -MAX_VEL) vel.x = -MAX_VEL;
			if(vel.y > MAX_VEL) vel.y = MAX_VEL;
			if(vel.y < -MAX_VEL) vel.y = -MAX_VEL;
			
			vel.scl(deltaTime);
			tryMove();
			vel.scl(1/deltaTime);
			
			stateTime += deltaTime;

		}
		
		player.setOrigin(8, 0);
		player.setPosition(pos.x, pos.y);
		renderPlayer();
	}
	
	public void renderPlayer()
	{
		player.draw(batch);
	}
	
	
	private void processInput() 
	{
		if(state == DYING || state == DEAD || state == PICKUP)
			return;
			
		if(Gdx.input.isButtonJustPressed(Buttons.RIGHT))
		{
			ArrayList<Entity> temp = super.overlaps();
			
			if(temp != null)
			{
				for(Entity e : temp)
				{
					
					if(e instanceof DroppedItem)
					{
						
						pickUp((DroppedItem) e);
						return;
					}
				}
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))
		{
			MAX_VEL = RUN_VEL;
		}
		else
		{
			MAX_VEL = WALK_VEL;
		}
		
		int wTimer = 0, sTimer = 0, dTimer = 0, aTimer = 0;
		
		if(Gdx.input.isKeyPressed(Keys.W) && (sTimer == 0 || wTimer <= sTimer))
		{
			dir.y = UP;
			//acc.y = ACC * dir.y;
			vel.y = MAX_VEL * dir.y;
			wTimer++;
		}
		else
		{
			wTimer = 0;
			if(!Gdx.input.isKeyPressed(Keys.S))
				vel.y = 0;
		}
			
		
		if(Gdx.input.isKeyPressed(Keys.S) && (wTimer == 0 || sTimer <= wTimer))
		{
			dir.y = DOWN;
			//acc.y = ACC * dir.y;
			vel.y = MAX_VEL * dir.y;
			sTimer++;
		}
		else
		{
			sTimer = 0;
			if(!Gdx.input.isKeyPressed(Keys.W))
				vel.y = 0;
		}
		
		if(Gdx.input.isKeyPressed(Keys.D) && (aTimer == 0 || dTimer <= aTimer))
		{
			dir.x = RIGHT;
			//acc.x = ACC * dir.x;
			vel.x = MAX_VEL * dir.x;
			dTimer++;
		}
		else
		{
			dTimer = 0;
			if(!Gdx.input.isKeyPressed(Keys.A))
				vel.x = 0;
		}
		
		if(Gdx.input.isKeyPressed(Keys.A) && (dTimer == 0 || aTimer <= dTimer))
		{
			dir.x = LEFT;
			//acc.x = ACC * dir.x;
			vel.x = MAX_VEL*dir.x;
			aTimer++;
		}
		else
		{
			aTimer = 0;
			if(!Gdx.input.isKeyPressed(Keys.D))
				vel.x = 0;
		}
		
		
		if(Gdx.input.isKeyJustPressed(Keys.O))
		{
			new DroppedItem(currentChunk,currentChunk.getCoords().x * Chunk.totalSize, currentChunk.getCoords().y * Chunk.totalSize, 20,20, batch, new Item(1, 5));
		}
		
	}
	
	private void tryMove()
	{
		if(vel.x != 0 || vel.y != 0)
		{
		pos.add(vel);
		chunkMovementCheck();
		}
	}
	
	private void pickUp(DroppedItem item)
	{
		if(inventory.add(item.getItem()))
		{
			item.dispose();
		}
		
		
	}
	
	public void dispose()
	{
		player.getTexture().dispose();
	}
	
	public Vector2 getPos()
	{
		return pos;
	}
	
	public void chunkMovementCheck()
	{		
		
		if(pos.x >= (currentChunkCoords.x +1) * Chunk.totalSize)
		{
			world.setFocused(world.getFocused().east());
			world.refreshRendered(World.EAST);
			world.updateRendered(World.EAST);
			currentChunk = currentChunk.east();
			currentChunkCoords.x += 1;
		}
		else if(pos.x < (currentChunkCoords.x) * Chunk.totalSize)
		{
			world.setFocused(world.getFocused().west());
			world.refreshRendered(World.WEST);
			world.updateRendered(World.WEST);
			currentChunk = currentChunk.west();
			currentChunkCoords.x -= 1;
		}
		
		if(pos.y >= (currentChunkCoords.y +1) * Chunk.totalSize)
		{
			world.setFocused(world.getFocused().north());
			world.refreshRendered(World.NORTH);
			world.updateRendered(World.NORTH);
			currentChunk = currentChunk.north();
			currentChunkCoords.y += 1;
		}
		else if(pos.y < (currentChunkCoords.y) * Chunk.totalSize)
		{
			world.setFocused(world.getFocused().south());
			world.refreshRendered(World.SOUTH);
			world.updateRendered(World.SOUTH);
			currentChunk = currentChunk.south();
			currentChunkCoords.y -= 1;
		}
		

		
	}
	public void update() {
		
	}
	
	public String toString()
	{
		return "Player";
	}
}
