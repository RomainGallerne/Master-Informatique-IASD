public class Decorateur extends Compte{
    Compte decore;

    public Decorateur(Compte cmt){
        this.decore = cmt;
    }
    public double prixLocation(Produit p){
        return decore.prixLocation(p);
    }
}
