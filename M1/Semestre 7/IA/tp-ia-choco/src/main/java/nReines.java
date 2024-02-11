import java.util.Scanner;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.constraints.extension.Tuples;

public class nReines {
public static void main(String[] args) {
		
		// Création du modele
		Model model = new Model("nReines");
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir n :");
		int n = sc.nextInt();
		
		// Création des variables
		IntVar [] R = model.intVarArray("x",n,1,n);      

	    
	    // Création des contraintes  
		model.allDifferent(R);
        
        for(int i=0;i<n;i++) {
        	for(int j=0;j<n;j++) {
        		if(i!=j) {
        			model.arithm(Math.abs(R[i]-R[j]),"!=",Math.abs(i-j)).post();
        		}
        	}
        }
        
        model.table(new IntVar[]{blu,gre}, tuplesInterdits).post();
        // création d'une contrainte en extension de portée <blu,gre>
        // dont les tuples autorisés/interdits sont données par tuplesInterdits
        model.table(new IntVar[]{blu,ivo}, tuplesInterdits).post();
        model.table(new IntVar[]{blu,red}, tuplesInterdits).post();
        model.table(new IntVar[]{blu,yel}, tuplesInterdits).post();
        model.table(new IntVar[]{gre,ivo}, tuplesInterdits).post();
        model.table(new IntVar[]{gre,red}, tuplesInterdits).post();
        model.table(new IntVar[]{gre,yel}, tuplesInterdits).post();
        model.table(new IntVar[]{ivo,red}, tuplesInterdits).post();
        model.table(new IntVar[]{ivo,yel}, tuplesInterdits).post();
        model.table(new IntVar[]{red,yel}, tuplesInterdits).post();

        model.table(new IntVar[]{eng,jap}, tuplesInterdits).post();
        model.table(new IntVar[]{eng,nor}, tuplesInterdits).post();
        model.table(new IntVar[]{eng,spa}, tuplesInterdits).post();
        model.table(new IntVar[]{eng,ukr}, tuplesInterdits).post();
        model.table(new IntVar[]{jap,nor}, tuplesInterdits).post();
        model.table(new IntVar[]{jap,spa}, tuplesInterdits).post();
        model.table(new IntVar[]{jap,ukr}, tuplesInterdits).post();
        model.table(new IntVar[]{nor,spa}, tuplesInterdits).post();
        model.table(new IntVar[]{nor,ukr}, tuplesInterdits).post();
        model.table(new IntVar[]{spa,ukr}, tuplesInterdits).post();

        model.table(new IntVar[]{cof,mil}, tuplesInterdits).post();
        model.table(new IntVar[]{cof,ora}, tuplesInterdits).post();
        model.table(new IntVar[]{cof,tea}, tuplesInterdits).post();
        model.table(new IntVar[]{cof,wat}, tuplesInterdits).post();
        model.table(new IntVar[]{mil,ora}, tuplesInterdits).post();
        model.table(new IntVar[]{mil,tea}, tuplesInterdits).post();
        model.table(new IntVar[]{mil,wat}, tuplesInterdits).post();
        model.table(new IntVar[]{ora,tea}, tuplesInterdits).post();
        model.table(new IntVar[]{ora,wat}, tuplesInterdits).post();
        model.table(new IntVar[]{tea,wat}, tuplesInterdits).post();

        model.table(new IntVar[]{dog,fox}, tuplesInterdits).post();
        model.table(new IntVar[]{dog,hor}, tuplesInterdits).post();
        model.table(new IntVar[]{dog,sna}, tuplesInterdits).post();
        model.table(new IntVar[]{dog,zeb}, tuplesInterdits).post();
        model.table(new IntVar[]{fox,hor}, tuplesInterdits).post();
        model.table(new IntVar[]{fox,sna}, tuplesInterdits).post();
        model.table(new IntVar[]{fox,zeb}, tuplesInterdits).post();
        model.table(new IntVar[]{hor,sna}, tuplesInterdits).post();
        model.table(new IntVar[]{hor,zeb}, tuplesInterdits).post();
        model.table(new IntVar[]{sna,zeb}, tuplesInterdits).post();

        model.table(new IntVar[]{che,koo}, tuplesInterdits).post();
        model.table(new IntVar[]{che,luc}, tuplesInterdits).post();
        model.table(new IntVar[]{che,old}, tuplesInterdits).post();
        model.table(new IntVar[]{che,par}, tuplesInterdits).post();
        model.table(new IntVar[]{koo,luc}, tuplesInterdits).post();
        model.table(new IntVar[]{koo,old}, tuplesInterdits).post();
        model.table(new IntVar[]{koo,par}, tuplesInterdits).post();
        model.table(new IntVar[]{luc,old}, tuplesInterdits).post();
        model.table(new IntVar[]{luc,par}, tuplesInterdits).post();
        model.table(new IntVar[]{old,par}, tuplesInterdits).post();
        
        /************************************************************************
         *                                                                      *
         *                        Contraintes simples                           *
         *                                                                      *
         ************************************************************************/
        
        model.table(new IntVar[]{eng,red}, tuplesAutorises).post(); //2
        model.table(new IntVar[]{spa,dog}, tuplesAutorises).post(); //3
        model.table(new IntVar[]{cof,gre}, tuplesAutorises).post(); //4
        model.table(new IntVar[]{ukr,tea}, tuplesAutorises).post(); //5
        model.table(new IntVar[]{old,sna}, tuplesAutorises).post(); //7
        model.table(new IntVar[]{koo,yel}, tuplesAutorises).post(); //8
        model.table(new IntVar[]{luc,ora}, tuplesAutorises).post(); //13
        model.table(new IntVar[]{jap,par}, tuplesAutorises).post(); //14
        
        /************************************************************************
         *                                                                      *
         *                  Contraintes de positions fixes                      *
         *                                                                      *
         ************************************************************************/
        int [][] t9 = new int[][] {{3}};
        Tuples tuplesAutorises9 = new Tuples(t9,true);
        model.table(new IntVar[]{mil}, tuplesAutorises9).post(); //9
        
        int [][] t10 = new int[][] {{1}};
        Tuples tuplesAutorises10 = new Tuples(t10,true);
        model.table(new IntVar[]{nor}, tuplesAutorises10).post(); //10
        
        /************************************************************************
         *                                                                      *
         *               Contraintes de positions relatives                     *
         *                                                                      *
         ************************************************************************/
        
        int [][] tSuiv = new int[][] {{1,2},{2,3},{3,4},{4,5}};
        int [][] tNext = new int[][] {{1,2},{2,3},{3,4},{4,5},{2,1},{3,2},{4,3},{5,4}};
        Tuples tuplesAutorisesSuiv = new Tuples(tSuiv,true);
        Tuples tuplesAutorisesNext = new Tuples(tNext,true);
        
        model.table(new IntVar[]{ivo,gre}, tuplesAutorisesSuiv).post(); //6
        model.table(new IntVar[]{che,fox}, tuplesAutorisesNext).post(); //11
        model.table(new IntVar[]{koo,hor}, tuplesAutorisesNext).post(); //12
        model.table(new IntVar[]{nor,blu}, tuplesAutorisesNext).post(); //15
        
        
        
        // Affichage du réseau de contraintes créé
        System.out.println("*** Réseau Initial ***");
        System.out.println(model);
        

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
