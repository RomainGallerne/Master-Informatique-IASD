public class CommandeVider extends Commande{

    public CommandeVider(Bidon bidon) {
        super(bidon);
    }

    public void executer() throws BidonExcpetion {
        volDeplace = bidon.getVolumeEffectif();
        bidon.setVolumeEffectif(0);
    }

    public void undo() throws BidonExcpetion {
        bidon.setVolumeEffectif(bidon.getVolumeEffectif()+volDeplace);
    }
}
