package Inventory;

public class Item {

	private int amt;
	private int itemId;
	
	public Item(int id, int a)
	{
		itemId = id;
		amt = a;
	}
	
	public Item splitStack()
	{
		if(amt > 1)
		{
			int temp = amt/2;
			Item ret = new Item(itemId, amt - temp);
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
			Item ret = new Item(itemId, 1);
			amt -= 1;
			return ret;
		}
		else
			return this;
	}
}
