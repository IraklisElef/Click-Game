package main;

import javax.swing.JFrame;

public class Window{

	public Window(String title, Game game) {
		
		JFrame jf = new JFrame(title);
		
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		jf.setUndecorated(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(game);
		jf.setVisible(true);
		
		game.start();
		
	}

	
}
