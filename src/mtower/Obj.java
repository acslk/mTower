package mtower;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Obj extends Rectangle {
	
	int type;
	public Obj (int type, int x, int y)
	{
		super((x+6)*32, (y+1)*32,32,32);
		this.type = type;
	}
	
	abstract void interact (Character c);
	abstract boolean pass();
	abstract boolean exist();
	abstract void draw (Graphics g);
	abstract Obj copy(int x, int y);
	
	static int convX (int x) {
		
		return x/32-6;
		
	}
	
	static int convY (int y) {
		
		return y/32 - 1;
	}
	
	public String toString() {
		
		String value = "";
		value += (x/32-6 + " " + (y/32-1) + " ");
		if (this instanceof Wall)
			value += (1 + " ");
		else if (this instanceof Mon)
			value += (2 + " ");
		else if (this instanceof Item)
			value += (3 + " ");
		else if (this instanceof Passable)
			value += (4 + " ");
		else if (this instanceof Gate)
			value += (5 + " ");
		else if (this instanceof Npc)
			value += (6 + " ");
		
		value += (type + " ");
		
		return value;
	}

}
