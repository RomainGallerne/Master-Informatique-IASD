public abstract class Compte {
    private Client cl;

    public Compte(){}
    public Compte(Client cl){
        this.cl = cl;
    }

    public double prixLocation(Produit p){
        return p.getPrixLoc();
    }

    public Client getCl() {
        return cl;
    }

    public void setCl(Client cl) {
        this.cl = cl;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "cl=" + cl +
                '}';
    }
}
