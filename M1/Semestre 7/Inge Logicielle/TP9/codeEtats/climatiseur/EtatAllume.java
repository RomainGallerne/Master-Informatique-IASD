package climatiseur;

public abstract class EtatAllume extends EtatClimatiseur{

    public EtatAllume(Climatiseur clim) {
        super(clim);
    }

    @Override
    public void onOff() {
        System.out.println("Arrêt du Climatiseur.");
        desarmerMinuteur();
        clim.setEtat(new EtatEteint(clim));
    }

    @Override
    public void accueil() {
        System.out.println("Retour à l'accueil.");
        clim.armerMinuteur();
        clim.setEtat(new EtatAccueil(clim));
    }

    @Override
    public void desarmerMinuteur(){
        clim.desarmerMinuteur();
    }

    @Override
    public void armerMinuteur(){
        clim.armerMinuteur();
    }
}
