package mtower;

import java.awt.Graphics;
import java.awt.Image;

public class Mon extends Obj {

	static int[] att = { 20, 15, 20, 25, 35, 40, 50, 65, 75, 90, 115, 120, 150,
			160, 200, 250, 300, 350, 400, 450, 500, 560, 620, 680, 750, 830,
			900, 980, 1150, 1000 };
	static int[] def = { 0, 2, 5, 5, 10, 20, 25, 30, 45, 50, 65, 70, 90, 90,
			110, 125, 150, 200, 260, 330, 400, 460, 520, 590, 650, 730, 850,
			900, 1050, 1000 };
	static int[] hp = { 50, 70, 100, 110, 200, 150, 125, 150, 300, 400, 500,
			250, 450, 550, 100, 700, 1300, 850, 500, 900, 1250, 1500, 1200,
			2000, 900, 1500, 2500, 1200, 3100, 15000 };
	static int[] gold = { 1, 2, 3, 5, 5, 8, 10, 10, 13, 15, 15, 17, 19, 20, 25,
			30, 35, 40, 45, 50, 55, 60, 75, 65, 70, 70, 75, 75, 80, 100 };
	static int[] exp = { 1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 15, 17, 19, 20, 25,
			30, 35, 40, 45, 50, 55, 60, 75, 65, 70, 70, 75, 75, 80, 100 };
	static Image[][] model = new Image[30][4];

	boolean canPass = false;
	boolean alive = true;
	static int frame = 0;

	public Mon(int type, int x, int y) {

		super(type, x, y);

	}

	public void special(Character c) {

	}

	public void interact(Character c) {

		int damage = c.calc(type);
		if (damage >= 0 && damage < c.hp) {
			canPass = true;
			alive = false;
			c.hp -= damage;
			c.gold += gold[type];
			c.exp += exp[type];
			special(c);
		}

	}

	@Override
	void draw(Graphics g) {
		g.drawImage(model[type][frame], x, y, null);

	}

	@Override
	boolean pass() {
		return canPass;
	}

	@Override
	boolean exist() {
		return alive;
	}
	
	Obj copy(int x, int y) {
		return new Mon (this.type,x,y);
	}

}
