package main.java.com.nks.testgame.core;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	BufferedImage image;
	
	public BufferedImage loadItem(String imgname) 
	{
		try  {
			image = ImageIO.read(new FileInputStream("res/img"+imgname));
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public BufferedImage loadPlayer(String imgname) 
	{
		try {
			image = ImageIO.read(new FileInputStream("res/img"+imgname));
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
