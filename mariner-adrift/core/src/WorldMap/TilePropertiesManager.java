package WorldMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.csds.marineradrift.Map;

public class TilePropertiesManager {
	
	public static TextureRegion[][] backTexts, foreTexts;
	
	Map<Integer, TileProperties> tpm = new Map();
	
	public TilePropertiesManager()
	{
		backTexts = new TextureRegion(new Texture("BackTiles.png")).split(20,20);
		foreTexts = new TextureRegion(new Texture("ForeTiles.png")).split(20, 20);
	}

}
