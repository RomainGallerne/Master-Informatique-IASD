import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Partie1 {
    private Bidon[] bidons;
    private int volumeBut;
    private Stack<Commande> suiteAction = new Stack<Commande>();
    private String[] entrees;

    public Partie1(int nombre_de_bidon, int[] capacite_des_bidons, int volume_a_atteindre){
        volumeBut = volume_a_atteindre;
        bidons = new Bidon[nombre_de_bidon];
        for(int i=0;i<nombre_de_bidon;i++){
            bidons[i] = new Bidon(capacite_des_bidons[i]);
        }
        entrees = new String[]{
                "Remplir",
                "Vider",
                "Transvaser"
        };
    }

    public void jouer() throws BidonExcpetion {
        Scanner sc = new Scanner(System.in);
        String str; int nb1; int nb2; Commande commande; Commande com;
        while (bidons[0].getVolumeEffectif() != volumeBut) {
            System.out.println("Volume à atteindre : "+volumeBut);
            System.out.println("Etats des bidons : ");
            for(int i=0;i<bidons.length;i++){
                System.out.println("Bidon "+i+" : "+bidons[i].getVolumeEffectif()+"/"+bidons[i].getVolumeMax());
            }
            System.out.println("Entrez une commande (Remplir, Vider, Transvaser, Annuler) : ");
            str = sc.nextLine();

            switch(str){
                case "Remplir":
                    System.out.println("Entrez le numéro du bidon à remplir :");
                    nb1 = Integer.parseInt(sc.nextLine());
                    commande = new CommandeRemplir(bidons[nb1]);
                    commande.executer();
                    suiteAction.push(commande);
                    break;
                case "Vider":
                    System.out.println("Entrez le numéro du bidon à vider :");
                    nb1 = Integer.parseInt(sc.nextLine());
                    commande = new CommandeVider(bidons[nb1]);
                    commande.executer();
                    suiteAction.push(commande);
                    break;
                case "Transvaser":
                    System.out.println("Entrez le numéro du bidon verseur :");
                    nb1 = Integer.parseInt(sc.nextLine());
                    System.out.println("Entrez le numéro du bidon dans lequel verser :");
                    nb2 = Integer.parseInt(sc.nextLine());
                    commande = new CommandeTransvaser(bidons[nb1],bidons[nb2]);
                    commande.executer();
                    suiteAction.push(commande);
                    break;
                case "Annuler":
                    Stack<Commande> sauvegarde = new Stack<Commande>();
                    com = suiteAction.pop();
                    while(com == null || com instanceof CommandeAnnuler){
                        sauvegarde.push(com);
                        com = suiteAction.pop();
                    }
                    commande = new CommandeAnnuler(com);
                    commande.executer();
                    while(!sauvegarde.isEmpty()){
                        suiteAction.push(sauvegarde.pop());
                    }
                    suiteAction.push(commande);
                    break;
                case "Refaire":
                    commande = suiteAction.pop();
                    if(!(commande instanceof CommandeAnnuler)){
                        System.out.println("Commande Incorrect");
                        suiteAction.push(commande);
                        break;
                    }
                    else {
                        commande.undo();
                        suiteAction.push(((CommandeAnnuler) commande).getCommande());
                        break;
                    }
                default:
                    System.out.println("Commande Incorrect");break;
            }

            System.out.println(suiteAction.toString());
        }
        System.out.println("Gagné ! Fin du Jeu");
        System.out.println("Commandes réalisés :");
        System.out.println(suiteAction.toString());
    }


}
