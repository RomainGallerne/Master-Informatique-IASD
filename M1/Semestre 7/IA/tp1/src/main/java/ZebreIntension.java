import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class ZebreIntension {

    public static void main(String[] args) {
        Model model = new Model("ZebreIntension");

        // Déclaration des variables
        IntVar Blue = model.intVar("Blue", 1, 5);
        IntVar Green = model.intVar("Green", 1, 5);
        IntVar Ivory = model.intVar("Ivory", 1, 5);
        IntVar Red = model.intVar("Red", 1, 5);
        IntVar Yellow = model.intVar("Yellow", 1, 5);

        IntVar English = model.intVar("English", 1, 5);
        IntVar Japanese = model.intVar("Japanese", 1, 5);
        IntVar Norwegian = model.intVar("Norwegian", 1, 5);
        IntVar Spanish = model.intVar("Spanish", 1, 5);
        IntVar Ukrainian = model.intVar("Ukrainian", 1, 5);

        IntVar Coffee = model.intVar("Coffee", 1, 5);
        IntVar Milk = model.intVar("Milk", 1, 5);
        IntVar OrangeJuice = model.intVar("Orange Juice", 1, 5);
        IntVar Tea = model.intVar("Tea", 1, 5);
        IntVar Water = model.intVar("Water", 1, 5);

        IntVar Dog = model.intVar("Dog", 1, 5);
        IntVar Fox = model.intVar("Fox", 1, 5);
        IntVar Horse = model.intVar("Horse", 1, 5);
        IntVar Snail = model.intVar("Snail", 1, 5);
        IntVar Zebra = model.intVar("Zebra", 1, 5);

        IntVar Chesterfield = model.intVar("Chesterfield", 1, 5);
        IntVar Kool = model.intVar("Kool", 1, 5);
        IntVar LuckyStrike = model.intVar("Lucky Strike", 1, 5);
        IntVar OldGold = model.intVar("Old Gold", 1, 5);
        IntVar Parliament = model.intVar("Parliament", 1, 5);

        // Contraintes
        model.arithm(English, "=", Red).post();
        model.arithm(Spanish, "=", Dog).post();
        model.arithm(Coffee, "=", Green).post();
        model.arithm(Ukrainian, "=", Tea).post();
        model.arithm(Green, "-", Ivory, "=", 1).post(); // Correction ici
        model.arithm(OldGold, "=", Snail).post();
        model.arithm(Kool, "=", Yellow).post();
        model.arithm(Milk, "=", 3).post();
        model.arithm(Norwegian, "=", 1).post();

        // Contraintes relatives avec la fonction abs
        IntVar diffChesterfieldFox = model.intVar("diffChesterfieldFox", 0, 5);
        model.distance(Chesterfield, Fox, "=", diffChesterfieldFox).post();
        model.arithm(diffChesterfieldFox, "=", 1).post();

        IntVar diffKoolHorse = model.intVar("diffKoolHorse", 0, 5);
        model.distance(Kool, Horse, "=", diffKoolHorse).post();
        model.arithm(diffKoolHorse, "=", 1).post();

        model.arithm(LuckyStrike, "=", OrangeJuice).post();
        model.arithm(Japanese, "=", Parliament).post();

        IntVar diffNorwegianBlue = model.intVar("diffNorwegianBlue", 0, 5);
        model.distance(Norwegian, Blue, "=", diffNorwegianBlue).post();
        model.arithm(diffNorwegianBlue, "=", 1).post();

        // Toutes les variables doivent être différentes dans leurs catégories respectives
        model.allDifferent(new IntVar[]{Blue, Green, Ivory, Red, Yellow}).post();
        model.allDifferent(new IntVar[]{English, Japanese, Norwegian, Spanish, Ukrainian}).post();
        model.allDifferent(new IntVar[]{Coffee, Milk, OrangeJuice, Tea, Water}).post();
        model.allDifferent(new IntVar[]{Dog, Fox, Horse, Snail, Zebra}).post();
        model.allDifferent(new IntVar[]{Chesterfield, Kool, LuckyStrike, OldGold, Parliament}).post();

        // Résolution et affichage
        if (model.getSolver().solve()) {
            System.out.println("Solution trouvée :");
            System.out.println(model);
        } else {
            System.out.println("Pas de solution");
        }

        // Affichage de l'ensemble des caractéristiques de résolution
        //calcul du temps d'exécution
        // Calcul de la première solution
        if(model.getSolver().solve()) {
        	System.out.println("\n\n*** Première solution ***");        
        	System.out.println(model);
        }

        // Calcul de toutes les solutions
    	System.out.println("\n\n*** Autres solutions ***");        
        while(model.getSolver().solve()) {    	
            System.out.println("Sol "+ model.getSolver().getSolutionCount()+"\n"+model);
	    }
	    
 
        
        // Affichage de l'ensemble des caractéristiques de résolution
      	System.out.println("\n\n*** Bilan ***");        
        model.getSolver().printStatistics();

    }
}

// 1 et 2
/*La solution obtenue avec le modèle intensionnel est la même que celle obtenue avec le modèle extensionnel. Par conséquent, les deux modèles sont correctement formulés et cohérents entre eux. */
