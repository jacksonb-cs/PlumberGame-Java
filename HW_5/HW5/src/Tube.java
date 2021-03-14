/*
Name: Jackson Bullard
Date: 2020.10.23
Assignment: HW #5
*/

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Tube extends Sprite
{
	static BufferedImage tube_img = null;
	Model model;
	
	Tube(int x, int y, Model m)
	{
		posX = x;
		posY = y;
		
		width = 55;
		height = 400;
		
		model = m;
		
		loadImg();
	}
	
	Tube(Json ob, Model m)
	{
		posX = (int)ob.getLong("x");
		posY = (int)ob.getLong("y");
		
		width = 55;
		height = 400;
		
		model = m;
		
		loadImg();
	}
	
	@Override
	boolean isTube()
	{
		return true;
	}
	
	@Override
	public void update()
	{
	}
	
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(tube_img, posX - model.view.scrollVal, posY, null);
	}
	
	@Override
	public void loadImg()
	{
		if (tube_img == null)	// Loads once ONLY
		{
			try
			{
				tube_img = ImageIO.read(new File("tube.png"));
			}
			catch (Exception e)
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}
	
	public boolean clickedOnTube(int x, int y)
	{
		if ((x >= posX) && (x <= posX + 55) && (y >= posY) && (y <= posY + 400))
			return true;
		else
			return false;
	}
	
	// Saves position of all tubes
	public Json marshal()
	{
		Json ob = Json.newObject();
		
		ob.add("x", posX);
		ob.add("y", posY);
		
		return ob;
	}
}