package mtower;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Wall extends Obj{

	private Image image = new ImageIcon("images//item//wall.png").getImage();
	
	public Wall(int type, int x, int y) {
		super(type, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	void interact(Character c) {
		
	}

	@Override
	boolean pass() {
		return false;
	}

	@Override
	void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	@Override
	boolean exist() {
		return true;
	}
	
	Obj copy(int x, int y) {
		return new Wall (this.type,x,y);
	}

}
