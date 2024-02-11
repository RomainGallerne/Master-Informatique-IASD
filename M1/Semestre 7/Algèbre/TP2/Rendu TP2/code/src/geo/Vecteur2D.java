package geo;

public class Vecteur2D extends Vecteur {
	public Vecteur2D(double x, double y) {
		super(2);
		coords[0] = x;
		coords[1] = y;
	}
	
// sinus de l'angle du vecteur i (axe des abscisses) à this
	public double sinus() {
		double coordsi [] = {1,0};
		Vecteur i = new Vecteur(coordsi);
		//On utilise la relation cos**2 + sin**2 = 1
		return Math.sqrt((double)1-Math.pow(this.cosinus(i),2));
	}
	
	public double sinus(Vecteur2D v) {
		//On utilise la relation cos**2 + sin**2 = 1
		return Math.sqrt((double)1-Math.pow(this.cosinus(v),2));
	}
	
	private double det(Vecteur2D v) {
		return this.coords[0]*v.coords[1] - this.coords[1]*v.coords[0];
	}

// cosinus de l'angle de i à this
	public double cosinus() {
		double coordsi [] = {1,0};
		Vecteur i = new Vecteur(coordsi);
		return super.cosinus(i);
	}
	
// tangente de l'angle de i à this
// attention this ne doit pas être vertical...
	public double tangente() {
		//On utilise tan = sin / cos
		if(this.coords[0] != 0){
			return this.sinus()/this.cosinus();
		}
		else return (double)0;
	}
	
	public double angle() {
		return Math.acos(this.cosinus());
	}

	public void setCoords(double x, double y) {
		this.coords[0] = x;
		this.coords[1] = y;
	}

	public double angle(Vecteur2D s2) {
		return Math.acos(this.cosinus(s2));
	}


}
