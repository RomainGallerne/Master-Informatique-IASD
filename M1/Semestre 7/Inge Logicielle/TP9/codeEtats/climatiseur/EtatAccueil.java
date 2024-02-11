package climatiseur;

public class EtatAccueil extends EtatAllume{
    public EtatAccueil(Climatiseur clim) {
        super(clim);
    }

    @Override
    public void minuteur() {
        System.out.println("Réglage du minuteur.");
        clim.setEtat(new EtatMinuteur(clim));
    }

    @Override
    public void fluxAir() {
        System.out.println("Réglage du flux d'air.");
        clim.setEtat(new EtatFluxAir(clim));
    }

    @Override
    public void incr() throws ClimatiseurException {
        if(clim.getTemperature() < 30){
            clim.setTemperature(clim.getTemperature()+1);
            System.out.print("Température incrémenté à ");
            System.out.println(clim.getTemperature());
        } else {
            throw new ClimatiseurException("Il fait déjà 30°C !");
        }
    }

    @Override
    public void decr() throws ClimatiseurException {
        if(clim.getTemperature() > 0){
            clim.setTemperature(clim.getTemperature()-1);
            System.out.print("Température décrémenté à ");
            System.out.println(clim.getTemperature());
        } else {
            throw new ClimatiseurException("Il fait déjà 0°C !");
        }
    }

    public String toString(){
        return "accueil";
    }
}
