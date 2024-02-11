public class Main {
    public static void main(String[] args) {
        Produit lgv = new Produit("La grande vadrouille", 10.0);
        Client cl = new Client("Dupont");
        //un compte normal pour le client Dupont
        Compte cmt = new CompteBasique(cl);
        System.out.println("prix basique : " + cmt.prixLocation(lgv));
        //Dupont achete un forfait réduction.
        cmt = new ForfaitReduction(cmt);
        System.out.println("prix réduction : " + cmt.prixLocation(lgv));
        //Dupont achete en plus un forfait seuil, le seuil est à 2
        cmt = new ForfaitSeuil(cmt);
        System.out.println("Seuil1+prixReduction : " + cmt.prixLocation(lgv));
        System.out.println("Seuil2+prixReduction : " + cmt.prixLocation(lgv));
        System.out.println("Seuil3+prixReduction : " + cmt.prixLocation(lgv)); //rend 0
        //Dupont avec ses 2 forfaits loue un produit soldé
        Produit r4 = new ProduitSolde("RockyIV", 10.0);
        System.out.println("Seuil1+prixReduction+produitSolde : " + cmt.prixLocation(r4));
        System.out.println("Seuil2+prixReduction+produitSolde : " + cmt.prixLocation(r4));
        System.out.println("Seuil3+prixReduction+produitSolde : " + cmt.prixLocation(r4));
    }
}