package geo;

import java.util.ArrayList;

//matrices carrees
public class Matrice {
	private final double[][] m;
	private final int dimension;
	private double determinant;

	public Matrice(double[][] coefs) {
		dimension = coefs[0].length;
		m = new double[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				m[i][j] = coefs[i][j];
			}
		}
		this.computeDeterminant();
	}

	public static Matrice computeAverageMatrixFromMatrixList(ArrayList<Matrice> l) {
		//Initialisation de la matrice moyenne
		double[][] coefsMoy = new double[l.get(0).dimension][l.get(0).dimension];
		for (int i = 0; i < l.get(0).dimension; i++) { //Pour chaque ligne
			for (int j = 0; j < l.get(0).dimension; j++) { //Pour chaque colonne
				coefsMoy[i][j] = 0;
			}
		}
		//Ajout des l coefs successifs
		for(int i=0;i<l.get(0).dimension;i++){ //Pour chaque ligne
			for(int j=0;j<l.get(0).dimension;j++){ //Pour chaque colonne
				for(int k=0;k<l.size();k++){ //Pour chaque matrice
					coefsMoy[i][j] += l.get(k).m[i][j];
				}
			}
		}
		//Divison des coefs par l
		for (int i = 0; i < l.get(0).dimension; i++) { //Pour chaque ligne
			for (int j = 0; j < l.get(0).dimension; j++) { //Pour chaque colonne
				coefsMoy[i][j] = coefsMoy[i][j]/l.size();
			}
		}
		return new Matrice(coefsMoy);
	}

	public double get(int i, int j) {
		return this.m[i][j];
	}

	public double getDeterminant() {
		return this.determinant;
	}

	public Vecteur getLine(int k) {
		return new Vecteur(this.m[k]);
	}
	public Vecteur getColumn(int k) {
		double[] colonne = new double[this.dimension];
		for(int i=0;i<this.dimension;i++){
			colonne[i] = this.m[k][i];
		}
		return new Vecteur(colonne);
	}

	public Matrice inverse() {
		// todo -> calculer l'inverse de this, après qu'elle était inversible
		// la retourner comme résultat
		return null; // à changer pour le résultat
	}

	private void computeDeterminant()
	{
		double num1, num2, det = 1, total = 1;
		int index;

		// temporary array for storing row
		double[] temp = new double[this.dimension + 1];

		// loop for traversing the diagonal elements
		for (int i = 0; i < this.dimension; i++) {
			index = i; // initialize the index

			// finding the index which has non zero value
			while (this.m[index][i] == 0 && index < this.dimension-1) {
				index++;
			}
			if (index == this.dimension) // if there is non zero element
			{
				// the determinant of matrix as zero
				continue;
			}
			if (index != i) {
				// loop for swapping the diagonal element row
				// and index row
				for (int j = 0; j < this.dimension; j++) {
					swap(this.m, index, j, i, j);
				}
				// determinant sign changes when we shift
				// rows go through determinant properties
				det = (det * Math.pow(-1, index - i));
			}

			// storing the values of diagonal row elements
			for (int j = 0; j < this.dimension; j++) {
				temp[j] = this.m[i][j];
			}

			// traversing every row below the diagonal
			// element
			for (int j = i + 1; j < this.dimension; j++) {
				num1 = temp[i]; // value of diagonal element
				num2 = this.m[j][i]; // value of next row element

				// traversing every column of row
				// and multiplying to every row
				for (int k = 0; k < this.dimension; k++) {
					// multiplying to make the diagonal
					// element and next row element equal
					this.m[j][k] = (num1 * this.m[j][k])
							- (num2 * temp[k]);
				}
				total = total * num1; // Det(kA)=kDet(A);
			}
		}

		// multiplying the diagonal elements to get
		// determinant
		for (int i = 0; i < this.dimension; i++) {
			det = det * this.m[i][i];
		}
		this.determinant = (det / total); // Det(kA)/k=Det(A);
	}

	static double[][] swap(double[][] arr, int i1, int j1, int i2, int j2)
	{
		double temp = arr[i1][j1];
		arr[i1][j1] = arr[i2][j2];
		arr[i2][j2] = temp;
		return arr;
	}

}
