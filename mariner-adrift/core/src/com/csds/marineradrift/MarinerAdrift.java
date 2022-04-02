package com.csds.marineradrift;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Screens.MainMenu;

public class MarinerAdrift extends Game {
	SpriteBatch batch;
	Texture img;
	public static Cursor cursor;
	
	@Override
	public void create () {
		cursor = new Cursor();
		setScreen(new MainMenu(this));
	}
}
