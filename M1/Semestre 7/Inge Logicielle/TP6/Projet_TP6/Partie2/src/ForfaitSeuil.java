public class ForfaitSeuil extends Decorateur{
    private int seuil;
    private int nbLocation = 0;

    public ForfaitSeuil(Compte cmt){
        super(cmt);
        this.seuil=2;
    }
    public ForfaitSeuil(Compte cmt, int seuil){
        super(cmt);
        this.seuil = seuil;
    }

    public void incrementLocation(){
        this.nbLocation++;
    }

    public void decrementLocation(){
        this.nbLocation--;
    }

    @Override
    public double prixLocation(Produit p){
        double prix;
        if(seuilAtteint()){
            nbLocation -= seuil;
            return 0.0;
        }
        else {
            nbLocation ++;
            return super.prixLocation(p);
        }
    }

    public boolean seuilAtteint(){
        if(seuil <= nbLocation){
            return true;
        }
        else {return false;}
    }

    public int getSeuil() {
        return seuil;
    }

    public void setSeuil(int seuil) {
        this.seuil = seuil;
    }

    public int getNbLocation() {
        return nbLocation;
    }

    public void setNbLocation(int nbLocation) {
        this.nbLocation = nbLocation;
    }

    @Override
    public String toString() {
        return "CompteAvecReduction{" +
                "cl=" + getCl() +
                "seuil=" + seuil +
                "nbLocation=" + nbLocation +
                '}';
    }
}

