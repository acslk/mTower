package mtower;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Npc extends Obj {

	private static Image[] image = { new ImageIcon("images//char//1.png").getImage(),
			new ImageIcon("images//char//4.png").getImage(), new ImageIcon("images//char//2.png").getImage() };
	GamePanel game;

	public Npc(int type, int x, int y, GamePanel game) {
		super(type, x, y);
		this.game = game;
	}

	public Npc(int type, int x, int y) {
		super(type, x, y);
	}

	@Override
	void interact(Character c) {

		if (type == 0) {
			game.mode = 1;
			game.shop_n = 0;
			game.op_n = 4;
			game.option = 0;
			game.opname[0] = "+800 hp (25 gold)";
			game.opname[1] = "+4 att (25 gold)";
			game.opname[2] = "+4 def (25 gold)";
			game.opname[3] = "leave";
		}

		else if (type == 1) {
			game.mode = 1;
			game.shop_n = 1;
			game.op_n = 4;
			game.option = 0;
			game.opname[0] = "+1000hp hp (30 exp)";
			game.opname[1] = "+5 att (30 exp)";
			game.opname[2] = "+5 def (30 exp)";
			game.opname[3] = "leave";
		}

	}

	static void exchange(int type, int option, GamePanel game) {

		if (type == 0) {

			if (option == 0 && game.c.gold >= 25) {
				game.c.hp += 800;
				game.c.gold -= 25;
			} else if (option == 1 && game.c.gold >= 25) {
				game.c.att += 4;
				game.c.gold -= 25;
			} else if (option == 2 && game.c.gold >= 25) {
				game.c.def += 4;
				game.c.gold -= 25;
			} else if (option == 3) {
				game.mode = 0;
			}

		}

		else if (type == 1) {

			if (option == 0 && game.c.exp >= 30) {
				game.c.hp += 1000;
				game.c.exp -= 30;
			} else if (option == 1 && game.c.exp >= 30) {
				game.c.att += 5;
				game.c.exp -= 30;
			} else if (option == 2 && game.c.exp >= 30) {
				game.c.def += 5;
				game.c.exp -= 30;
			} else if (option == 3) {
				game.mode = 0;
			}

		}

	}

	@Override
	boolean pass() {
		return false;
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
		return new Npc (this.type,x,y);
	}

}
