package Inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.csds.marineradrift.Map;

public class ItemPropertiesManager {
	
	public static TextureRegion[][] textures, digits;
	public static Map<Integer, ItemProperties> ip = new Map<Integer, ItemProperties>();
	
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
		ip.add(0,new ItemProperties("Wood",64,false));
		ip.add(1,new ItemProperties("Stone",64,false));
		ip.add(2,new ItemProperties("Fiber",64,false));
		ip.add(3,new ItemProperties("Fish", 64, false));
		ip.add(4, new ItemProperties("Cooked Fish", 64, false));
		ip.add(5, new ItemProperties("The Sus", 1, false));
		ip.add(24, new ItemProperties("Wood Net", 1, false));
		ip.add(25, new ItemProperties("Stone Net", 1, false));
		ip.add(26, new ItemProperties("Stone Hammer", 1, false));
		ip.add(27, new ItemProperties("Fish Wand", 1, false));
		ip.add(28, new ItemProperties("Fish Sword", 1, false));
		ip.add(29, new ItemProperties("Wood Shovel", 1, false));
		
	}
	
}
