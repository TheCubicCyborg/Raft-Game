package Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import Inventory.Inventory;
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
	
	static final float JUMP_VEL = 10f;
	static float ACC = 20f;
	static final float MAX_VEL = 6f;
	static final float DAMP = 0.9f;
	
	Vector2 pos = new Vector2();
	Vector2 acc = new Vector2();
	Vector2 vel = new Vector2();
	Vector2 dir = new Vector2();
	
	public Rectangle hitbox = new Rectangle();
	
	int state;
	float stateTime;
	World world;
	
	public Player(World world, Inventory inventory, float x, float y)
	{
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
	}
	
	public void update(float deltaTime)
	{
		processInput();
		
		acc.scl(deltaTime);
		
		vel.add(acc.x, acc.y);
		if(acc.x == 0) vel.x *= DAMP;
		if(acc.y == 0) vel.y *= DAMP;
		
		if(vel.x > MAX_VEL) vel.x = MAX_VEL;
		if(vel.x < -MAX_VEL) vel.x = -MAX_VEL;
		if(vel.y > MAX_VEL) vel.y = MAX_VEL;
		if(vel.y < -MAX_VEL) vel.y = -MAX_VEL;
		
		vel.scl(deltaTime);
		tryMove();
		vel.scl(1/deltaTime);
		
		stateTime += deltaTime;
	}
	
	
	private void processInput() 
	{
		if(state == DYING || state == DEAD || state == PICKUP)
			return;
			
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))
		{
			ACC = 30f;
		}
		else
		{
			ACC = 20f;
		}
		
		int wTimer = 0, sTimer = 0, dTimer = 0, aTimer = 0;
		
		if(Gdx.input.isKeyPressed(Keys.W) && wTimer <= sTimer)
		{
			dir.y = UP;
			acc.y = ACC * dir.y;
			wTimer++;
		}
		else
			wTimer = 0;
		
		if(Gdx.input.isKeyPressed(Keys.S) && sTimer <= wTimer)
		{
			dir.y = DOWN;
			acc.y = ACC * dir.y;
			sTimer++;
		}
		else
			sTimer = 0;
		
		if(Gdx.input.isKeyPressed(Keys.D) && dTimer <= aTimer)
		{
			dir.x = RIGHT;
			acc.x = ACC * dir.y;
			dTimer++;
		}
		else
			dTimer = 0;
		
		if(Gdx.input.isKeyPressed(Keys.A) && aTimer <= dTimer)
		{
			dir.x = LEFT;
			acc.x = ACC * dir.x;
			aTimer++;
		}
		else
			aTimer = 0;
	}
	
	private void tryMove()
	{
		
	}
	
	private void pickUp()
	{
		
	}
}
