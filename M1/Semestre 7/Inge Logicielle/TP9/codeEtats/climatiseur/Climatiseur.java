package climatiseur;

import java.util.Timer;
import java.util.TimerTask;

public class Climatiseur {
    private EtatClimatiseur etat;
    private int temperature = 21;
    private int vitesse = 1;
    private int minuteur = 60;

    public Climatiseur() {
        this.etat = new EtatEteint(this);
    }

    public void onOff(){
        etat.onOff();
    }

    public void accueil(){
        etat.accueil();
    }

    public void minuteur(){
        etat.minuteur();
    }

    public void fluxAir(){
        etat.fluxAir();
    }

    public Timer armerMinuteur() {
        TimerTask task = new TimerTask() {
            public void run() {
                minuteur -= 60;
                onOff();
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, (long)minuteur*100L);
        if(minuteur <= 0){timer.cancel();minuteur=60;}

        return timer;
    }

    public void desarmerMinuteur(){

    }

    public void incr() throws ClimatiseurException {
        try{etat.incr();}
        catch (ClimatiseurException ce) {
            System.out.println(ce.getLocalizedMessage());}
    }

    public void decr() throws ClimatiseurException{
        try{etat.decr();}
        catch (ClimatiseurException ce) {
            System.out.println(ce.getLocalizedMessage());}
    }

    public EtatClimatiseur getEtat() {
        return etat;
    }

    public void setEtat(EtatClimatiseur etat) {
        this.etat = etat;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public int getMinuteur() {
        return minuteur;
    }

    public void setMinuteur(int minuteur) {
        this.minuteur = minuteur;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void affiche() {
        System.out.println("Climatiseur{" +
                "etat=" + etat.toString() +
                ", temperature=" + temperature +
                ", vitesse=" + vitesse +
                ", minuteur=" + minuteur +
                '}');
    }
}
