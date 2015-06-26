package mtower;

import javax.swing.*;

public class GameMain extends JFrame{
	
	GamePanel game;
	
	public GameMain() {
		
		super("mTower");
		setResizable(false);
		setLocation(500,300);
		game = new GamePanel();
		add(game);
		
		setIconImage (new ImageIcon ("images//level//icon.png").getImage ());
		
	}

	
	public static void main (String[] args) {
		
		GameMain frame = new GameMain();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	
}
