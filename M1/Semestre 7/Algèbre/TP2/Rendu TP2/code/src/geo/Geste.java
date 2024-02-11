package geo;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import ui.config.Parameters;

public class Geste {
	private String nom;
	private Image img;
	private ArrayList<Trace> traces; 
	private double ak;
	private double bk[];
	private double featureMeanVector[];
	private double covariance[][];
	
	public Geste(String nom) {
		this.nom = nom;
		loadTraces(nom);
		System.out.println("Loading data from "+ ui.config.Parameters.defaultFolder +"/" + nom);
	}
	
	public void loadTraces(String name) {
		initTraces();
		File wd = new File(ui.config.Parameters.defaultFolder +"/" +name);
		System.out.println("loading traces from "+wd.getAbsolutePath());
		
		if (wd.isDirectory()) {
			for (File f:wd.listFiles()) {
				if (!(f.isDirectory())){
					if (f.getName().equals(wd.getName() + "-" + ui.config.Parameters.baseModelName+ ".csv"))
						addTrace(new Trace(true, f.getAbsolutePath()));
					else 
						addTrace(new Trace(false, f.getAbsolutePath()));
				}
			}
		}else {
			System.out.println ("warning: loading data failed");
		}
	}
	
	void computeCovarianceMatrix() {
		for(Trace t : traces) {
			t.initFeatures();
		}
		computeFeaturesMeanVector();
		int dimension = geo.Parameters.numberOfFeatures;
		double matrix[][] = new double[dimension][dimension];
		
		for (int k = 0; k < traces.size(); k++) {
			for (int i = 0; i < dimension; i++) {
				for (int j = 0; j < dimension; j++) {
					matrix[i][j] = (traces.get(k).getValueForFeature(i) - featureMeanVector[i]) *
									(traces.get(k).getValueForFeature(j) - featureMeanVector[j]);
				}
			}
		}		
	}

	private void computeFeaturesMeanVector() {
		int dimension = geo.Parameters.numberOfFeatures;
		featureMeanVector = new double[dimension];
		double m;
		
		for (int i = 0; i < dimension; i++) {
			m = 0;
			for (int k = 0; k < traces.size(); k++) {
				m += traces.get(k).getValueForFeature(i);
			}
			featureMeanVector[i] = m / dimension;
		}
	}

	public Matrice getCovarianceMatrix() {
		return new Matrice(covariance);
	}

	public void setEstimator(Vecteur v) {
		bk = new double[v.getDimension()];	
		for (int i = 0; i < bk.length; i++)
			bk[i] = v.get(i);
	}

	public void setBias(double w0) {
		ak = w0;	
	}
	
	public double computeScore(Vecteur feature) {
		return new Vecteur(bk).produitScalaire(feature) + ak;
	}
	
	public void exportImage(String path) {
		if (traces.size() > 0) {
			Rectangle bb = traces.get(0).computeBoundingBox();
			BufferedImage img = new BufferedImage(bb.width, bb.height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) img.getGraphics();
			g2d.clipRect(bb.x, bb.y, bb.width, bb.height);
			try {
				ImageIO.write(img, "PNG", new java.io.File(path + nom + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Warning : trying to export png on empty traces");
		}
	}

	public void initTraces() {
		 traces = new ArrayList<Trace>();		
	}

	public ArrayList<Trace> getTraces() {
		return this.traces;
	}

	public void addTrace(Trace t) {
		traces.add(t);	
	}

	public Trace get(int i) {
		return traces.get(i);
	}

	public void clear() {
		traces.clear();		
	}

	public String getName() {
		return nom;
	}
	


}
