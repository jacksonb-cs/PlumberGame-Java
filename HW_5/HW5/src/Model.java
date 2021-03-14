/*
Name: Jackson Bullard
Date: 2020.10.23
Assignment: HW #5
*/

import java.util.ArrayList;
import java.util.Iterator;

class Model
{
	ArrayList<Sprite> sprites;
	Mario mario;
	View view;

	Model()
	{
		sprites = new ArrayList<Sprite>();
		mario = new Mario(this);
		sprites.add(mario);
		view = null;
	}
	
	// New model created after loading map.json
	Model(Json ob, View v)
	{
		view = v;

		sprites = new ArrayList<Sprite>();
		Json tubeList = ob.get("tubes");
		Json goombaList = ob.get("goombas");
		
		mario = new Mario(this);		// Starts Mario on the ground
		sprites.add(mario);
		
		for (int i = 0; i < tubeList.size(); i++)
		{
			sprites.add(new Tube(tubeList.get(i), this));
		}
		
		for (int i = 0; i < goombaList.size(); i++)
		{
			sprites.add(new Goomba(goombaList.get(i), this));
		}
	}
	
	// Updates all sprites
	public void update()
	{
		Iterator<Sprite> allSprites = sprites.iterator();
		
		while (allSprites.hasNext())
		{
			Sprite s = allSprites.next();
			
			s.update();
			
			if (s.isGoomba())
			{
				Goomba enemy = (Goomba)s;
				if (enemy.kill)
				{
					allSprites.remove();
				}
			}
			else if (s.isFireball())
			{
				Fireball f = (Fireball)s;
				if (f.kill)
				{
					allSprites.remove();
				}
			}
		}
	}

	public void tubeClick(int x, int y)
	{
		boolean tubeRemoved = false;	// Tracks whether tube was removed
		
		Iterator<Sprite> allSprites = sprites.iterator();
		
		while (allSprites.hasNext())
		{
			Sprite s = allSprites.next();
			
			if (s.isTube())
			{
				Tube t = (Tube)s;			// Stores current sprite in tube variable for method access
				if (t.clickedOnTube(x, y))	// Checks if tube was clicked on
				{
					allSprites.remove();
					tubeRemoved = true;
				}
			}
		}
		
		if (!tubeRemoved && y <= 395)	// Restricts tubes to above ground
		{
			Tube t = new Tube(x, y, this);
			sprites.add(t);
		}
	}
	
	public void goombaClick(int x, int y)
	{
		if (y <= 336)	// Must click above ground - goomba height (59 pixels)
		{
			Goomba enemy = new Goomba(x, y, this);
			sprites.add(enemy);
		}
	}
	
	public void shootFire()
	{
		Fireball f = new Fireball(mario.posX + 20, mario.posY + 30, mario.flip, this);
		sprites.add(f);
	}
	
	public Json marshal()
	{
		Json ob = Json.newObject();
		Json tubeList = Json.newList();
		Json goombaList = Json.newList();
		
		ob.add("tubes", tubeList);
		ob.add("goombas", goombaList);
		
		Iterator<Sprite> allSprites = sprites.iterator();
		
		while (allSprites.hasNext())
		{
			Sprite s = allSprites.next();
			
			if (s.isTube())
			{
				Tube t = (Tube)s;
				tubeList.add(t.marshal());
			}
			else if (s.isGoomba())
			{
				Goomba enemy = (Goomba)s;
				goombaList.add(enemy.marshal());
			}
		}

		return ob;
	}
	
	public void setView(View v)
	{
		view = v;
	}
}






















