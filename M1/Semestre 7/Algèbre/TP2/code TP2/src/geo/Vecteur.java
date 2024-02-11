package geo;
import java.lang.Math;

public class Vecteur {
	double coords[];
	
	public Vecteur(int dimension) {
		coords = new double[dimension];
	}
	
	public Vecteur(double[] bk) {
		coords = new double[bk.length];
		for (int i = 0; i < bk.length; i++)
			coords[i] = bk[i];
	}

	public Vecteur(Vecteur v) {
		coords = new double[v.getDimension()];
		for (int i = 0; i < coords.length; i++)
			coords[i] = v.get(i);
	}

	// version 0 - on suppose que les vecteurs sont de même dimension
	// pas de vérification automatique car pas de gestion d'exception
	public double produitScalaire(Vecteur v) {
		double prodscalaire = 0;
		for(int i=0;i<coords.length;i++){
			prodscalaire += this.coords[i]*v.coords[i];
		}
		return prodscalaire;
	}
	
	public double cosinus(Vecteur v) {
		return (this.produitScalaire(v)) / (this.norme()*v.norme());
	}
	

	public double norme() {
		double sommecarre = 0;
		for(int i=0;i<coords.length;i++){
			sommecarre += Math.pow(this.coords[i],2);
		}
		return Math.sqrt(sommecarre);
	}

	public double get(int i) {
		return coords[i];
	}

	public void setValueForFeature(int i, double d) {
		this.coords[i] = d;		
	}

	public int getDimension() {
		return this.coords.length;
	}
	
}
