package mtower;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class GameKeyBindings {

	GamePanel game;
	String cmd;
	Timer t;
	int dx, dy;
	int ix, iy;
	boolean isMoving = false;

	public GameKeyBindings(GamePanel g) {

		InputMap im = g.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = g.getActionMap();

		im.put(KeyStroke.getKeyStroke("pressed RIGHT"), "RightArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DownArrow");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "x");

		am.put("RightArrow", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				cmd = "Right";
			}
		});
		am.put("LeftArrow", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				cmd = "Left";
			}
		});
		am.put("UpArrow", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				cmd = "Up";
			}
		});
		am.put("DownArrow", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				cmd = "Down";
			}
		});
		am.put("Enter", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				cmd = "Enter";
			}
		});
		am.put("x", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				cmd = "x";
			}
		});

		ActionListener refresh = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (cmd != null)
					keyEvent();

			}

		};

		t = new Timer(1, refresh);
		t.start();
		this.game = g;
	}

	public void keyEvent () {
		
		if (game.mode == 0 && !isMoving) {
			if (cmd.equals("Left"))
				checkMove(3);
			else if (cmd.equals("Right"))
				checkMove(4);
			else if (cmd.equals("Up"))
				checkMove(1);
			else if (cmd.equals("Down"))
				checkMove(2);
			else if (cmd.equals("x")) {
				game.mode = 2;
				game.option = 0;
				game.repaint(192, 32, 352, 352);
				for (int i = 0; i < 11; i++)
					for (int j = 0; j < 11; j++)
						if (game.cf[i][j] != null && game.cf[i][j] instanceof Mon)
							game.monList[((Mon) game.cf[i][j]).type] = true;
				//for (int i = 0; i < game.monList.length; i++)
			}
		}
		
		else if (game.mode == 1) {
			if (cmd.equals("Up")) {
				if (game.option > 0)
					game.option--;
				game.repaint(288, 160, 32, 96);
				
			} 
			else if (cmd.equals("Down")) {
				if (game.option < game.op_n-1)
					game.option++;
				game.repaint(288, 160, 32,96);
			}
			else if (cmd.equals("Enter")) {
				Npc.exchange(game.shop_n, game.option, game);
				game.repaint(32, 32, 128, 352);
			}
		}
		
		else if (game.mode == 2) {
			if (cmd.equals("x")) {
				game.mode = 0;
				for (int i = 0; i < Mon.hp.length; i++)
					game.monList[i] = false;
			}
			else if (cmd.equals("Left")) {
				if (game.option > 0)
					game.option--;
				game.repaint(225, 350, 200, 32);
				
			} 
			else if (cmd.equals("Right")) {
				if (game.option < 2)
					game.option++;
				game.repaint(225, 350, 200,32);
			}
			else if (cmd.equals("Up")) {
				
			}
			else if (cmd.equals("Down")) {
				
			}
			else if (cmd.equals("Enter")) {
				if (game.option == 1) {
					try {
						GamePanel.save("save.save",game.tower, game.c, game.floor);
					} catch (IOException e1) {}
					game.repaint(32, 32, 128, 352);
					game.mode = 0;
				}
				else if (game.option == 2) {
					try {
						game.load("save.save");
					} catch (FileNotFoundException e1) {}
					game.repaint(32, 32, 128, 352);
					game.mode = 0;
				}
			}
		}
		
		cmd = null;
		
	}

	// 1-up, 2-down, 3-left, 4-right
	public void checkMove(int direction) {

		isMoving = true;

		if (direction == 1) {
			dx = game.c.px;
			dy = game.c.py - 1;
		} else if (direction == 2) {
			dx = game.c.px;
			dy = game.c.py + 1;
		} else if (direction == 3) {
			dx = game.c.px - 1;
			dy = game.c.py;
		} else if (direction == 4) {
			dx = game.c.px + 1;
			dy = game.c.py;
		}

		game.c.dir(direction);

		System.out.println(dx + "  " + dy);

		if (dx > 10 || dx < 0 || dy > 10 || dy < 0) {
		} else if (game.cf[dx][dy] != null) {
			if (game.cf[dx][dy].pass())
				move(direction);
			game.cf[dx][dy].interact(game.c);
			game.repaint(32, 32, 128, 352);
			if (game.cf[dx][dy] != null && !game.cf[dx][dy].exist())
				game.cf[dx][dy] = null;
		} else {
			move(direction);
		}

		isMoving = false;

	}

	// 1-up, 2-down, 3-left, 4-right
	public void move(int direction) {

		for (int i = 0; i < 4; i++) {

			for (int j = 0; j < 2; j++) {
				if (direction == 1)
					game.c.y -= 4;
				else if (direction == 2)
					game.c.y += 4;
				else if (direction == 3)
					game.c.x -= 4;
				else if (direction == 4)
					game.c.x += 4;
				GamePanel.delay(30);
				game.paintImmediately(192, 32, 352, 352);
			}

			game.c.shuffle();

		}

		game.c.px = dx;
		game.c.py = dy;

	}

}
