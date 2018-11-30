package de.gaudinicki.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Button;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener{

	private static final long serialVersionUID = -2826962172477400814L;
	
	private int width = 500;
	private int height = 500;
	
	private Image apple;
	private Image head;
	private Image tail;
	
	private int tail_amount = 3;
	private int snake_width = 10;
	private int snake_x[] = new int[width*height/(snake_width*snake_width)];
	private int snake_y[] = new int[width*height/(snake_width*snake_width)];
	
	private int apple_x;
	private int apple_y;
	private boolean running;

	private int score = 0;
	
	private Timer t;
	
	/* 0 = left; 1 = right; 2 = up; 3 = down*/
	public static int direction;
	
	public Game()
	{
		addKeyListener(new SnakeListener());
		setPreferredSize(new Dimension(width,height));
		setFocusable(true);
		setBackground(Color.lightGray);
		
		ImageIcon icon_apple = new ImageIcon(getClass().getClassLoader().getResource("apple.png"));
		ImageIcon icon_head = new ImageIcon(getClass().getClassLoader().getResource("head.png"));
		ImageIcon icon_tail = new ImageIcon(getClass().getClassLoader().getResource("tail.png"));
		
		apple = icon_apple.getImage();
		head = icon_head.getImage();
		tail = icon_tail.getImage();
		
		for(int i=0; i < tail_amount;i++)
		{
			snake_x[i] = 100 - i*10;
			snake_y[i] = 50;
		}
		running = true;
		
		t = new Timer(250, this);
		t.start();
		
		spawn_apple();
	}
	
	private void spawn_apple() {
		int random = (int)(Math.random()*29);
		apple_x = random * snake_width;
		random = (int)(Math.random()*29);
		apple_y = random * snake_width;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running)
		{
			check_apple();
			check_death();
			move_snake();
		}
		repaint();
	}


	private void move_snake() {
		for(int i = tail_amount; i > 0; i--)
		{
			snake_x[i] = snake_x[i-1];
			snake_y[i] = snake_y[i-1];
		}
		
		switch(tail_amount)
		{
		case 1:
			t = new Timer(250,this);
			t.start();
		case 5:
			t.stop();
			t = new Timer(225,this);
			t.start();
			break;
		case 10:
			t.stop();
			t = new Timer(200,this);
			t.start();
			break;
		case 15:
			t.stop();
			t = new Timer(175,this);
			t.start();
			break;
		case 20:
			t.stop();
			t = new Timer(150,this);
			t.start();
			break;
		case 25:
			t.stop();
			t = new Timer(125,this);
			t.start();
			break;
		case 30:
			t.stop();
			t = new Timer(100,this);
			t.start();
			break;
		case 35:
			t.stop();
			t = new Timer(75,this);
			t.start();
			break;
		case 40:
			t.stop();
			t = new Timer(65,this);
			t.start();
			break;
		case 45:
			t.stop();
			t = new Timer(55,this);
			t.start();
			break;
		case 50:
			t.stop();
			t = new Timer(45,this);
			t.start();
			break;
		default:
			break;
		
		}
		
		/* 0 = left; 1 = right; 2 = up; 3 = down*/
		switch(direction)
		{
		case 0:
			snake_x[0] -= snake_width;
			break;
		case 1:
			snake_x[0] += snake_width;
			break;
		case 2:
			snake_y[0] -= snake_width;
			break;
		case 3:
			snake_y[0] += snake_width;
		default:
			break;
		}
		
	}

	private void check_death() {
		for(int i=tail_amount; i > 3;i--)
		{
			if(snake_x[0] == snake_x[i] && snake_y[0] == snake_y[i])
			{
				running = false;
			}
		}
		if(snake_y[0] >= height || snake_x[0] >= width || snake_y[0] < 0 || snake_x[0] < 0)
		{
			running = false;
		}
		if(!running)
		{
			t.stop();
		}
	}

	private void check_apple() {
		if(snake_x[0] == apple_x && snake_y[0] == apple_y)
		{
			tail_amount++;
			spawn_apple();
		}
		
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		if(running)
		{
			Font f = new Font("Calibri", Font.BOLD, 16);
			
			g.setColor(Color.WHITE);
			g.setFont(f);
			g.drawString("Score: " + (tail_amount-3), 10,20);
			if((tail_amount-3) > getHighscore())
			{
				g.drawString("Highscore: " + (tail_amount-3), 390, 20);
			}
			else{
				g.drawString("Highscore: " + getHighscore(), 390, 20);
			}
			g.drawImage(apple, apple_x, apple_y, this);
			
			for(int i = 1; i < tail_amount;i++)
			{
				g.drawImage(tail, snake_x[i], snake_y[i], this);
			}
			
			g.drawImage(head, snake_x[0], snake_y[0], this);
			
			Toolkit.getDefaultToolkit().sync();
		}
		else
		{
			Button btnResHigh = new Button("reset Highscore");
			score = (tail_amount-3);
			
			Font f = new Font("Calibri", Font.BOLD,16);
			
			g.setColor(Color.WHITE);
			g.setFont(f);
			g.drawString("Game Over - You died, noob!", 50,height/3);
			g.drawString("Score: " + score,50,208);
			
			btnResHigh.setBackground(Color.BLACK);
			btnResHigh.setForeground(Color.WHITE);
			btnResHigh.addActionListener(new TestActionListener());
			add(btnResHigh);
			
			if((tail_amount-3) > getHighscore())
			{
				g.drawString("Highscore: " + (tail_amount-3), 50, height/2);
				g.drawString("Herzlichen Glückwunsch!",50,50);
				g.drawString("Du hast einen neuen Highscore errungen!",50,70);
			}
			else{
				g.drawString("Highscore: " + getHighscore(), 50, height/2);
			}
			if(score > getHighscore())
				setNewHighscore(score);
		}
	}
	
	public void setNewHighscore(int newHigh) 
	{	
		 String dateiInhalt = "";
		 File f = new File(System.getProperty("user.home") + "\\highscore.txt");
		 Formatter x;
		 if(f.exists())
		 {
			 try {
				Scanner s = new Scanner(f);
				while(s.hasNext())
				{
					dateiInhalt += s.next();
				}
				x = new Formatter(f);
				if(Integer.parseInt(dateiInhalt) < newHigh)
				{
					String sNewHigh = String.valueOf(newHigh);
					x.format("%s", sNewHigh);
				}
				else{
					x.format("%s", dateiInhalt);
				}
				
				x.close();
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 else {
			 //Datei erstellen und neuen Highscore eintragen
			try {
				f.createNewFile();
				x = new Formatter(f);
				x.format("%s", newHigh);
			} 
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		 }
	}
	
	public int getHighscore()
	{
		File f = new File(System.getProperty("user.home") + "\\highscore.txt");
		
		if(f.exists()) {
			DateiHandler dh = new DateiHandler(f);
			int highscore = dh.readHighscore();
			dh.close();
			
			return highscore;
		}
		else {
			return 0;
		}
	
	}
}

class TestActionListener implements ActionListener {
	public void actionPerformed(ActionEvent e){
		System.out.println("Der Resetbutton wurde gedrückt!");
	}
}
