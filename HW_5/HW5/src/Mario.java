/*
Name: Jackson Bullard
Date: 2020.10.23
Assignment: HW #5
*/

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import javax.imageio.ImageIO;

public class Mario extends Sprite
{
	double velY;		// y velocity
	int state;			// Animation frame
	int jumpCounter;	// Timer between jumps
	boolean jumping;	// Tracks whether Mario is jumping
	int ground;			// Keeps track of the most downward position Mario can be regarding his posX
	boolean moving;		// Tracks whether Mario is moving
	boolean flip;		// Flips Mario's image while running left
	int prevX;			// Stores previous x position
	int prevY;			// Stores previous y position
	int offset;			// Offset to keep view on Mario appropriately
	
	Model model;
	static BufferedImage[] mario_img = null;	// Arrary that keeps track of 5 Mario images for animation
	static int realGround = 300;	// The actual ground height, unaltered by tubes
	
	Mario(Model m)
	{
		ground = realGround;
		offset = 75;
		posX = offset;
		posY = ground;
		velY = 0;
		state = 0;
		moving = false;
		flip = false;
		prevX = posX;
		prevY = posY;
		
		jumpCounter = 0;
		jumping = false;
		
		width = 60;
		height = 95;
		
		model = m;
		loadImg();	// Attempts to load Mario images
	}
	
	@Override
	public void loadImg()
	{
		if (mario_img == null)	// Loads images ONCE
		{
			try
			{
				mario_img = new BufferedImage[5];
				mario_img[0] = ImageIO.read(new File("mario1.png"));
				mario_img[1] = ImageIO.read(new File("mario2.png"));
				mario_img[2] = ImageIO.read(new File("mario3.png"));
				mario_img[3] = ImageIO.read(new File("mario4.png"));
				mario_img[4] = ImageIO.read(new File("mario5.png"));
			}
			catch (Exception e)
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}
	
	public void jump()
	{
		if (jumpCounter > 4)
		{
			jumping = true;
			velY = -15;
			posY += velY;
		}
	}
	
	public void jumpCounterReset()
	{
		jumpCounter = 0;
		jumping = false;
	}
	
	@Override
	public boolean isMario()
	{
		return true;
	}
	
	@Override
	public void draw(Graphics g)
	{
		if (!flip)
			g.drawImage(mario_img[state], posX - model.view.scrollVal, posY, null);	// Running to the right
		else if (flip)
			g.drawImage(mario_img[state], posX - model.view.scrollVal + width, posY, - width, height, null);	// Running to the left
	}
	
	// Rectifies collisions between Mario and a Tube
	public void fixTubeCollide(Sprite s)
	{
		if (prevY + height - 1 < s.posY)	// Mario comes from above
		{
			posY = s.posY - height - 1;
		}
		else if (prevX > s.posX + s.width)	// Mario comes from right
		{
			posX = s.posX + s.width + 1;
		}
		else if (prevX < s.posX)	// Mario comes from left
		{
			posX = s.posX - width - 1;
		}
	}
	
	@Override
	public void update()
	{
		// Loops through all sprites to check for collisions
		Iterator<Sprite> sprites = model.sprites.iterator();
		
		while (sprites.hasNext())
		{
			Sprite s = sprites.next();
			
			if (s.isTube())	// Only checks tubes
			{
				if (collidesWith(s))	// Checks for collision
				{
					fixTubeCollide(s);	// Rectifies collision
				}
				
				// Applies if this is the first tube in the list that Mario is directly above
				if (ground == realGround)
				{
					if (!(posX + width < s.posX) && !(posX > s.posX + s.width)
							&& !(posY + height > s.posY))
					{
						ground = s.posY - height - 1;
					}
				}
				// Sets ground as the height of the highest tube that Mario is directly above
				else if (!(posX + width < s.posX) && !(posX > s.posX + s.width)
							&& !(posY + height > s.posY) && s.posY < ground)
				{
					ground = s.posY - height - 1;
				}
			}
		}
		
		if ((posY >= ground) || (ground - posY < velY))
		{
			velY = 0.0;
			posY = ground;
			
			if (jumpCounter < 5)
			{
				jumpCounter++;
			}
		}
		else if(!jumping)
		{
			velY += 1.2;
			jumpCounter = 0;
			
			// Keeps Mario from clipping through ground
			if (ground - posY < velY)
			{
				velY = ground - posY;
			}
			
			posY += velY;
		}
		
		if (moving)
		{
			state++;	// Increments Mario's animation state
			state %= 5;	// Keeps animation state between 0 to 4
		}
		
		model.view.scrollVal = posX - offset;
		
		prevX = posX;	// Saves Mario's previous x coordinate
		prevY = posY;	// Saves Mario's previous y coordinate
		ground = realGround;	// Resets the value for the ground
		moving = false;	// Resets moving value every frame
	}
}



















