/*
Name: Jackson Bullard
Date: 2020.10.23
Assignment: HW #5
*/

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	private static final long serialVersionUID = -4029680288031259087L;	// Wanted to get rid of all warnings...
	
	Model model;
	Controller controller;
	View view;

	public Game()
	{
		// Creates necessary instances
		model = new Model();	// Order is important (model, controller, view)
		controller = new Controller(this, model);
		view = new View(controller, model);

		// Sets the environment
		this.setTitle("Goomba Mayhem!");
		this.setSize(1000, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		// Passes the controller object to be used
		view.addMouseListener(controller);
		this.addKeyListener(controller);
	}
	
	public void run()
	{
		// Game loop
		while(true)
		{
			controller.update();	// Updates controller
			model.update();		// Updates models
			view.repaint();		// Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync();		// Updates screen
			
			// Go to sleep for 40 milliseconds
			try
			{
				Thread.sleep(33);	// I sped the game up by ~5 frames per second. I hope that's ok.
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public void setModel(Model m)
	{
		model = m;
	}

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
}