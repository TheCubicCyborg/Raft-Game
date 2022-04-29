package Crafting;

import java.util.ArrayList;
import Inventory.Item;
import WorldMap.Tile;

public class Recipe {
	private boolean locked;
	private ArrayList<Item> ingredients;
	private ArrayList<Item> results;
	private Tile station;
	public Recipe(boolean locked, ArrayList<Item> ingredients, ArrayList<Item> results, Tile station) {
		this.locked = locked;
		this.ingredients = ingredients;
		this.results = results;
		this.station = station;
	}
	public boolean locked() {
		return locked;
	}
	public void unlock() {
		if (locked) {
			locked = false;
		}
	}
	public ArrayList<Item> getIngredients(){
		return ingredients;
	}
	public ArrayList<Item> getResults(){
		return results;
	}
}
