public class CommandeTransvaser extends Commande{
    private Bidon bidonReceveur;

    public CommandeTransvaser(Bidon bidon_verseur,Bidon bidon_receveur) {
        super(bidon_verseur);
        this.bidonReceveur = bidon_receveur;
    }

    public void executer() throws BidonExcpetion {
        volDeplace = Math.min(
          bidon.getVolumeEffectif(),
          bidonReceveur.getVolumeMax()-bidonReceveur.getVolumeEffectif()
        );

        bidonReceveur.setVolumeEffectif(
                bidonReceveur.getVolumeEffectif()+volDeplace
        );
        bidon.setVolumeEffectif(bidon.getVolumeEffectif()-volDeplace);
    }

    public void undo() throws BidonExcpetion{
        bidonReceveur.setVolumeEffectif(
                bidonReceveur.getVolumeEffectif()-volDeplace
        );
        bidon.setVolumeEffectif(bidon.getVolumeEffectif()+volDeplace);
    }
}
