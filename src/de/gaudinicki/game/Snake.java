package de.gaudinicki.game;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Snake extends JFrame{

	private static final long serialVersionUID = 458199775367903336L;

	public Snake()
	{
		add(new Game());
		
		setResizable(false);
		pack();
		
		setTitle("Snake");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable(){
			
			@Override
			public void run()
			{
				JFrame snake = new Snake();
				snake.setVisible(true);
			}
			
			
		});
	}

}
