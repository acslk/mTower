package mtower;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	static final Font INFO_FONT = new Font("Verdana", Font.PLAIN, 15);
	static final Font OP_FONT = new Font("David", Font.BOLD, 16);
	static final Font MON_FONT = new Font("Calibri", Font.PLAIN, 16);

	private static Image back = new ImageIcon("images//level//1.png").getImage();
	private static Image back_x = new ImageIcon("images//level//back1.png").getImage();
	private static Image stat = new ImageIcon("images//level//side4.png").getImage();
	private static Image shop = new ImageIcon("images//level//option2.png").getImage();
	private static Image cursor_shop = new ImageIcon("images//level//choose1.png")
			.getImage();
	private static Image cursor_menu = new ImageIcon("images//level//choose.png")
			.getImage();

	// contains information of the entire tower
	public Obj[][][] tower;

	// current floor
	public Obj[][] cf;
	public int floor;
	public int mode, shop_n, option, op_n;
	public boolean[] monList;
	String[] opname;
	Character c;

	ExecutorService executor = Executors.newSingleThreadExecutor();
	Runnable backgroundAnimation = new Runnable() {
		@Override
		public void run() {
			while (true) {
				Mon.frame = (Mon.frame + 1) % 4;
				GamePanel.delay(400);
				repaint(192, 32, 352, 352);
			}

		}
	};

	public GamePanel() {

		setPreferredSize(new Dimension(576, 416));
		setOpaque(true);

		mode = 0;
		opname = new String[4];
		monList = new boolean[Mon.hp.length];
		tower = new Obj[1][11][11];

		for (int i = 0; i < Mon.model.length; i++)
			for (int j = 0; j < 4; j++)
				Mon.model[i][j] = new ImageIcon("images//mon//" + i + "_" + j + ".png")
						.getImage();

		try {
			load("map.map");
		} catch (FileNotFoundException e) {
		}

		executor.execute(backgroundAnimation);

		GameKeyBindings input = new GameKeyBindings(this);

	}

	public void paint(Graphics g) {

		super.paintComponents(g);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		if (mode < 2) {

			g.drawImage(back, 192, 0, null);
			for (int i = 0; i < 11; i++)
				for (int j = 0; j < 11; j++)
					if (cf[i][j] != null)
						cf[i][j].draw(g);
			c.draw(g);

			if (mode == 1) {

				g.setFont(OP_FONT);
				g.drawImage(shop, 288, 160, null);
				if (op_n == 4) {
					g.drawString(opname[0], 315, 180);
					g.drawString(opname[1], 315, 202);
					g.drawString(opname[2], 315, 224);
					g.drawString(opname[3], 315, 246);
					g.drawImage(cursor_shop, 285, 160 + 20 * option, null);
				}

			}

		}

		else if (mode == 2) {

			g.setFont(INFO_FONT);
			g.drawImage(back_x, 192, 32, null);
			g.drawImage(cursor_menu, 225 + 80 * option, 350, null);
			g.drawString("Info          Save         Load", 250, 370);

			int count = 0;

			g.setFont(MON_FONT);
			for (int i = 0; i < monList.length; i++) {
				//TODO add extra pages for additional monsters
				if (monList[i] && count < 6) {
					int dmg = c.calc(i);
					g.drawImage(Mon.model[i][Mon.frame], 215, 40 + count * 50,
							null);
					g.drawString("hp: " + Mon.hp[i], 270, 55 + count * 50);
					g.drawString("att: " + Mon.att[i], 360, 55 + count * 50);
					g.drawString("def: " + Mon.def[i], 450, 55 + count * 50);
					g.drawString("gold: " + Mon.gold[i], 270, 70 + count * 50);
					g.drawString("exp: " + Mon.exp[i], 360, 70 + count * 50);
					if (dmg < 0 || dmg > 999999)
						g.drawString("dmg: ????", 450, 70 + count * 50);
					else
						g.drawString("dmg: " + dmg, 450, 70 + count * 50);
					count++;
				}

			}

		}

		g.drawImage(stat, 0, 0, null);
		g.setFont(INFO_FONT);
		g.drawString("F " + floor, 60, 60);
		g.drawImage(Character.model[1][1], 110, 40, null);
		g.drawString("hp: " + c.hp, 40, 100);
		g.drawString("att: " + c.att, 40, 130);
		g.drawString("def: " + c.def, 40, 160);
		g.drawString("gold: " + c.gold, 40, 190);
		g.drawString("exp: " + c.exp, 40, 220);
		g.drawImage(Item.model[7], 40, 250, 32, 32, null);
		g.drawImage(Item.model[8], 40, 280, 32, 32, null);
		g.drawImage(Item.model[9], 40, 310, 32, 32, null);
		g.drawString(Integer.toString(c.key[0]), 90, 275);
		g.drawString(Integer.toString(c.key[1]), 90, 305);
		g.drawString(Integer.toString(c.key[2]), 90, 335);

	}

	public static void save(String name, Obj[][][] Tower, Character c, int floor)
			throws IOException {

		File save = new File(name);
		FileWriter out;
		out = new FileWriter(save);
		out.write(Tower.length + " " + floor + "\n");
		out.write(c.hp + " " + c.att + " " + c.def + " " + c.px + " " + c.py
				+ " " + c.gold + " " + c.exp + " " + c.key[0] + " " + c.key[1]
				+ " " + c.key[2] + "\n");
		for (int i = 0; i < Mon.hp.length; i++)
			out.write(Mon.hp[i] + " " + Mon.att[i] + " " + Mon.def[i] + " "
					+ Mon.gold[i] + " " + Mon.exp[i] + "\n");
		for (int i = 0; i < Item.amount.length; i++)
			out.write(Item.amount[i] + " ");
		out.write("\n");
		for (int i = 0; i < Tower.length; i++) {
			for (int j = 0; j < 11; j++) {
				for (int k = 0; k < 11; k++) {
					if (Tower[i][j][k] != null)
						out.write(Tower[i][j][k].toString());
				}
			}
			out.write(-1 + "\n");
		}

		out.close();

	}

	public void load(String name) throws FileNotFoundException {

		Scanner load = new Scanner(new File(name));
		tower = new Obj[load.nextInt()][11][11];
		floor = load.nextInt();
		c = new Character(load.nextInt(), load.nextInt(), load.nextInt(),
				load.nextInt(), load.nextInt());
		c.gold = load.nextInt();
		c.exp = load.nextInt();
		c.key[0] = load.nextInt();
		c.key[1] = load.nextInt();
		c.key[2] = load.nextInt();

		for (int i = 0; i < Mon.hp.length; i++) {
			Mon.hp[i] = load.nextInt();
			Mon.att[i] = load.nextInt();
			Mon.def[i] = load.nextInt();
			Mon.gold[i] = load.nextInt();
			Mon.exp[i] = load.nextInt();
		}
		for (int i = 0; i < Item.amount.length; i++)
			Item.amount[i] = load.nextInt();
		int f = 0;
		cf = tower[f];
		while (f < tower.length) {

			int x = load.nextInt();
			if (x == -1) {
				f++;
				if (f < tower.length)
					cf = tower[f];
			} else {
				int y = load.nextInt();
				int t = load.nextInt();
				if (t == 1)
					cf[x][y] = new Wall(load.nextInt(), x, y);
				else if (t == 2)
					cf[x][y] = new Mon(load.nextInt(), x, y);
				else if (t == 3)
					cf[x][y] = new Item(load.nextInt(), x, y);
				else if (t == 4)
					cf[x][y] = new Passable(load.nextInt(), x, y, this);
				else if (t == 5)
					cf[x][y] = new Gate(load.nextInt(), x, y, this);
				else if (t == 6)
					cf[x][y] = new Npc(load.nextInt(), x, y, this);
			}

		}

		cf = tower[floor];

	}

	public static void delay(int msec) {
		try {
			Thread.sleep(msec);
		} catch (Exception e) {
		}
	}

}
