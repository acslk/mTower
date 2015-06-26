package mtower;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class EditorMain extends JFrame{
	
	EditPanel editor;
	
	public EditorMain() {
		
		super("Editor");
		setResizable(false);
		setLocation(500,300);
		editor = new EditPanel();
		add(editor);
		addMenus();
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("images//level//cursor.png");
		Point hotSpot = new Point (0,0);
		Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "cursor");
		setCursor(cursor);
		
	}

	
	public static void main (String[] args) {
		
		EditorMain frame = new EditorMain();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	 private void addMenus ()
	    {
	        JMenuBar menuBar = new JMenuBar ();
	        JMenu gameMenu = new JMenu ("File");
	        gameMenu.setMnemonic ('F');

	        JMenuItem newMap = new JMenuItem ("New");
	        JMenuItem save = new JMenuItem ("Save");
	        JMenuItem load = new JMenuItem ("Load");
	        newMap.addActionListener (new ActionListener ()
	        {
	            /** Responds to the New Menu choice
	              * @param event The event that selected this menu option
	              */
	            public void actionPerformed (ActionEvent event)
	            {
	            	editor.Tower = new ArrayList<Obj[][]>();
	        		editor.cf = new Obj[11][11];
	        		editor.Tower.add(editor.cf);
	        		editor.floor = 0;
	        		editor.repaint();
	            }
	        }
	        );
	        
	        save.addActionListener (new ActionListener ()
	        {
	            public void actionPerformed (ActionEvent event)
	            {
	                try {
						GamePanel.save("map.map",toArray(editor.Tower), new Character (1000,10,10,5,10), 0);
					} catch (IOException e) {}
	            }
	        }
	        );
	        
	        load.addActionListener (new ActionListener ()
	        {
	            public void actionPerformed (ActionEvent event)
	            {
	            	try {
						editor.load("map.map");
					} catch (FileNotFoundException e) {}
	            	
	            }
	        }
	        );
	        
	        gameMenu.add(newMap);
	        gameMenu.add(save);
	        gameMenu.add(load);
	        menuBar.add(gameMenu);
	     
	        setJMenuBar (menuBar);
	    }
	 
	 
	 public static Obj[][][] toArray (ArrayList <Obj[][]> a){
		 
		 Obj[][][] b = new Obj[a.size()][11][11];
		 for (int i = 0; i < a.size(); i++)
			 b[i] = a.get(i);
		 return b;
	 }
	
}
