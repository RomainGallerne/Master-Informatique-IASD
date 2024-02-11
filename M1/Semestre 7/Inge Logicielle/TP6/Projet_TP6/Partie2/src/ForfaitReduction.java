public class ForfaitReduction extends Decorateur{
    private double reduction;

    public ForfaitReduction(Compte cmt){
        super(cmt);
        this.reduction = 10;
    }
    public ForfaitReduction(Compte cmt, double reduction){
        super(cmt);
        this.reduction = reduction;
    }

    @Override
    public double prixLocation(Produit p){
        return (1.0-(getReduction()/100.0)) * super.prixLocation(p);
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
