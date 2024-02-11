package ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import geo.Trace;

public class Tracker implements MouseMotionListener, MouseListener{
	Vue vue;
	Trace trace;
	boolean on;
	
	public Tracker(Vue vue) {
		this.vue = vue;
	}

	private void startTracking(Point P0) {
		trace = new Trace(false);
		vue.add(trace);
		trace.add(P0);
		on = true;
	}

	private void stopTracking() {
		on = false;
	}


	@Override
	public void mouseMoved(MouseEvent e) {
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		stopTracking();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		stopTracking();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startTracking(e.getPoint());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (on) {
			trace.add(e.getPoint());
			vue.repaint();
		}
	}
}
