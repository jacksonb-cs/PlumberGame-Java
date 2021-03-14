/*
Name: Jackson Bullard
Date: 2020.10.23
Assignment: HW #5
*/

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	Game game;
	View view;
	Model model;
	
	boolean keyLeft;
	boolean keyRight;
	boolean keySpace;
	boolean releaseSpace;
	boolean releaseControl;
	boolean keyS;
	boolean keyL;
	boolean keyE;
	
	boolean editorDisable;		// Disables the adding/removing of tubes and saving

	Controller(Game g, Model m)
	{
		keyLeft = false;
		keyRight = false;
		keySpace = false;
		releaseSpace = false;
		releaseControl = false;
		keyS = false;
		keyL = false;
		keyE = false;
		
		game = g;
		model = m;
		editorDisable = false;
		
		if (editorDisable)
			keyL = true;
	}
	
	// Detects if a key is being pressed
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT:
				keyRight = true;
				break;
			case KeyEvent.VK_LEFT:
				keyLeft = true;
				break;
			case KeyEvent.VK_SPACE:
				keySpace = true;
				break;
		}
	}

	// Detects if a key is no longer being pressed
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT:
				keyRight = false;
				break;
			case KeyEvent.VK_LEFT:
				keyLeft = false;
				break;
			case KeyEvent.VK_SPACE:
				keySpace = false;
				releaseSpace = true;
				break;
			case KeyEvent.VK_CONTROL:
				releaseControl = true;
				break;
			case KeyEvent.VK_S:
				keyS = true;
				break;
			case KeyEvent.VK_L:
				keyL = true;
				break;
			case KeyEvent.VK_E:
				keyE = true;
				break;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	void update()
	{
		if (keyRight && keyLeft);	// Intentionally does nothing! Prevents movement.
		
		else if (keyRight)
		{
			view.scrollVal += 10;
			model.mario.posX += 10;
			model.mario.moving = true;
			model.mario.flip = false;
		}
		else if (keyLeft)
		{
			view.scrollVal -= 10;
			model.mario.posX -= 10;
			model.mario.moving = true;
			model.mario.flip = true;
		}
		
		if (keySpace)
		{
			model.mario.jump();
		}
		
		if (releaseSpace)
		{
			model.mario.jumpCounterReset();
			
			releaseSpace = false;
		}
		
		if (releaseControl)
		{
			model.shootFire();
			
			releaseControl = false;
		}
		
		if (keyS)
		{
			if (!editorDisable)
				model.marshal().save("map.json");
			
			keyS = false;
		}
		else if (keyL)
		{
			model = new Model(Json.load("map.json"), view);
			
			view.setModel(model);	// Gives view the new model instance
			game.setModel(model);	// Gives game the new model instance
			
			view.scrollVal = 0;	// Resets screen scroll value b/c Mario's position is also reset (in Model)
			keyL = false;	// Resets keyL value
		}
		else if (keyE)
		{
			editorDisable = !editorDisable;
			keyE = false;
		}
	}

	public void actionPerformed(ActionEvent e)
	{
	}
	
	void setView(View v)
	{
		view = v;
	}
	
	public void mousePressed(MouseEvent e)
	{
		if (!editorDisable)
		{
			switch (e.getButton())
			{
			case MouseEvent.BUTTON1:	// Left mouse click adds tube
				model.tubeClick(e.getX() + view.scrollVal, e.getY());
				break;
			case MouseEvent.BUTTON3:	// Right mouse click adds goomba offset to the center of the cursor
				model.goombaClick(e.getX() + view.scrollVal - 25, e.getY() - 29);
				break;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
	}
	
	public void mouseEntered(MouseEvent e)
	{
	}
	
	public void mouseExited(MouseEvent e)
	{
	}
	
	public void mouseClicked(MouseEvent e)
	{
	}
}
















