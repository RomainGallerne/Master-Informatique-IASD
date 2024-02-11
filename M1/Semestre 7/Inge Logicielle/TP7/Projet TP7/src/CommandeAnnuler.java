public class CommandeAnnuler extends Commande {
    private Commande commande;

    public CommandeAnnuler(Commande commande){
        super();
        this.commande = commande;
    }
    @Override
    public void executer() throws BidonExcpetion {
        commande.undo();
    }

    public void undo() throws BidonExcpetion {
        commande.executer();
    }

    public Commande getCommande(){
        return this.commande;
    }
}
