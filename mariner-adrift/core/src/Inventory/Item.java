package Inventory;

public class Item implements Comparable{

	private int amt;
	private int itemId;
	private boolean selected;
	
	public Item(int id, int a)
	{
		itemId = id;
		amt = a;
		selected = false;
	}
	
	public int getTextureX()
	{
		return (itemId%12);
	}
	
	public int getTextureY()
	{
		return ((int)(itemId/12));
	}
	
	public int getAmt()
	{
		return amt;
	}
	
	public void setAmt(int a)
	{
		amt = a;
	}
	
	public int getId()
	{
		return itemId;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean b)
	{
		selected = b;
	}
	
	public void combineItems(Item i)
	{
		i.amt += this.amt;
	}
	
	public void putOne(Item i)
	{
		i.amt += 1;
		this.amt -= 1;
	}
	
	public Item pickUp()
	{
		return this;
	}
	
	
	public Item takeOne()
	{
		if(amt >0)
		{
			Item ret = new Item(itemId, 1);
			amt -= 1;
			return ret;
		}
		else
			return this;
	}
	public boolean equals(Item other) {
		return itemId == other.itemId;
	}
	public int compareTo(Object o) {
		Item other = (Item) o;
		if (amt < other.amt)
			return -1;
		else if (amt == other.amt)
			return 0;
		else
			return 1;
	}
}
