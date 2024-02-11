package climatiseur;

public class EtatMinuteur extends EtatAllume{
    public EtatMinuteur(Climatiseur clim) {
        super(clim);
    }

    @Override
    public void minuteur() {
        System.out.println("Retour à l'accueil.");
        clim.setEtat(new EtatAccueil(clim));
    }

    @Override
    public void fluxAir() {}

    @Override
    public void incr() throws ClimatiseurException {
        if(clim.getMinuteur() < 480){
            clim.setMinuteur(clim.getMinuteur()+60);
            System.out.print("Minuteur incrémenté à ");
            System.out.println(clim.getMinuteur());
        } else {
            throw new ClimatiseurException("Minuteur déjà à 8h !");
        }
    }

    @Override
    public void decr() throws ClimatiseurException {
        if(clim.getMinuteur() > 0){
            clim.setMinuteur(clim.getMinuteur()-60);
            System.out.print("Minuteur décrémenté à ");
            System.out.println(clim.getMinuteur());
        } else {
            throw new ClimatiseurException("Minuteur déjà 0h !");
        }
    }

    public String toString(){
        return "réglage minuteur";
    }
}
