package calculettePrefixe;

public class TestCalculetteInfixe {

	public static void main(String[] args){
		Calculette c = new Calculette();
		c.enter("plus"); //etat 1 : stocke l'operation a effectuer dans un registre
		c.enter("123"); //etat 2 : stocke le nombre 123 dans acc (Accumulateur)
		c.enter("234"); //etat 3 : effectue l'operation et stocke dans acc 
		System.out.println(c.getResult());
		c.enter("mult"); //etat 1
		c.enter("3"); //etat 2
		c.enter("15"); //etat 3 
		System.out.println(c.getResult());
	}
}
