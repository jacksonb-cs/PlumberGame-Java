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

public class Goomba extends Sprite
{
	double velY;	// Vertical velocity
	boolean flip;	// Determines direction of movement
	int ground;		// Tracks y value of ground as a function of x
	int prevX;		// Tracks the previous x position
	int prevY;		// Tracks the previous y position
	int deathTimer;
	boolean dying;
	boolean kill;
	
	Model model;	// Stores reference to current instance of model
	
	static BufferedImage[] goomba_img = null;	// Stores goomba and goomba on fire
	static int realGround = 337;	// 396 - height
	
	Goomba(int x, int y, Model m)
	{
		posX = x;
		posY = y;
		width = 50;
		height = 59;
		
		velY = 0;
		flip = false;
		ground = realGround;
		prevX = 0;
		prevY = 0;
		deathTimer = 25;
		kill = false;
		dying = false;
		
		model = m;
		
		loadImg();	// Loads both goomba images
	}
	
	Goomba(Json ob, Model m)
	{
		posX = (int)ob.getLong("x");
		posY = (int)ob.getLong("y");
		
		width = 50;
		height = 59;
		
		velY = 0;
		flip = false;
		ground = realGround;
		prevX = 0;
		prevY = 0;
		deathTimer = 25;
		kill = false;
		dying = false;
		
		model = m;
		
		loadImg();
	}
	
	@Override
	public boolean isGoomba()
	{
		return true;
	}
	
	@Override
	public void update()
	{
		// Loops through all sprites to check for collisions
		Iterator <Sprite> sprites = model.sprites.iterator();
		
		while (sprites.hasNext())
		{
			Sprite s = sprites.next();
			
			if (s.isTube())	// Only checks tubes
			{
				if (collidesWith(s))	// Checks for collision
				{
					goombaTubeCollide(s);
				}
			
				// Applies if this is the first tube in the list that Goomba is directly above
				if (ground == realGround)
				{
					if (!(posX + width < s.posX) && !(posX > s.posX + s.width)
							&& !(posY + height > s.posY))
					{
						ground = s.posY - height - 1;
					}
				}
				// Sets ground as the height of the highest tube that Goomba is directly above
				else if (!(posX + width < s.posX) && !(posX > s.posX + s.width)
							&& !(posY + height > s.posY) && s.posY < ground)
				{
					ground = s.posY - height - 1;
				}
			}
			else if (s.isFireball())
			{
				if (collidesWith(s) && posY < s.posY + height)
				{
					Fireball f = (Fireball)s;	// Removes fireball after contact
					f.kill = true;
					
					dying = true;
				}
			}
		}
		
		// Horizontal movement
		if (flip)
		{
			posX += 7;
		}
		else if (!flip)
		{
			posX -= 7;
		}

		// Vertical movement
		if ((posY >= ground) || (ground - posY < velY))
		{
			velY = 0.0;
			posY = ground;
		}
		else
		{
			velY += 1.2;
			
			// Keeps Goomba from clipping through ground
			if (ground - posY < velY)
			{
				velY = ground - posY;
			}
			
			posY += velY;
		}
		
		if (dying)
			deathTimer--;
		
		if (deathTimer <= 0)
			kill = true;
		
		prevX = posX;
		prevY = posY;
		ground = realGround;
	}

	@Override
	public void draw(Graphics g)
	{
		int i = dying ? 1 : 0;	// Converts boolean to integer in order to display correct dying/not dying goomba picture
		
		if (!flip)	// Goomba moving left
			g.drawImage(goomba_img[i], posX - model.view.scrollVal, posY, width, height, null);
		else if (flip)	// Goomba moving right
			g.drawImage(goomba_img[i], posX - model.view.scrollVal + width, posY, - width, height, null);
	}

	@Override
	public void loadImg()
	{
		if (goomba_img == null)	// Loads images ONCE
		{
			try
			{
				goomba_img = new BufferedImage[2];
				goomba_img[0] = ImageIO.read(new File("goomba.png"));
				goomba_img[1] = ImageIO.read(new File("goomba_fire.png"));
			}
			catch (Exception e)
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}
	
	public void goombaTubeCollide(Sprite s)
	{
		if (prevY + height - 1 < s.posY)	// Goomba comes from above
		{
			posY = s.posY - height - 1;
		}
		else if (prevX + 7 > s.posX + s.width)	// Goomba comes from right
		{
			posX = s.posX + s.width + 7;
			flip = !flip;	// Flips Goomba's movement
		}
		else if (prevX < s.posX)	// Goomba comes from left
		{
			posX = s.posX - width - 7;
			flip = !flip;	// Flips Goomba's movement
		}
	}
	
	public Json marshal()
	{
		Json ob = Json.newObject();
		
		ob.add("x", posX);
		ob.add("y", posY);
		
		return ob;
	}
}














