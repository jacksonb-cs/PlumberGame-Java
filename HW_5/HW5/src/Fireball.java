/*
Name: Jackson Bullard
Date: 2020.10.23
Assignment: HW #5
*/

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Fireball extends Sprite
{
	double velY;	// Vertical velocity
	double maxVelY;
	boolean flip;	// Horizontal direction of fireball
	int lifespan;	// Depletes as it moves
	boolean kill;
	
	Model model;	// Stores reference to working instance of model

	static BufferedImage fireball_img = null;	// Stores picture of fireball
	static int realGround = 349;	// 396 - height
	
	Fireball(int x, int y, boolean direction, Model m)
	{
		posX = x;
		posY = y;
		width = 47;
		height = 47;
		
		flip = direction;
		kill = false;
		velY = 0.0;
		maxVelY = 0.0;
		model = m;
		
		if (flip)
			lifespan = 400;		// Distance travelled before disappearing when moving left
		else
			lifespan = 1500;	// Distnace travelled before disappearing when moving right
		
		loadImg();
	}
	
	@Override
	public boolean isFireball()
	{
		return true;
	}

	@Override
	public void update()
	{
		// Vertical movement
		velY += 1.2;
		
		if (posY + velY > realGround)
		{
			posY = realGround;	// Keeps fireball from clipping through the ground
			
			if (maxVelY == 0)	// Stores the maximum kinetic energy after the first bounce
			{
				maxVelY = velY;
			}
			else
			{
				velY = - maxVelY;
			}
		}
		else
		{
			posY += velY;
		}
		
		if (flip)
		{
			posX -= 15;	// Horizontal movement (left)
			lifespan -= 15;	// Shortens life
		}
		else if (!flip)
		{
			posX += 15;	// Horizontal movement (right)
			lifespan -= 15;	// Shortens life
		}
		
		if (lifespan <= 0)	// Dies after lifespan drops to 0
		{
			kill = true;
		}
	}

	@Override
	public void draw(Graphics g)
	{
		if (!flip)	// Fireball moving right
			g.drawImage(fireball_img, posX - model.view.scrollVal, posY, null);
		else if (flip)	// Fireball moving left
			g.drawImage(fireball_img, posX - model.view.scrollVal + width, posY, - width, height, null);
	}

	@Override
	public void loadImg()
	{
		if (fireball_img == null)	// Loads image ONCE
		{
			try
			{
				fireball_img = ImageIO.read(new File("fireball.png"));
			}
			catch (Exception e)
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}

}














