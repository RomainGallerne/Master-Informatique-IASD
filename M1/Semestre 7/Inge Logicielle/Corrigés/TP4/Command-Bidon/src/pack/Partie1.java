package pack;

import java.util.Collection;

//le client, incluant le joueur, est représenté par le main.

public class Partie1 extends Partie{

	public Partie1(int nbBidons, int[] capacites, int but) {
		super(nbBidons, capacites, but); }
	
	
	public Collection jouer() {
		//ici la création de la commande est concomittante à son invocation
		//(on crée la commande puis on la passe au lanceur
		//ce n'est pas forcément toujours le cas.
		//Il peut y avoir plein de variantes, par exemple on crée les commandes une fois
		//et on les passe ensuite au lanceur quand besoin.
		//c'est d'ailleurs une optimisation possible de cet exercice, si on doit remplir
		//3 fois le bidon b3, est-il utile de créer 3 instances de RemplirBidon ...
		
		Bidon b1 = bidons.get(0);
		Bidon b2 = bidons.get(1);
		Bidon b3 = bidons.get(2);
		
		l.pushCommand(new RemplirBidon(b2));
		l.pushCommand(new TransvaserBidon(b2,b1)); //100
		
		l.pushCommand(new RemplirBidon(b2));
		l.pushCommand(new TransvaserBidon(b2,b1)); //200 c'est trop
		l.popLastCommand();                        //undo last
		System.out.println(this.etatBidons());
		
		l.pushCommand(new RemplirBidon(b3));
		l.pushCommand(new TransvaserBidon(b3,b1));
		
		if (this.gagné())
			System.out.println("Gagné avec la solution : " + l.getHistory());
		else System.out.println("Perdu, mauvaise solution : " + l.getHistory());
		return(l.getHistory());
	}
}
