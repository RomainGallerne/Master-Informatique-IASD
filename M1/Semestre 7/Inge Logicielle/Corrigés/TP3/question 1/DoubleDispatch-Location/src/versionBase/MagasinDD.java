package versionBase;
//Magasin avec double dispatch voir étape 1 et étape 2 dans les commentaires//
//version de base, ProduitSoldé est une sous-classe de Produit

public class MagasinDD{
	public static void main(String[] args){
		
		Produit lgv = new Produit("La grande vadrouille", 10.0);
		Client cl = new Client("Dupont");

		Compte cmt = new Compte(cl);
		//double dispatch - etape 1 - envoi de message à un Compte
		System.out.println("Compte : " + cmt.prixLocation(lgv));
		
		Compte cmt2 = new CompteAvecReduction(cl);
		System.out.println("CompteReduction : " + cmt2.prixLocation(lgv));
		
		Compte cmt3 = new CompteAvecSeuil(cl); //seuil vaut 2
		System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv));
		System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv));
		System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv)); //doit valoir 0
		System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv));
		
		Produit r = new ProduitSolde("RockyIV", 10.0);
		System.out.println("Compte+ProduitSolde : " + cmt.prixLocation(r));
		System.out.println("CompteReduction+ProduitSolde : " + cmt2.prixLocation(r));
		}
}

class Produit{
	double prixBase;
	double TVA = 19.6;
	double marge = 1.10;
	String titre;
	
	Produit(String titre, double pb) {
		this.titre = titre;
		this.prixBase = pb;}
	
	protected double prixHT(){return prixBase * marge;}

	double prixVente(){return (this.prixHT() * (1 + TVA));}
	
	double prixLocation(){
		return this.prixVente() * 5 /100;
	}
}


class ProduitSolde extends Produit{
	ProduitSolde(String titre, double pb) {
		super(titre, pb);}
	
	double prixVente(){return (super.prixVente() / 2);}
}

class Client{
	protected String nom;
	protected Compte compte;
	public void setCompte(Compte c){compte = c;}
	public Compte getCompte(){return compte;}
	
	public Client(String nom){
		this(nom, null);}
	
	public Client(String nom, Compte c){
		this.nom = nom;
		this.compte = c;}
}

class Compte {
	static int compteur = 0;
	int numero;
	Client titulaire;

	public Compte (Client cl){
		titulaire = cl;
		cl.setCompte(this);
		this.numero = ++compteur;
	}
	
	public double prixLocation(Produit p){
		//double dispatch - etape 2 : envoi du message à un produit
		return p.prixLocation();}
}

class CompteAvecReduction extends Compte{
	//donne une reduction de 10% sur chaque location
	double reduction = 0.10;
	
	public CompteAvecReduction (Client cl){super(cl);}
	
	public double prixLocation(Produit p){
		return(super.prixLocation(p) * (1-reduction));}
}

class CompteAvecSeuil extends Compte{
	//donne une location gratuite après $compteur payantes
	static int init = 2;
	int compteur = init;
	//le compteur est incrémenté à chaque fois que le produit est loué
	//(méthode non écrite)
	
	public CompteAvecSeuil (Client cl) { super(cl); }
	
	public double prixLocation(Produit p){
		if (compteur-- == 0) 
			{compteur = init; return 0.0;}
		else return super.prixLocation(p);
	}
}





