package mtower;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Item extends Obj {
	
	static int amount[] = {150,400,1000,3,10};
	static Image[] model = {new ImageIcon("images//item//p1.png").getImage(), new ImageIcon("images//item//p2.png").getImage(), new ImageIcon("images//item//p3.png").getImage(),
									new ImageIcon("images//item//a1.png").getImage(), new ImageIcon("images//item//a1.png").getImage(), new ImageIcon("images//item//d1.png").getImage(), new ImageIcon("mon\\d1.png").getImage(),
									new ImageIcon("images//item//k1.png").getImage(), new ImageIcon("images//item//k2.png").getImage(),new ImageIcon("images//item//k3.png").getImage()};
	
	Image image;
	
	public Item (int type, int x, int y) {
		
		super(type,x,y);
		this.image = model[type];
		
	}
	
	void interact(Character c) {
		
		if (type < 3)
			c.hp += amount[type];
		else if (type < 5)
			c.att += amount[type];
		else if (type < 7)
			c.def += amount[type-2];
		else if (type < 10)
			c.key[type - 7]++;
		
	}

	@Override
	void draw(Graphics g) {
		g.drawImage(image, x, y, null);

	}

	@Override
	boolean pass() {
		return true;
	}

	@Override
	boolean exist() {
		return false;
	}
	
	Obj copy(int x, int y) {
		return new Item (this.type,x,y);
	}

}