package com.csds.marineradrift;

import com.badlogic.gdx.InputAdapter;

public class MarinerInputProcessor extends InputAdapter{

	private float Y;
	
	public void update()
	{
		Y= 0;
	}
	
	public boolean scrolled(float amountX, float amountY)
	{
		Y = amountY;
		
		if(amountX != 0 || amountY != 0)
			return true;
		else
			return false;
	}
	
	public float scroll()
	{
		return Y;
	}
}
