package graphics;

import java.awt.Graphics;
/**
 * class represent drawing
 * @author Haim Nahmani 
 */
public interface IDrawable {
	 public final static String PICTURE_PATH = "C:\\Users\\Haim Nahmani\\Desktop\\pictures\\pictures\\";
	 public void loadImages(String nm);
	 public void drawObject(Graphics g);
	 public String getColor();	 
}

