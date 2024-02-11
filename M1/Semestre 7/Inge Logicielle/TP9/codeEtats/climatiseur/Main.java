package climatiseur;

import calculettePrefixe.Calculette;

public class Main {
    public static void main(String[] args) throws ClimatiseurException {
        Climatiseur c = new Climatiseur();
        c.affiche(); //Attendu : éteint,21,1,60
        c.onOff();
        c.decr();
        c.decr();
        c.affiche(); //Attendu : accueil,19,1,60
        c.minuteur();
        c.incr();
        c.incr();
        c.incr();
        c.affiche(); //Attendu : minuteur,19,1,240
        c.fluxAir();
        c.affiche(); //Attendu : minuteur,19,1,240
        c.accueil();
        c.incr();
        c.affiche(); //Attendu : accueil,20,1,240
        c.fluxAir();
        c.decr();
        c.decr();
        c.affiche(); //Attendu : fluxAir,20,0,240
        c.onOff();
        c.affiche(); //Attendu : eteint,20,0,-1
    }
}
