public class Produit {
    private String nomProd;
    private double prixLoc;

    public Produit(){}
    public Produit(String nomProd, double prixLoc){
        this.nomProd = nomProd;
        this.prixLoc = prixLoc;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public double getPrixLoc() {
        return prixLoc;
    }

    public void setPrixLoc(double prixLoc) {
        this.prixLoc = prixLoc;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "nomProd='" + nomProd + '\'' +
                ", prixLoc=" + prixLoc +
                '}';
    }
}
