package Inventory;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ItemProperties {
	private boolean isConsumable;
	private int maxSize;
	
	public ItemProperties(int m, boolean c) 
	{
		maxSize = m;
		isConsumable = c;
	}
	
	public int getMaxSize()
	{
		return maxSize;
	}
}
