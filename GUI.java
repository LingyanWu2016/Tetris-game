package myTetris;

import javax.swing.*;
import java.io.IOException;
import java.awt.GridLayout;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
public class GUI extends JPanel{
	public int score=0;
	public int level=1;
	public double speed=1.0;
	public GUI() throws IOException{       
		this.setLayout(new GridLayout(5, 1));
		
		JSlider N = new JSlider();
		N.setMinimum(20);
		N.setMaximum(50);
		N.setMinorTickSpacing(1);
		N.setMajorTickSpacing(5);
		N.setPaintTicks(true);
		N.setPaintLabels(true);
		JLabel levelFactor = new JLabel("Current N (Level Factor): " + N.getValue());
		N.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if ((JSlider)e.getSource() == N) {
					level = N.getValue();
					levelFactor.setText("Current N (Level Factor): " + level);
				}
			}
		});
		this.add(levelFactor);
		this.add(N);
		
		JSlider M = new JSlider();
		M.setMinimum(1);
		M.setMaximum(10);
		M.setMinorTickSpacing(1);
		M.setMajorTickSpacing(2);
		M.setPaintTicks(true);
		M.setPaintLabels(true);
		JLabel scoreFactor = new JLabel("Current M (Scoring Factor): " + M.getValue());
		M.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				if ((JSlider)e.getSource() == M) {
					score = M.getValue();
					scoreFactor.setText("Current M (Scoring Factor) : " + score);
				}
			}
		});
		this.add(scoreFactor);
		this.add(M);		
		JSlider S = new JSlider();
		S.setMinimum(1);
		S.setMaximum(10);
		S.setMinorTickSpacing(1);
		S.setMajorTickSpacing(2);
		S.setPaintTicks(true);
		S.setPaintLabels(true);
		JLabel speedFactor = new JLabel("Current S (Speed Factor): " + S.getValue()/10.0);
		S.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				if ((JSlider)e.getSource() == S) {
					speed = S.getValue()/10.0;
					speedFactor.setText("Current S(Speed Factor): " + speed);
				}
			}
		});
		this.add(speedFactor);
		this.add(S);
		JButton startBt = new JButton("Start Game");		
		startBt.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				new Tetris();
				startBt.setEnabled(false);
			}
		});
		this.add(startBt);
		startBt.setEnabled(false);

		JButton startBt1 = new JButton("Adjust Parameters");
		
		startBt1.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {		
				PrintWriter writer = null;
				try {
					writer = new PrintWriter("level.txt");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
		    	writer.println(Integer.toString(level));
		    	writer.close();
		    	
		    	PrintWriter writer1 = null;
				try {
					writer1 = new PrintWriter("factor.txt");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
		    	writer1.println(Double.toString(speed));
		    	writer1.close();
		    	
		    	PrintWriter writer3 = null;
				try {
					writer3 = new PrintWriter("scorefactor.txt");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
		    	writer3.println(Integer.toString(score));
		    	writer3.close();		    	
		    	startBt.setEnabled(true);

			}
		});
		this.add(startBt1);		
	}
}