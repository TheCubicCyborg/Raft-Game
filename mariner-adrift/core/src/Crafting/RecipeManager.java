package Crafting;

import java.util.ArrayList;

import com.csds.marineradrift.Entry;
import com.csds.marineradrift.Map;

import Inventory.Inventory;
import Inventory.Item;

public class RecipeManager {
	private Map<Integer, Recipe> recipes;
	private ArrayList<Integer> unlockedItem;
	private Inventory inventory;
	public RecipeManager(Inventory inventory){
		recipes = new Map<Integer, Recipe>();
		unlockedItem = new ArrayList<Integer>();
		this.inventory = inventory;
	}
	public void unlockAll(int ID) {
		if(!unlockedItem.contains(ID)) {
			for(Entry r: recipes) {
				if (((Recipe) r.getElement()).locked())
					((Recipe) r.getElement()).unlock();
			}
			unlockedItem.add(ID);
		}
	}
	public void craft(int ID) {
		Recipe r = recipes.getElement(ID);
		for(Item i: r.getIngredients()) {
			inventory.remove(i);
		}
		for(Item i: r.getResults()) {
			inventory.add(i);
		}
	}
	
}