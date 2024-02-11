package pack;
import java.util.ArrayList;
import java.util.Arrays;

//Une partie représente le client et le joueur
//Chaque sous-classe concrète représente une partie 
//spécifique

public abstract class Partie {
	protected ArrayList<Bidon> bidons;
	int volumeVisé; //volume à atteindre
	int nbBidons;   //nombre de bidons
	Lanceur l;      //lanceur de commandes
	//une partie passe les commandes au lanceur pour exécution
	
	public String etatBidons(){
		String s = "";
		for (Bidon b : bidons) {
			s = s + b.getVolume() + " "; 	}
		return s; }
	
	//le constructeur crée les bidons
	//les parties concrètes pourront les référencer via des variables
	public Partie(int nbBidons, int[] capacites, int but) {
		volumeVisé = but;
		nbBidons = capacites.length;
		bidons = new ArrayList<Bidon>(nbBidons);
		for(int i = 0; i<nbBidons; i++) {
			bidons.add(i, new Bidon(capacites[i]));}
		l = new Lanceur();
		}
	
	public boolean gagné() {
		return(bidons.get(0).getVolume() == volumeVisé);
	}
	
	public void raz() {
		System.out.println("Annuler toutes les commandes déjà jouées");
		while(l.hasNextUndo())
			l.popLastCommand(); 	
	}	
}
