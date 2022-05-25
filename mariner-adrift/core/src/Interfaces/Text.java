package Interfaces;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

public class Text {

	private static final Pixmap fontMap;
	String text;
	private Pixmap pixmap;
	
	static
	{
		Texture temp = new Texture("font.png");
		temp.getTextureData().prepare();
		fontMap = temp.getTextureData().consumePixmap();
	}
	
	public Text(String text)
	{
		
		this.text = text;
		
		pixmap = new Pixmap(text.length()*7,9,Format.RGBA8888);
		int n = 0;
		for(char c : text.toCharArray())
		{
			pixmap.drawPixmap(fontMap, n*7, 0, getMapIndex(c)*7, 0, 7, 9);
			n++;
		}
	}
	
	public Pixmap getPixmap()
	{
		return pixmap;
	}
	
	public int getMapIndex(char c)
	{
		if(c>= 32 && c <= 126)
			return c -32;
		else
		{
			//if no texture, returns *
			return 10;
		}
	}
}
