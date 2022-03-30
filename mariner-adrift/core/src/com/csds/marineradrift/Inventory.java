package com.csds.marineradrift;

public class Inventory {
	
	private Item[][] inventory;
	
	
	
	public Inventory()
	{
		//first 3 rows are main inventory, row 4 is hotbar, row 5 is miscelaneous slots ie. Armor, equippables
		inventory = new Item[5][9];
		
	}
	
	
}
