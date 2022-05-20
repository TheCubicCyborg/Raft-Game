package Skills;

import java.util.ArrayList;

import Inventory.Item;

public class Skill {
	private Skill parent;
	private ArrayList<Skill> children;
	private String name, description;
	private Item[] requirements;
	private boolean unlocked;
	
	public Skill(String n, String d, Item[] r, Skill p)
	{
		name = n;
		description = d;
		requirements = r;
		parent = p;
		children = new ArrayList<Skill>();
		unlocked = false;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public Item[] getRequirements()
	{
		return requirements;
	}
	
	public void setRequirements(Item[] r)
	{
		requirements = r;
	}
	
	public void setDescription(String d)
	{
		description = d;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void addChild(Skill child)
	{
		children.add(child);
	}
	
	public void setParent(Skill parent)
	{
		this.parent = parent;
	}
	
	public ArrayList<Skill> getChildren()
	{
		return children;
	}
	
	public void removeChild(Skill child)
	{
		children.remove(child);
	}
	
	public Skill parent()
	{
		return parent;
	}
	
	public boolean unlock()
	{
		if(parent.unlocked)
		{
			unlocked = true;
			return true;
		}	
		else
			return false;
	}
	public boolean equals(Skill o) {
		return name.equals(o.name);
	}
	
}
