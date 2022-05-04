package WorldMap;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
	protected int backID, foreID, x, y;
	protected Chunk chunk;
	private Sprite sprite;
	private SpriteBatch batch;
	
	public Tile (int b, int f, int x, int y, Chunk chunk, SpriteBatch batch) {
		this.backID = b;
		this.foreID = f;
		this.x = x * Chunk.tileSize;
		this.y = y * Chunk.tileSize;
		this.chunk = chunk;
		this.batch = batch;
		
		
		if(b != 0)
		{
			TextureRegion tempReg = TilePropertiesManager.backTexts[b/12][b%12];
			Texture tempText = tempReg.getTexture();
			if(!tempText.getTextureData().isPrepared())
				tempText.getTextureData().prepare();
			Pixmap tempPix = tempText.getTextureData().consumePixmap();
			Pixmap temp = new Pixmap(tempReg.getRegionWidth(), tempReg.getRegionHeight(), Format.RGBA8888);
			for (int X = 0; X < tempReg.getRegionWidth(); X++) {
			    for (int Y = 0; Y < tempReg.getRegionHeight(); Y++) {
			        int colorInt = tempPix.getPixel(tempReg.getRegionX() + X, tempReg.getRegionY() + Y);
			        temp.drawPixel(X, Y, colorInt);
			    }
			}
			tempReg = TilePropertiesManager.foreTexts[f/12][f%12];
			tempText = tempReg.getTexture();
			if(!tempText.getTextureData().isPrepared())
				tempText.getTextureData().prepare();
			tempPix = tempText.getTextureData().consumePixmap();
			for (int X = 0; X < tempReg.getRegionWidth(); X++) {
			    for (int Y = 0; Y < tempReg.getRegionHeight(); Y++) {
			        int colorInt = tempPix.getPixel(tempReg.getRegionX() + X, tempReg.getRegionY() + Y);
			        temp.drawPixel(X,Y,colorInt);
			    }
			}
			
			sprite = new Sprite(new Texture(temp));
			sprite.setPosition(this.x, this.y);
		}
		
	}
	
	
	public void render()
	{
		if(sprite != null)
			sprite.draw(batch);
	}
	
	public void destroy() {
		chunk.setTile(0,0, x, y);
		
	}
}
