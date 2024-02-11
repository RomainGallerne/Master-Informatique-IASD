public class Bidon {
    private int volumeMax;
    private int volumeEffectif=0;

    public Bidon(int volume_du_bidon){
        volumeMax = volume_du_bidon;
    }

    public int getVolumeEffectif() {
        return volumeEffectif;
    }

    public void setVolumeEffectif(int volumeEff) throws BidonExcpetion{
        if(volumeEff > volumeMax || volumeEff < 0){
            throw new BidonExcpetion();
        } else {
            this.volumeEffectif = volumeEff;
        }
    }

    public int getVolumeMax() {
        return volumeMax;
    }

    public void setVolumeMax(int volumeMax) {
        this.volumeMax = volumeMax;
    }
}
