public class CompteAvecReduction extends Compte{
    private double reduction;

    public CompteAvecReduction(){super();}
    public CompteAvecReduction(Client cl){
        super(cl);
        this.reduction = 10;
    }
    public CompteAvecReduction(Client cl, double reduction){
        super(cl);
        this.reduction = reduction;
    }

    @Override
    public double prixLocation(Produit p){
        return (1.0-(getReduction()/100.0)) * p.getPrixLoc();
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(int reduction) {
        this.reduction = reduction;
    }

    @Override
    public String toString() {
        return "CompteAvecReduction{" +
                "cl=" + getCl() +
                "reduction=" + reduction +
                '}';
    }
}
