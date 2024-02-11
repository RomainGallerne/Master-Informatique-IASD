package climatiseur;

public class EtatFluxAir extends EtatAllume{
    public EtatFluxAir(Climatiseur clim) {
        super(clim);
    }

    @Override
    public void minuteur() {}

    @Override
    public void fluxAir() {
        System.out.println("Retour à l'accueil.");
        clim.setEtat(new EtatAccueil(clim));
    }

    public void incr() throws ClimatiseurException {
        if(clim.getVitesse() < 5){
            clim.setVitesse(clim.getVitesse()+1);
            System.out.print("Vitesse incrémenté à ");
            System.out.println(clim.getVitesse());
        } else {
            throw new ClimatiseurException("Vitesse déjà à 5 !");
        }
    }

    @Override
    public void decr() throws ClimatiseurException {
        if(clim.getVitesse() > 0){
            clim.setVitesse(clim.getVitesse()-1);
            System.out.print("Température décrémenté à ");
            System.out.println(clim.getVitesse());
        } else {
            throw new ClimatiseurException("Vitesse déjà 0 !");
        }
    }

    public String toString(){
        return "réglage flux d'air";
    }
}
