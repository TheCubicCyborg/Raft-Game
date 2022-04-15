package Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import Inventory.Inventory;
import WorldMap.Chunk;
import WorldMap.World;

public class Player {

	Vector2 position, speed, acceleration;
	//STATES
	static final int IDLE = 0;
	static final int WALK = 1;
	static final int RUN = 2;
	static final int SLEEP = 4;
	static final int DYING = 5;
	static final int PICKUP = 6;
	static final int DEAD = 7;
	
	
	
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
	
	Vector2 pos = new Vector2();
	Vector2 acc = new Vector2();
	Vector2 vel = new Vector2();
	Vector2 dir = new Vector2();
	
	public Rectangle hitbox = new Rectangle();
	
	int state;
	float stateTime;
	
	World world;
	Vector2 currentChunk;
	
	private SpriteBatch batch;
	private Sprite player;
	
	public Player(World world, Inventory inventory, float x, float y, SpriteBatch batch)
	{
		this.batch = batch;
		
		inWater = false;
		
		this.world = world;
		pos.x = x;
		pos.y = y;
		acc.x = 0;
		acc.y = 0;
		vel.x = 0;
		vel.y = 0;
		
		hitbox.height = 0.8f;
		hitbox.width = 0.6f;
		hitbox.x = pos.x;
		hitbox.y = pos.y;
		
		
		state = IDLE;
		stateTime = 0;

		player = new Sprite(new Texture("player.png"));
		
		currentChunk = world.getFocused().getCoords();
	}
	
	public void update(float deltaTime)
	{
		
		if(!Gameplay.isPaused)
		{
			processInput();
			
			//acc.scl(deltaTime);
			
			//vel.add(acc.x, acc.y);
			//if(acc.x == 0) vel.x *= DAMP;
			//if(acc.y == 0) vel.y *= DAMP;
			
			if(vel.x > MAX_VEL) vel.x = MAX_VEL;
			if(vel.x < -MAX_VEL) vel.x = -MAX_VEL;
			if(vel.y > MAX_VEL) vel.y = MAX_VEL;
			if(vel.y < -MAX_VEL) vel.y = -MAX_VEL;
			
			vel.scl(deltaTime);
			tryMove();
			vel.scl(1/deltaTime);
			
			stateTime += deltaTime;
			
//			System.out.println(currentChunk.x + "," + currentChunk.y);
//			System.out.println(pos.x + "," + pos.y);
		}
		
		renderPlayer();
	}
	
	public void renderPlayer()
	{
		batch.begin();
		batch.draw(player,pos.x, pos.y, 16, 32);
		batch.end();
	}
	
	
	private void processInput() 
	{
		if(state == DYING || state == DEAD || state == PICKUP)
			return;
			
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
	}
	
	private void tryMove()
	{
		if(vel.x != 0 || vel.y != 0)
		{
		pos.add(vel);
		chunkMovementCheck();
		}
	}
	
	private void pickUp()
	{
		
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
		
		if(pos.x >= (currentChunk.x +1) * Chunk.totalSize)
		{
			world.setFocused(world.getFocused().east());
			world.updateRendered(World.EAST);
//			world.renderNew(World.EAST);
			currentChunk.x += 1;
		}
		else if(pos.x < (currentChunk.x) * Chunk.totalSize)
		{
			world.setFocused(world.getFocused().west());
//			world.renderNew(World.WEST);
			world.updateRendered(World.WEST);
			currentChunk.x -= 1;
		}
		
		if(pos.y >= (currentChunk.y +1) * Chunk.totalSize)
		{
			world.setFocused(world.getFocused().north());
//			world.renderNew(World.NORTH);
			world.updateRendered(World.NORTH);
			currentChunk.y += 1;
		}
		else if(pos.y < (currentChunk.y) * Chunk.totalSize)
		{
			world.setFocused(world.getFocused().south());
//			world.renderNew(World.SOUTH);
			world.updateRendered(World.SOUTH);
			currentChunk.y -= 1;
		}
		

		
	}
}
