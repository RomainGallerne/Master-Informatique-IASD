public class CommandeRemplir extends Commande{


    public CommandeRemplir(Bidon bidon) {
        super(bidon);
    }

    public void executer() throws BidonExcpetion {
        volDeplace = bidon.getVolumeMax()-bidon.getVolumeEffectif();
        bidon.setVolumeEffectif(bidon.getVolumeMax());
    }

    public void undo() throws BidonExcpetion{
        bidon.setVolumeEffectif(bidon.getVolumeEffectif()-volDeplace);
    }
}
