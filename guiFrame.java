package myTetris;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;



public class guiFrame extends JFrame{
	public static void main(String[] args) throws IOException {
		new guiFrame();
	}
	
	guiFrame() throws IOException{ 
		super("User Interface");
		setIgnoreRepaint(true);
		addWindowListener(new WindowAdapter()
		{public void windowClosing(WindowEvent e){System.exit(0);}});
		setSize (600, 500);
		GUI gui= new GUI();
		add(gui);
		setVisible(true);		
		pack();
	}
}
	


