package Main;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Dragon Tale");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Tells Java to completely close the program one the red x is pressed
		window.setResizable(false);
		//Tells Java to disable the possibility to Resize the window (False = boolea
		window.pack(); 
		//Tells Java to make the screen size the recommended size determined by the computer screen.
		window.setVisible(true);
		//Tells Java to either hide or show the window.
		
	}
	
}
