public abstract class Commande {
    protected Bidon bidon;
    protected int volDeplace;

    public Commande(Bidon bidon) {
        this.bidon = bidon;
    }

    public Commande(){}

    public abstract void executer() throws BidonExcpetion;

    protected void undo() throws BidonExcpetion{}
    public String toString(){
        return (this.getClass()+"");
    }
}
