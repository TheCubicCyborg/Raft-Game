package com.csds.marineradrift;

public class Item {

	private int textureId, amt, maxAmt;
	private String name;
	
	public Item(String n, int t, int a, int m)
	{
		name = n;
		textureId = t;
		amt = a;
		maxAmt = m;
	}
	
	public Item splitStack()
	{
		if(amt > 1)
		{
			int temp = amt/2;
			Item ret = new Item(name, textureId, amt - temp, maxAmt);
			amt = temp;
			return ret;
		}
		else
			return this;
	}
	
	public Item takeOne()
	{
		if(amt >1)
		{
			Item ret = new Item(name, textureId, 1, maxAmt);
			amt -= 1;
			return ret;
		}
		else
			return this;
	}
}
