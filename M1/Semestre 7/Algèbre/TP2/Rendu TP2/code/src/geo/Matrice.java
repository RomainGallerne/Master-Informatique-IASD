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
	
	public double[][] getM() {
		return this.m;
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
	
	private void computeDeterminant() {
	    if (this.dimension==1) {
	        // Matrice 1x1 -> On retourne l'unique élément
	        this.determinant = this.m[0][0];
	    } else if (this.dimension==2) {
	        // Matrice 2x2 -> det=ad - bc
	        this.determinant = (this.m[0][0]*this.m[1][1]) - (this.m[0][1]*this.m[1][0]);
	    } else {
	        // Pour une matrice >2x2 -> Développement de laplace
	        double det = 0;
	        for (int j=0; j<this.dimension; j++) {
	            det += this.m[0][j]*this.cofacteur(0,j); 
	            //On réalise toujours le développement par rapport à la ligne 1
	            //On ne gère pas ici le problème de savoir quelle ligne est la plus simple à développer (contient le plus de 0)
	        }
	        this.determinant = det;
	    }
	}

	private double cofacteur(int ligne, int colonne) {
	    int dimSousMatrice = this.dimension-1;
	    double[][] tabSousMatrice = new double[dimSousMatrice][dimSousMatrice];
	    int ligneSousMatrice=0, colonneSousMatrice=0;

	    //Remplissage de la sous-Matrice
	    for (int i=0; i<this.dimension; i++) {
	        if (i!=ligne) {
	        	colonneSousMatrice=0;
		        for (int j=0; j<this.dimension; j++) {
		            if (j!=colonne) {
			            tabSousMatrice[ligneSousMatrice][colonneSousMatrice] = this.m[i][j];
			            colonneSousMatrice++;
		            }
		        }
		        ligneSousMatrice++;
	        }
	    }

	    Matrice sousMatrice = new Matrice(tabSousMatrice);
	    //Appel récursif, calcul du déterminant de la sousMatrice au "new"
	    return Math.pow(-1, ligne+colonne)*sousMatrice.getDeterminant();
	}

	public Matrice inverse() {
		if(this.getDeterminant() != 0) { //Si la matrice est inversible
			double[][] matriceInverse = new double[dimension][dimension];
			//Copie de la matrice m
	        double[][] matrice = new double[dimension][dimension];
	        for (int i=0; i<dimension; i++) {
	            System.arraycopy(this.m[i], 0, matrice[i], 0, dimension);
	        }
	        
			//Remplissage de la matrice inverse
			for (int i=0; i<this.dimension; i++) {
				for (int j=0; j<this.dimension; j++) {
					if(i==j) {matriceInverse[i][j] = 1.0;}
					else {matriceInverse[i][j] = 0.0;}
				}
			}
			
			//Pivot de Gauss
			for (int i = 0; i<dimension; i++) {
				
				int ligneSwap = i;
				while(matrice[i][i] == 0.0) {
					matrice = swapLigne(matrice, i, ligneSwap);
					matriceInverse = swapLigne(matriceInverse, i, ligneSwap);
					ligneSwap++;
				}
				
	            double pivot = matrice[i][i];
	            
	            for (int j = 0; j<dimension; j++) {
	                matrice[i][j] /= pivot;
	                matriceInverse[i][j] /= pivot;
	            }
	            for (int k = 0; k<dimension; k++) {
	                if (k != i) {
	                    double facteur = matrice[k][i];
	                    for (int j = 0; j<dimension; j++) {
	                        matrice[k][j] -= facteur*matrice[i][j];
	                        matriceInverse[k][j] -= facteur*matriceInverse[i][j];
	                    }
	                }
	            }
	        }
			return new Matrice(matriceInverse);
		}
		else {return null;}
	}
	
	public double[][] swapLigne(double[][] matrice, int a, int b){
		double[][] matCopie = new double[dimension][dimension];
        for (int i=0; i<dimension; i++) {
            System.arraycopy(matrice[i], 0, matCopie[i], 0, dimension);
        }
		double[] ligneTemp = matCopie[a];
		matCopie[a] = matCopie[b];
		matCopie[b] = ligneTemp;
		return matCopie;
	}
	
	public Vecteur applicationLin(Vecteur vect) {
	    if (this.dimension != vect.getDimension()) {
	        throw new IllegalArgumentException("Les dimensions de la matrice et du vecteur ne correspondent pas");
	    }

	    double[] result = new double[this.dimension];

	    for (int i=0; i<this.dimension; i++) {
	        double sum=0;
	        for (int j=0; j<this.dimension; j++) {
	            sum += this.m[i][j] * vect.get(j);
	        }
	        result[i] = sum;
	    }

	    return new Vecteur(result);
	}
	
	public String toString(){
		String affichage = "";
		for(int i=0;i<dimension;i++) {
			for(int j=0;j<dimension;j++) {
				affichage += (Math.round(this.m[i][j]*1000.0)/1000.0+" ");
			}
			affichage += "\n";
		}
		return affichage;
	}

}
