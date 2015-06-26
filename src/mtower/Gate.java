package mtower;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Gate extends Obj {

	private static Image[][] image = {
			{ new ImageIcon("images//item//d1_0.png").getImage(),
					new ImageIcon("images//item//d1_1.png").getImage(),
					new ImageIcon("images//item//d1_2.png").getImage() },
			{ new ImageIcon("images//item//d2_0.png").getImage(),
					new ImageIcon("images//item//d2_1.png").getImage(),
					new ImageIcon("images//item//d2_2.png").getImage() },
			{ new ImageIcon("images//item//d3_0.png").getImage(),
					new ImageIcon("images//item//d3_1.png").getImage(),
					new ImageIcon("images//item//d3_2.png").getImage() } };

	GamePanel game;
	boolean canPass = false;
	boolean exist = true;
	int frame = 0;

	public Gate(int type, int x, int y, GamePanel game) {
		super(type, x, y);
		this.game = game;
	}

	public Gate (int type, int x, int y) {
		super(type, x, y);
	}

	@Override
	void interact(Character c) {

		if (c.key[type] > 0) {
			canPass = true;
			exist = false;
			c.key[type]--;

			for (int i = 0; i < 2; i++) {
				frame++;
				GamePanel.delay(50);
				game.paintImmediately(x, y, 32, 32);
			}

		}

	}

	@Override
	boolean pass() {
		return canPass;
	}

	@Override
	boolean exist() {
		return exist;
	}

	@Override
	void draw(Graphics g) {
		g.drawImage(image[type][frame], x, y, 32, 32, null);

	}

	@Override
	Obj copy(int x, int y) {
		return new Gate (this.type,x,y);
	}

}
