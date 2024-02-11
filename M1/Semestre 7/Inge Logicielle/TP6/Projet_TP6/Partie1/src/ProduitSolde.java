public class ProduitSolde extends Produit{

        public ProduitSolde(){super();}
        public ProduitSolde(String nomProd, double prixLoc){
            super(nomProd,prixLoc);
        }

        @Override
        public double getPrixLoc() {
            return super.getPrixLoc()/2;
        }

}
