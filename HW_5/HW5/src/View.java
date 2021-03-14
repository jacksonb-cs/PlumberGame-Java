/*
Name: Jackson Bullard
Date: 2020.10.23
Assignment: HW #5
*/

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

class View extends JPanel
{
	private static final long serialVersionUID = 3358444104426080917L;	// Wanted to get rid of all warnings...
	
	Model model;
	int scrollVal;

	View(Controller c, Model m)
	{
		model = m;				// Saves the Model that is passed in
		model.setView(this);	// Sets this View in Model
		c.setView(this);		// Sets this View in Controller
		scrollVal = 0;
	}
	
	public void setModel(Model m)
	{
		model = m;
	}
	
	// Paints the background
	public void paintComponent(Graphics g)
	{
		// Draw background
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0,  0,  this.getWidth(), this.getHeight());
		
		// Draw ground
		g.setColor(Color.gray);
		g.fillRect(0,  396,  1920,  1000);
				
		for (int i = 0; i < model.sprites.size(); i++)
		{
			model.sprites.get(i).draw(g);
		}
	}
}






















