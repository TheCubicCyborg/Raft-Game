package com.csds.marineradrift;

import com.badlogic.gdx.Gdx;

public class Cursor {

	private float x, y;
	private final static int GAME = 0;
	private final static int MENU = 1;
	private final static int INVENTORY = 2;
	private Object selected;
	private int state;
	
	public Cursor()
	{
		state = 0;
	}
	
	public void update(float deltaTime)
	{
		x = Gdx.input.getX();
		y = Gdx.input.getY();
		
		if(state == GAME)
			gameUpdate(deltaTime);
		else if(state == MENU)
			menuUpdate(deltaTime);
		else if(state == INVENTORY)
			inventoryUpdate(deltaTime);
	}
	
	private void gameUpdate(float deltaTime)
	{
		
	}
	
	private void menuUpdate(float deltaTime)
	{
		
	}
	
	private void inventoryUpdate(float deltaTime)
	{
		
	}
}
