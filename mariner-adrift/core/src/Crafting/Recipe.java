package Crafting;

import java.util.ArrayList;
import Inventory.Item;
import WorldMap.Tile;

public class Recipe {
	private boolean locked;
	private ArrayList<Item> ingredients;
	private ArrayList<Item> results;
	public Recipe(boolean locked, ArrayList<Item> ingredients, ArrayList<Item> results) {
		this.locked = locked;
		this.ingredients = ingredients;
		this.results = results;
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
