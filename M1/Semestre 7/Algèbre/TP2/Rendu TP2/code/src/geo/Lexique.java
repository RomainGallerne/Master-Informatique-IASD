package geo;

import java.io.File;
import java.util.ArrayList;

public class Lexique {
	private String dataStoragePath = ui.config.Parameters.defaultFolder; 	
	private ArrayList<Geste> gestes;
	private Matrice globalCovarianceMatrix;
	private Matrice globalCovarianceMatrixInverse;
	public Lexique() {
		initRecognizer();
	}
	private void computeAllCovariances() {
		ArrayList<Matrice> l = new ArrayList<Matrice>();
		Geste g;
		for (int k= 0; k < gestes.size(); k++) {
			g = gestes.get(k);
			g.computeCovarianceMatrix();
			l.add(g.getCovarianceMatrix());
		}
		globalCovarianceMatrix = Matrice.computeAverageMatrixFromMatrixList(l);	
		globalCovarianceMatrixInverse = globalCovarianceMatrix.inverse();
	}
	
	//pré-requis: la matrice globale de covariance est à jour
	private void computeAllRecognitionFunctions() {
		Geste g;
		for (int k= 0; k < gestes.size(); k++) {
			g = gestes.get(k);
			g.setEstimator(globalCovarianceMatrixInverse.getColumn(k));
		}		
	}
	
	public Geste recognize(Trace t) {
		Geste g;
		double results[] = new double[gestes.size()];
		
		for (int k= 0; k < gestes.size(); k++) {
			g = gestes.get(k);
			results[k] = g.computeScore(t.getFeatureVector());
		}
		
		//for version0, find "first max"
		//todo : what if several gestures have the max score?
		int max = 0;
		for (int i = 0; i < results.length; i++) {
			if (results[i] > results[max]) {
				max = i;
			}
		}		
		return gestes.get(max);
	}
	
	public void initRecognizer(){
		importData(dataStoragePath);
		computeAllCovariances();
		computeAllRecognitionFunctions();
	}
	
	public void importData(String filePath) {
		gestes = new ArrayList<Geste>();
		File wd = new File(dataStoragePath);
		System.out.println("loading lexicon from "+wd.getAbsolutePath());
		
		if (wd.isDirectory()) {
			for (File f:wd.listFiles()) {
				if (f.isDirectory()){
					gestes.add(new Geste(f.getName())); //crée le geste du nom du dir et charge le modèle et les traces qui se trouvent dans le dir
				}
			}
		} else {
			System.out.println ("warning: loading data failed -> "+ wd.getAbsolutePath() + " is not a directory");
		}

	}	

}
