package mtower;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Character extends Rectangle{
	
	
	static Image[][] model = {{new ImageIcon("images//char//u0.png").getImage(), new ImageIcon("images//char//u1.png").getImage(), new ImageIcon("images//char//u2.png").getImage(), new ImageIcon("images//char//u3.png").getImage()},
									{new ImageIcon("images//char//d0.png").getImage(), new ImageIcon("images//char//d1.png").getImage(), new ImageIcon("images//char//d2.png").getImage(), new ImageIcon("images//char//d3.png").getImage()},
									{new ImageIcon("images//char//l0.png").getImage(), new ImageIcon("images//char//l1.png").getImage(), new ImageIcon("images//char//l2.png").getImage(), new ImageIcon("images//char//l3.png").getImage()},
									{new ImageIcon("images//char//r0.png").getImage(), new ImageIcon("images//char//r1.png").getImage(), new ImageIcon("images//char//r2.png").getImage(), new ImageIcon("images//char//r3.png").getImage()}};
	
	int att, def, hp;
	int key[] = {0,0,0};
	int gold = 0, exp = 0;
	int level = 0;
	Image image = model[0][1];
	int dir, frame;
	int px,py;
	
	
	public Character (int hp, int att, int def, int x, int y) {
		
		super((x+6)*32,(y+1)*32,32,32);
		this.att = att;
		this.def = def;
		this.hp = hp;
		this.dir = 0;
		this.frame = 1;
		px = x;
		py = y;
		
	}
	
	public int calc(int type) {

		if (att <= Mon.def[type])
			return -1;
		if (def >= Mon.att[type])
			return 0;
		return (Mon.att[type] - def) * (Mon.hp[type] - 1) / (att - Mon.def[type]);

	}
	
	//1 - up, 2 - down, 3 - left, 4 - right
	public void dir (int d) {
		image = model[d-1][1];
		dir = d-1;
	}
	
	public void shuffle () {
		
		frame = (frame+1)%4;
		image = model[dir][frame];
		
	}
	
	void draw (Graphics g) {
		
		g.drawImage(image,x,y,null);
		
	}
 
}
