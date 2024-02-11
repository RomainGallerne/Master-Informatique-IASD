package pack;

public class Main {

	public static void main(String[] args) {
		
		//une partie
		int[] capacitesBidons = {200, 100, 50};
		Partie1 p = 
				new Partie1(
							3,                //nombre de bidons
							capacitesBidons,  //capacité des bidons
							150);             //volume à atteindre
		p.jouer();
		}
}
