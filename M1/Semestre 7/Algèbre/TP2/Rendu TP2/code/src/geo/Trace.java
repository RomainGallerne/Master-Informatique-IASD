package geo;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import ui.Style;
import ui.io.ReadWritePoint;

public class Trace {
	private ArrayList<PointVisible> points ;
	private Style style = new Style();
	private Vecteur features;

	public Trace(boolean model) {
		if (model) style = Style.getModelStyle();
		points = new ArrayList<PointVisible>();
		features = new Vecteur(13);		
	}
	
	public Trace(boolean model, String fileName) {
		this(model);
		File f = new File(fileName);
		ReadWritePoint rwp = new ReadWritePoint(f);
		if (model) System.out.println("loading model from "+f.getAbsolutePath());
		points = rwp.read();
		initFeatures();
	}
	
	void initFeatures() {
		//f1
		Vecteur2D u1 = new Vecteur2D(points.get(2).x - points.get(0).x, points.get(2).y - points.get(0).y);
		features.coords[0] = u1.cosinus();
		
		//f2
		features.coords[1] = u1.sinus();
		
		//f3
		Rectangle r = computeBoundingBox();
		Vecteur2D u2 = new Vecteur2D(r.width, r.height);
		features.coords[2] = u2.norme();
		
		//f4 
		features.coords[3] = u2.angle();
		
		//f5 
		int n = points.size();
		Vecteur2D u3 = new Vecteur2D(points.get(n - 1).x - points.get(0).x, points.get(n - 1).y - points.get(0).y);
		features.coords[4] = u3.norme();
		
		//f6 et f7
		Vecteur2D u4 = new Vecteur2D(points.get(0).x, points.get(0).y);
		Vecteur2D u5 = new Vecteur2D(points.get(n - 1).x, points.get(n - 1).y);
		features.coords[5] = u4.cosinus(u5);
		features.coords[6] = u4.sinus(u5);
		
		//f8
		features.coords[7] = traceLength();
		
		//f9, f10, f11
		double x = points.get(1).x - points.get(0).x, y = points.get(1).y - points.get(0).y;
		features.coords[8] = features.coords[9] = features.coords[10] = 0;
		
		Vecteur2D s1 = new Vecteur2D(x,y), s2 = new Vecteur2D(0,0);		
		for (int i = 2; i < points.size(); i++) {
			x = points.get(i).x - x;
			y = points.get(i).x - y;
			s2.setCoords(x, y);
			features.coords[8] += s1.angle(s2);
			features.coords[9] += features.coords[8] > 0 ? features.coords[8] : -features.coords[8];
			features.coords[10] += Math.pow(features.coords[8], 2);
			s1.setCoords(s2.coords[0], s2.coords[1]);
		}
		
		//f12
		features.coords[11] = traceDuration();
		
		//f13
		features.coords[11] = maxSpeed();		
	}

	private double maxSpeed() {
		double max = 0;
		double x = points.get(0).x, y = points.get(0).y, previous =points.get(0).timeStamp, speed, dt;
		Vecteur2D subPath = new Vecteur2D(x,y);
		
		for (int i = 1; i < points.size(); i++) {
			x = points.get(i).x - x;
			y = points.get(i).x - y;
			subPath.setCoords(x, y);
			dt = points.get(i).timeStamp - previous;
			speed = subPath.norme()/dt;
			if (speed > max ) max = speed;
		}			
		return max;
	}

	private double traceDuration() {
		int n = points.size();
		return points.get(n - 1).getTimeStamp() - points.get(0).getTimeStamp();
	}


	public void add(Point p) {
		add(new PointVisible(p.x, p.y));	
	}
	
	public void add(PointVisible p) {
		points.add(p);	
	}
	

	public void showInfos(Graphics2D g) {
		Rectangle r = computeBoundingBox();
		String features = points.size() + " points,  length ~> "+ Math.round(traceLength());
		g.translate(-r.x, -r.y);
		g.scale(2, 2);
		g.drawString(features,r.x, r.y - 10);
		g.scale(.5, .5);
		g.translate(r.x, r.y);
	}
	
	public void draw(Graphics2D g) {
		if (style.drawLine()) {
			drawLines(g);
		}
		if (style.drawPoints()) {
			drawPoints(g);
		}
		showInfos(g);	
	}
	
	public void drawPoints(Graphics2D g) {
		for (PointVisible p: points) {
			p.dessine(g, style);
		}
	}

	private void drawLines(Graphics2D g) {
		PointVisible p1, p2;
		g.setColor(style.color());
		for (int i = 0; i < points.size()-1;i++) {
			p1 = points.get(i);
			p2 = points.get(i+1);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
	
	public Rectangle computeBoundingBox() {
		int minx, miny,maxx, maxy;
		minx = points.get(0).x;
		maxx = points.get(0).x;
		miny = points.get(0).y;
		maxy = points.get(0).y;
		for (PointVisible p: points) {
			if(p.x < minx) minx = p.x;
			if(p.y < miny) miny = p.y;
			if(p.x > maxx) maxx = p.x;
			if(p.y > maxy) maxy = p.y;
		}
		return new Rectangle(minx,miny,maxx-minx,maxy-miny);
	}
	
	public double traceLength() {
		double x = points.get(0).x, y = points.get(0).y, length=0;
		Vecteur2D subPath = new Vecteur2D(x,y);
		for (int i = 1; i < points.size(); i++) {
			x = points.get(i).x - x;
			y = points.get(i).x - y;
			subPath.setCoords(x, y);
			length += subPath.norme();
		}
		return length;
	}
	
	public int exportWhenConfirmed(String filePath) {
		Path p = Paths.get(filePath);
		int userInput = JOptionPane.NO_OPTION;
		if (Files.exists(p)) {
			userInput = JOptionPane.showConfirmDialog(null, 
					p.getFileName()+": file exists, overwrite existing file ?", "", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			System.out.println("Export cancelled");
			if(userInput != JOptionPane.YES_OPTION) return userInput;		
		}
		System.out.println("Export "+p);
		export(filePath, true);
		return userInput;		
	}
	
	private void export(String path, boolean overwrite) {
		File f = new File(path);
		if (f.exists() && !overwrite) return;
		ReadWritePoint rw = new ReadWritePoint(f);
		Rectangle r = computeBoundingBox();
		int x,y;
		for (PointVisible p: points){
			x = p.x-r.x; 
			y = p.y-r.y;
			rw.add(x+";"+y+";"+p.toString());
		}
		rw.write();
	}

	public double getValueForFeature(int i) {
		return features.get(i);
	}

	public Vecteur getFeatureVector() {
		return new Vecteur(features);
	}

}
