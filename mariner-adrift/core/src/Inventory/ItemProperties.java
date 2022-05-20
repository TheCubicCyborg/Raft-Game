package Inventory;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ItemProperties {
	private String name;
	private boolean isConsumable;
	private int maxSize;
	
	public ItemProperties(String n, int m, boolean c) 
	{
		name = n;
		maxSize = m;
		isConsumable = c;
	}
	
	public int getMaxSize()
	{
		return maxSize;
	}
	
	public String name()
	{
		return name;
	}
}
