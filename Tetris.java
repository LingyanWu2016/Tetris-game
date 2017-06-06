package myTetris;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.event.*;


public class Tetris extends JFrame{
	/*private JPanel p;
	private JSlider s1,s2,s3;
	private JLabel l1,l2,l3;*/
	//int M;
	/*public static void main(String[] args) {
		new Tetris();
	}*/
	Tetris(){ 
		/*getContentPane().setLayout(new FlowLayout());
		this.setLocationByPlatform(true);*/
		setTitle("Tetris Game--Assignment3");
		setSize (617, 540);		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		CvTetris tetris=new CvTetris();
		add(tetris,BorderLayout.CENTER );
		tetris.start();
		setVisible(true);
		/*p = new JPanel();
		add(p,BorderLayout.WEST);
		s1 = new JSlider(JSlider.HORIZONTAL,1,10,10);
		s1.setMajorTickSpacing(2);
		s1.setPaintTicks(true);
		p.add(s1);
		l1 = new JLabel("M:10");
		add(l1);
		event e = new event();
		s1.addChangeListener(e);*/
		//pack();
	}
	/*public class event implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			M = s1.getValue();
			l1.setText("Current value:"+M);
		}
	}*/
}

