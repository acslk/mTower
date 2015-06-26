package mtower;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class EditPanel extends JPanel {

	static final Font INFO_FONT = new Font("Verdana", Font.PLAIN, 15);

	private static Image back = new ImageIcon("images//level//1.png").getImage();
	private static Image fback = new ImageIcon("images//level//side4.png").getImage();
	private static Image edit_back = new ImageIcon("images//level//e.png").getImage();
	private static Image mon_u = new ImageIcon("images//others//mon.png").getImage();
	private static Image mon_s = new ImageIcon("images//others//monS.png").getImage();
	private static Image item_u = new ImageIcon("images//others//item.png").getImage();
	private static Image item_s = new ImageIcon("images//others//itemS.png").getImage();
	private static Image other_u = new ImageIcon("images//others//other.png")
			.getImage();
	private static Image other_s = new ImageIcon("images//others//otherS.png")
			.getImage();
	private static Image up = new ImageIcon("images//others//up.png").getImage();
	private static Image down = new ImageIcon("images//others//down.png").getImage();
	private static Image newF = new ImageIcon("images//others//new.png").getImage();
	private static Image empty = new ImageIcon("images//others//empty.png").getImage();

	ArrayList<Obj[][]> Tower;
	Obj[][] cf;
	int floor;
	Image mon, item, other;
	int cat;

	ObjList list;
	Obj selected;

	public EditPanel() {

		setPreferredSize(new Dimension(864, 416));
		setOpaque(true);
		Tower = new ArrayList<Obj[][]>();
		cf = new Obj[11][11];
		Tower.add(cf);
		floor = 0;

		for (int i = 0; i < Mon.model.length; i++)
			for (int j = 0; j < 4; j++)
				Mon.model[i][j] = new ImageIcon("images//mon//" + i + "_" + j + ".png")
						.getImage();

		mon = mon_u;
		item = item_u;
		other = other_u;
		list = new ObjList(13, 3);
		selected = null;

		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseMotionHandler());

	}

	public void select_cat(int n) {

		cat = n;
		if (cat == 1) {
			mon = mon_s;
			item = item_u;
			other = other_u;
			list.obj = list.monL;
		} else if (cat == 2) {
			mon = mon_u;
			item = item_s;
			other = other_u;
			list.obj = list.itemL;
		} else if (cat == 3) {
			mon = mon_u;
			item = item_u;
			other = other_s;
			list.obj = list.otherL;
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponents(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.drawImage(fback, 0, 0, null);
		g.drawImage(back, 192, 0, null);
		g.drawImage(edit_back, 576, 0, null);
		g.setFont(INFO_FONT);
		g.drawString("F " + floor, 70, 60);
		g.drawImage(mon, 580, 40, null);
		g.drawImage(item, 660, 40, null);
		g.drawImage(other, 740, 40, null);
		g.drawImage(up, 75, 100, null);
		g.drawImage(down, 75, 180, null);
		g.drawImage(newF, 75, 260, null);
		g.drawImage(empty, 660, 320, null);
		list.draw(g);
		for (int i = 0; i < 11; i++)
			for (int j = 0; j < 11; j++)
				if (cf[i][j] != null)
					cf[i][j].draw(g);

	}
	
	public void load(String name) throws FileNotFoundException {

		Scanner load = new Scanner(new File(name));
		int total_floor = load.nextInt();
		Tower = new ArrayList<>();
		floor = load.nextInt();
		Character c = new Character(load.nextInt(), load.nextInt(), load.nextInt(),
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

		cf = new Obj[11][11];
		while (f < total_floor) {

			int x = load.nextInt();
			if (x == -1) {
				f++;
				Tower.add(cf);
				cf = new Obj[11][11];
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
					cf[x][y] = new Passable(load.nextInt(), x, y);
				else if (t == 5)
					cf[x][y] = new Gate(load.nextInt(), x, y);
				else if (t == 6)
					cf[x][y] = new Npc(load.nextInt(), x, y);
			}

		}

		cf = Tower.get(floor);
		repaint(192, 32, 352, 352);
	}
	

	private class MouseHandler extends MouseAdapter {

		public void mousePressed(MouseEvent event) {
			Point p = event.getPoint();
			if (p.x > 590 && p.y > 50 && p.x < 650 && p.y < 70)
				select_cat(1);
			else if (p.x > 665 && p.y > 50 && p.x < 725 && p.y < 70)
				select_cat(2);
			else if (p.x > 745 && p.y > 50 && p.x < 820 && p.y < 70)
				select_cat(3);
			else if (p.x > 660 && p.y > 320 && p.x < 695 && p.y < 355)
				selected = null;
			
			else if (p.x > 75 && p.y > 100 && p.x < 110 && p.y < 135) {
				if (floor < Tower.size() - 1) {
					floor++;
					cf = Tower.get(floor);
				}
			} 
			
			else if (p.x > 75 && p.y > 180 && p.x < 110 && p.y < 215) {
				if (floor > 0) {
					floor--;
					cf = Tower.get(floor);
				}
			} 
			
			else if (p.x > 75 && p.y > 260 && p.x < 110 && p.y < 295) {
				cf = new Obj[11][11];
				Tower.add(floor + 1, cf);
				floor++;
			}
			
			else if (list.obj != null) {
				for (Obj i : list.obj) {
					if (i.contains(p)) {
						selected = i;
					}
				}
			}
			int x = Obj.convX(p.x);
			int y = Obj.convY(p.y);
			if (x >= 0 && x < 11 && y >= 0 && y < 11) {
				if (selected == null)
					cf[x][y] = null;
				else
					cf[x][y] = selected.copy(x, y);
			}

			repaint();

		}

		public void mouseReleased(MouseEvent event) {

		}
	}

	private class MouseMotionHandler implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent event) {
			Point p = event.getPoint();
			int x = Obj.convX(p.x);
			int y = Obj.convY(p.y);
			if (x >= 0 && x < 11 && y >= 0 && y < 11) {
				if (selected == null)
					cf[x][y] = null;
				else
					cf[x][y] = selected.copy(x, y);
			}
			repaint(192, 32, 352, 352);

		}

		@Override
		public void mouseMoved(MouseEvent event) {
			// TODO Auto-generated method stub

		}

	}

}
