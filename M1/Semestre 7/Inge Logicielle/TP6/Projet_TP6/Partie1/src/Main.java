public class Main {
    public static void main(String[] args) {
        Produit lgv = new Produit("La grande vadrouille", 10.0);
        Client cl = new Client("Dupont");
        Compte cmt = new Compte(cl);

        System.out.println("-------------Partie I--------------");
        System.out.println("-------------P1 - Question 1/2--------------");
        System.out.println(lgv);
        System.out.println(cl);
        System.out.println(cmt);
        System.out.println(cmt.prixLocation(lgv));

        System.out.println("--------------P1 - Question 3---------------");
        Compte cmt2 = new CompteAvecReduction(cl);
        System.out.println("CompteReduction : " + cmt2.prixLocation(lgv));
        Compte cmt3 = new CompteAvecSeuil(cl); // le seuil est à 2 par défaut
        System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv));
        System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv));
        System.out.println("CompteSeuil : " + cmt3.prixLocation(lgv)); // doit afficher 0
        Produit r4 = new ProduitSolde("RockyIV", 10.0);
        System.out.println("CompteNormal+ProduitSoldé : " + cmt.prixLocation(r4));
        System.out.println("CompteReduction+ProduitSoldé : " + cmt2.prixLocation(r4));

    }
}