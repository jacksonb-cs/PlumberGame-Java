/*
Name: Jackson Bullard
Date: 2020.10.23
Assignment: HW #5
*/

import java.awt.Graphics;

abstract class Sprite
{
	int posX, posY;		// Stores x and y coordinates
	int width, height;	// Stores width and height of sprite
	
	abstract void update();
	abstract void draw(Graphics g);
	abstract void loadImg();
	
	boolean isMario()
	{
		return false;
	}
	
	boolean isTube()
	{
		return false;
	}
	
	boolean isFireball()
	{
		return false;
	}
	
	boolean isGoomba()
	{
		return false;
	}

	// Returns true if two sprites collide together
	boolean collidesWith(Sprite other)
	{
		if (!(posX + width < other.posX) && !(posX > other.posX + other.width)
				&& !(posY + height < other.posY))
		{
			return true;
		}
		else
			return false;
	}
}
