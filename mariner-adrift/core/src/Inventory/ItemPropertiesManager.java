package Inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.csds.marineradrift.Map;

public class ItemPropertiesManager {
	
	public static TextureRegion[][] textures, digits;
	static Map<Integer, ItemProperties> ip = new Map<Integer, ItemProperties>();
	
	public ItemPropertiesManager()
	{
		textures = new TextureRegion(new Texture("ItemMap.png")).split(20, 20);
		digits = new TextureRegion(new Texture("digits.png")).split(5, 7);
		initiateProperties();
	}
	
	public static ItemProperties getItemProps(int i)
	{
		return ip.getElement(i);
	}
	
	public void initiateProperties()
	{
		ip.add(0,new ItemProperties(64,false));
		ip.add(1,new ItemProperties(64,false));
		ip.add(2,new ItemProperties(64,false));
	}
	
}
