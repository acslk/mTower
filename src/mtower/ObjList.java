package mtower;

import java.awt.Graphics;

public class ObjList {

	static Obj[] monL, itemL, otherL;

	Obj[] obj;

	public ObjList(int x, int y) {

		monL = new Obj[Mon.hp.length];
		for (int i = 0; i <= (monL.length - 1) / 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (i * 6 + j < monL.length)
					monL[i * 6 + j] = new Mon(i * 6 + j, x+j, y+i);
			}
		}

		itemL = new Obj[Item.model.length];
		for (int i = 0; i <= (itemL.length - 1) / 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (i * 6 + j < itemL.length)
					itemL[i * 6 + j] = new Item(i * 6 + j, x+j, y+i);
			}
		}

		otherL = new Obj[9];
		otherL[0] = new Wall(0, x, y);
		otherL[1] = new Passable(0, x+1, y);
		otherL[2] = new Passable(1, x+2, y);
		otherL[3] = new Gate(0, x+3, y);
		otherL[4] = new Gate(1, x+4, y);
		otherL[5] = new Gate(2, x+5, y);
		otherL[6] = new Npc(0, x, y+1);
		otherL[7] = new Npc(1, x+1, y+1);
		otherL[8] = new Npc(2, x+2, y+1);

		obj = null;

	}

	void draw(Graphics g) {

		if (obj != null)
			for (Obj i : obj)
				i.draw(g);

	}

}
