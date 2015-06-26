package mtower;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Passable extends Obj{
	
	private static Image[] image = {new ImageIcon("images//item//up.png").getImage(),new ImageIcon("images//item//down.png").getImage()};
	GamePanel game;

	public Passable(int type, int x, int y, GamePanel game) {
		super(type, x, y);
		this.game = game;
	}
	
	public Passable (int type, int x, int y) {
		super(type, x, y);
	}
	
	void interact(Character c) {
		
		if (type == 0) {
			game.floor++;
		}
		else if (type == 1) {
			game.floor--;
		}
		game.cf = game.tower[game.floor];

		
	}

	@Override
	boolean pass() {
		return true;
	}

	@Override
	boolean exist() {
		return true;
	}

	@Override
	void draw(Graphics g) {
		g.drawImage(image[type], x, y, 32, 32, null);
		
	}
	
	Obj copy(int x, int y) {
		return new Passable (this.type,x,y);
	}

}
