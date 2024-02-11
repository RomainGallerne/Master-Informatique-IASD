package geo;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import ui.Couleur;
import ui.Style;

public class PointVisible extends Rectangle {
	private Color color = Couleur.fg;
	private String label;
	int timeStamp;

	public void setLabel(String label) {
		this.label = label;
	}

	public String toString() {
		return this.label;
	}

	public PointVisible(int x, int y) {
		super(x, y, 2 * Style.defaultHalfWidth, 2 * Style.defaultHalfWidth);
		label = "p";
	}

	public PointVisible(double x, double y) {
		this((int)x,(int)y);
		label = "m";
	}

	public void dessine(Graphics2D g2d, Style style) {
		g2d.setColor(style.color());
		width = style.getWidth() > 1 ? style.getWidth()/2 : style.getWidth() ;
		switch(style.drawPoint) {
			case disc:
				g2d.fill(new Ellipse2D.Double(this.x - width, this.y - width, 2 * width, 2 * width));
				break;
			case circle:
				g2d.draw(new Ellipse2D.Double(this.x - width, this.y - width, 2 * width, 2 * width));
				break;
			case square:
				g2d.draw(new Rectangle(this.x - width, this.y - width, 2 * width, 2 * width));
				break;
			case filledSquare:
				g2d.fill(new Rectangle(this.x - width, this.y - width, 2 * width, 2 * width));
				break;
			case cross:
				g2d.draw(new Line2D.Double(this.x - width, this.y - width, this.x - width+2 * width, this.y - width+2 * width));
				g2d.draw(new Line2D.Double(this.x - width+2 * width, this.y - width, this.x - width, this.y - width+2 * width));
				break;
			default:
				g2d.draw(new Ellipse2D.Double(this.x - width, this.y - width, 2 * width, 2 * width));
				break;
		}		
		if (style.drawLabel()) drawLabel(g2d);
	}

	public void print() {
		System.out.println("x = " + x + " y = " + y + " w = " + width + " h = " + height);
	}

	public void drawLabel(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		String longLabel;
		int centeredText;
		longLabel = label + "(" + x + "," + y + ")";
		centeredText = (int) (x - fm.stringWidth(longLabel) / 2);
		g.drawString(longLabel, centeredText, (int) (y - width - fm.getDescent()));
	}


	public PointVisible copy() {
		PointVisible p = new PointVisible(this.x, this.y);
		p.color = this.color;
		p.label = this.label;
		return p;
	}

	public double distance(PointVisible p2) {
		double dx = p2.x - x, dy = p2.y - y;
		return Math.sqrt(dx*dx + dy*dy);
	}

	public int getTimeStamp() {
		// TODO Auto-generated method stub
		return timeStamp;
	}
}
