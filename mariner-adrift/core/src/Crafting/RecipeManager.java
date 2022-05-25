package Crafting;

import java.util.ArrayList;

import com.csds.marineradrift.Entry;
import com.csds.marineradrift.Map;

import Inventory.Inventory;
import Inventory.Item;

public class RecipeManager {
	public static Map<Integer, Recipe> recipes;
	private ArrayList<Integer> unlockedItem;
	private static Inventory inventory;
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
	public static void craft(int ID) {
		Recipe r = recipes.getElement(ID);
		for(Item i: r.getIngredients()) {
			inventory.remove(i);
		}
		for(Item i: r.getResults()) {
			inventory.add(i);
		}
	}
	static{
		recipes = new Map<Integer, Recipe>();
		ArrayList<Item> temp = new ArrayList();
		ArrayList<Item> temp2 = new ArrayList();
		temp.add(new Item(0, 4));
		temp.add(new Item(2, 8));
		temp2.add(new Item(24, 1));
		recipes.add(24, new Recipe(false, temp, temp2));
		temp.clear();
		temp2.clear();
		
		temp.add(new Item(1, 4));
		temp.add(new Item(2, 8));
		temp2.add(new Item(25, 1));
		recipes.add(25, new Recipe(false, temp, temp2));
		temp.clear();
		temp2.clear();
		
		temp.add(new Item(0, 8));
		temp.add(new Item(1, 8));
		temp2.add(new Item(26, 1));
		recipes.add(26, new Recipe(false, temp, temp2));
		temp.clear();
		temp2.clear();
		
		temp.add(new Item(0, 4));
		temp.add(new Item(3, 8));
		temp2.add(new Item(27, 1));
		recipes.add(27, new Recipe(false, temp, temp2));
		temp.clear();
		temp2.clear();
		
		temp.add(new Item(3, 1));
		temp.add(new Item(0, 1));
		temp2.add(new Item(4, 1));
		recipes.add(4, new Recipe(false, temp, temp2));
		temp.clear();
		temp2.clear();
		
		temp.add(new Item(4, 9000));
		temp2.add(new Item(5, 1));
		recipes.add(5, new Recipe(false, temp, temp2));
		temp.clear();
		temp2.clear();
		
		temp.add(new Item(3, 10));
		temp.add(new Item(0, 8));
		temp2.add(new Item(28, 1));
		recipes.add(28, new Recipe(false, temp, temp2));
		temp.clear();
		temp2.clear();
		
		temp.add(new Item(0, 20));
		temp2.add(new Item(29, 1));
		recipes.add(29, new Recipe(false, temp, temp2));
		temp.clear();
		temp2.clear();
	}
}