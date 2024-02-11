package climatiseur;

public abstract class EtatClimatiseur {
    protected Climatiseur clim;

    public EtatClimatiseur(Climatiseur clim) {
        this.clim = clim;
    }

    public Climatiseur getClim() {
        return clim;
    }

    public void setClim(Climatiseur clim) {
        this.clim = clim;
    }

    public abstract void onOff();
    public abstract void accueil();
    public abstract void minuteur();
    public abstract void fluxAir();
    public abstract void armerMinuteur();
    public abstract void desarmerMinuteur();
    public abstract void incr() throws ClimatiseurException;
    public abstract void decr() throws ClimatiseurException;

}
