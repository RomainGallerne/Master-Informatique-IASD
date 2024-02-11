package climatiseur;

public class EtatEteint extends EtatClimatiseur{
    public EtatEteint(Climatiseur clim) {
        super(clim);
    }

    @Override
    public void onOff() {
        System.out.println("Allumage du Climatiseur.");
        clim.setMinuteur(60);
        clim.armerMinuteur();
        clim.setEtat(new EtatAccueil(clim));
    }

    @Override
    public void accueil() {}

    @Override
    public void minuteur() {}

    @Override
    public void fluxAir() {}

    @Override
    public void armerMinuteur() {}

    @Override
    public void desarmerMinuteur() {}

    @Override
    public void incr() throws ClimatiseurException {}

    @Override
    public void decr() throws ClimatiseurException {}

    public String toString(){
        return "éteint";
    }
}
