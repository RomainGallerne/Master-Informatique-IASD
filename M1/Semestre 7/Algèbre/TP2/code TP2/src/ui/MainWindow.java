package ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import geo.Trace;

public class MainWindow extends JFrame{
	Vue v1;
	
	public MainWindow(boolean training, String title, int x, int y, int w, int h) {
		super(title);
		if (training) 
			createTrainingWindow(x,y,w,h); 
		else
			createRecognizerWindow();			
	}
	
	public MainWindow(boolean training, String title) {
		super(title);
		if (training) 
			createTrainingWindow(900, 40, 800, 600); 
		else
			createRecognizerWindow();	
	}

	private void createTrainingWindow(int x, int y, int w, int h) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		v1 = new Vue(w, h);
		add(new UsefulTools(this, v1, true), BorderLayout.NORTH);
		add(new JScrollPane(v1));
		pack();
		setLocation(x, y);		
	}
	
	private void createRecognizerWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		v1 = new Vue(400, 400);
		v1.clear(true);
		add(new UsefulTools(this, v1, false), BorderLayout.NORTH);
		add(new JScrollPane(v1));
		pack();
		setLocation(500,50);		
	}

	public void addGesture(Trace g) {
		v1.add(g);
	}
}
