package de.gaudinicki.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DateiHandler {
	Scanner s;
	
	DateiHandler(File f)
	{
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int readHighscore(){
		if(s.hasNext())
		{
			int highscore = s.nextInt();
			
			return highscore;
		}
		return 0;
	}
	
	public void close()
	{
		s.close();
	}
}
