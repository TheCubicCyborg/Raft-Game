package com.csds.marineradrift;   

public class SkillTree {

	private Skill start;
	
	public SkillTree()
	{
		start = null;
	}
	
	public void add(String n, String d, Item[] r, Skill p)
	{
		Skill temp = new Skill(n,d,r, p);
		p.addChild(temp);
		
		if(start == null)
			start = temp;
	}
	
	public void toJson()
	{
		
	}
	
	
	
	
	
	
}
