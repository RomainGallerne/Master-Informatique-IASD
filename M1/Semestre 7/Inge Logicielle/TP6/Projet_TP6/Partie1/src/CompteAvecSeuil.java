public class CompteAvecSeuil extends Compte{
    private int seuil;
    private int nbLocation = 0;

    public CompteAvecSeuil(){}
    public CompteAvecSeuil(Client cl){
        super(cl);
        this.seuil=2;
    }
    public CompteAvecSeuil(Client cl, int seuil){
        super(cl);
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
        if(seuilAtteint()){
            nbLocation -= seuil;
            nbLocation ++;
            return 0.0;
        }
        else {
            nbLocation ++;
            return p.getPrixLoc();
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

