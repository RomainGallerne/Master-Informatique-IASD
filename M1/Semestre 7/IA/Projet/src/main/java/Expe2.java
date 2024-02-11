import java.io.*;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;
import org.jgrapht.alg.util.Pair;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Expe2 {
	
	private static String afficheTemps(int secondes) {
		int m=0, h=0, s=secondes;
		while(s>=60) {
			m++; s-=60;
			while(m>=60) {
				h++; m-=60;
			}
		}
		if(h>0) {return h+"H"+m+"min";}
		else {return m+"min";}
		
	}

	private static Model lireReseau(BufferedReader in) throws Exception{
		Model model = new Model("Expe");
		int nbVariables = Integer.parseInt(in.readLine());				// le nombre de variables
		int tailleDom = Integer.parseInt(in.readLine());				// la valeur max des domaines
		IntVar []var = model.intVarArray("x",nbVariables,0,tailleDom-1); 	
		int nbConstraints = Integer.parseInt(in.readLine());			// le nombre de contraintes binaires		
		for(int k=1;k<=nbConstraints;k++) { 
			String chaine[] = in.readLine().split(";");
			IntVar portee[] = new IntVar[]{var[Integer.parseInt(chaine[0])],var[Integer.parseInt(chaine[1])]}; 
			int nbTuples = Integer.parseInt(in.readLine());				// le nombre de tuples		
			Tuples tuples = new Tuples(new int[][]{},true);
			for(int nb=1;nb<=nbTuples;nb++) { 
				chaine = in.readLine().split(";");
				int t[] = new int[]{Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1])};
				tuples.add(t);
			}
			model.table(portee,tuples).post();	
		}
		in.readLine();
		return model;
	}	
	
	private static Pair<Long,Long> Taux(String nomFichier,double nbreReseaux) throws Exception {
        double nbReseauSat = 0;
        long timeout = 0;
        String limite = "10s";
        BufferedReader readFile = new BufferedReader(new FileReader(nomFichier));
        for(int nb=1 ; nb<=nbreReseaux; nb++) {
            Model model=lireReseau(readFile);
            if(model==null) {
                System.out.println("Problème de lecture de fichier !\n");
                return null;
            }
            
            Solver solver = model.getSolver();
            solver.limitTime(limite);
            
            if (model.getSolver().solve()){
                nbReseauSat++;    // le solveur a trouvé une solution donc on augmente le compteur du nbre de réseaux satisfaits
            } else if (model.getSolver().isStopCriterionMet()){
                timeout++;
                System.out.println("Le solveur n'a pas trouvé s'il y avait une solution ou pas avant d'atteindre la limite de temps ("+limite+").");
            } else {
                System.out.println("Le solveur a trouvé qu'il n'y a pas de solution.");
            }
        }
        
        //System.out.println("Nombre de fois où le solveur a mis plus de "+limite+" pour trouver une solution : "+timeout);
        long t = (long) ((nbReseauSat/nbreReseaux)*100.0);
        Pair<Long,Long> r = new Pair<Long,Long>(t,timeout);
        return r;
    }
	
	private static void csvGenerateur(int nbContraintes,int nbTuples,int nbRes) throws Exception {
		ThreadMXBean thread = ManagementFactory.getThreadMXBean();
		
		//Initialisation du fichier CSV
	    File fCSV = new File("benchmark/densite-"+Math.round((double)nbContraintes*100.0/435.0)+"/CSV"+nbTuples+"tuples"+nbRes+"res.csv");
	    BufferedWriter bwCSV = new BufferedWriter(new FileWriter(fCSV));
	    bwCSV.write("NbTuples,TauxResSatis,TpsExecMoyen(ms),TimeOuts");
	    bwCSV.newLine();
	    
	    //Remplissage du fichier CSV
	    int t=150;
	    String f;
	    long tauxSolMoyen,nbTimeOutTotal;
	    Pair<Long, Long> retourTauxSol;
	    double[] Tps = new double[5];
	    long debutCPU,debutUser;
	    double maxTps,minTps,moyenTps,sum;
	    while(t<=nbTuples) {
	    	tauxSolMoyen=0;
	    	nbTimeOutTotal=0;
	    	f = "CSP/nbContraintes-"+nbContraintes+"/csp-30-20-"+nbContraintes+"-"+nbTuples+"-"+nbRes+".txt";
	    	for(int i=0;i<5;i++) {
	    		debutCPU=thread.getCurrentThreadCpuTime();
	    		debutUser= thread.getCurrentThreadUserTime();
	    		
	    		retourTauxSol = Taux(f,nbRes);
	    		
	    		Tps[i] = (thread.getCurrentThreadCpuTime()-debutCPU)-(thread.getCurrentThreadUserTime()-debutUser);
	    		tauxSolMoyen += retourTauxSol.getFirst();
	    		nbTimeOutTotal += retourTauxSol.getSecond();
	    	}
	    	maxTps = Math.max(Math.max(Math.max(Math.max(Tps[0],Tps[1]),Tps[2]),Tps[3]),Tps[4]);
	    	minTps = Math.min(Math.min(Math.min(Math.min(Tps[0],Tps[1]),Tps[2]),Tps[3]),Tps[4]);
	    	sum=0.0;
	    	for(double tp : Tps) {sum+=tp;}
	    	moyenTps = (sum-(maxTps+minTps))/3.0;
	    	tauxSolMoyen /= 5;
	    	
	    	bwCSV.write(t+","+tauxSolMoyen+","+Math.round(moyenTps*Math.pow(10.0,-6.0))+","+(int)nbTimeOutTotal); //nbTuples,taux de réseaux satisfaits en moyenne, temps de résolution moyen
	    	bwCSV.newLine();
	        t+=5;
	    }
	    bwCSV.close();
	}
	
	private static void genToutCSV(int[] tabContraintes, int nbTuples, int[] tabRes) throws Exception{
		//Génération de tous les CSV possibles
		int progression = 0;
		for(int nbContr : tabContraintes) {
			System.out.println("-> Génération des CSV de densité "+Math.round((double)nbContr*100.0/435.0));
			for(int nbRes : tabRes) {
				csvGenerateur(nbContr,nbTuples,nbRes);
				progression ++;
				System.out.println("	Progression : "+(progression*100)/(tabContraintes.length*tabRes.length)+"%.");
			}
		}
		System.out.println("Tous les fichiers CSV ont été générés avec succès.");
	}
			
	
	
	public static void main(String[] args) throws Exception{
		int nbRes=3;
		
		
		System.out.println("<-------------------TEST DE BENCH------------------------->");
		String ficName = "bench.txt";
		BufferedReader readFile = new BufferedReader(new FileReader(ficName));
		for(int nb=1 ; nb<=nbRes; nb++) {
			Model model=lireReseau(readFile);
			if(model==null) {
				System.out.println("Problème de lecture de fichier !\n");
				return;
			}
			System.out.println("Réseau lu "+nb+" :\n"+model.getSolver().solve()+"\n\n");
		}
		
		System.out.println("<-------------------TEST DE BENCHSATISF ET BENCHINSAT------------------------->");
		
		//Test du programme sur les fichiers "benchSatisf" et "benchInsat"
		String [] file = {"benchSatisf.txt","benchInsat.txt","bench.txt"};
		int nbSat;
		for (String f : file) {
			BufferedReader buff = new BufferedReader(new FileReader(f));
			nbSat = 0;
			for(int nb=1 ; nb<=nbRes; nb++) {
				Model model=lireReseau(buff);
				if(model==null) {
					System.out.println("Problème de lecture de fichier !\n");
					return;
				}
				if (model.getSolver().findSolution() != null) {nbSat++;} //Le modèle a-t-il une solution ?
			}
			System.out.println(f + " -> " + nbSat + " réseaux satisfaits soit "+Taux(f,nbRes).getFirst()+"%.");
		}
		
		System.out.println();
		System.out.println("<-------------------TEST DE CSP--------------------------->");
		/*try (Scanner entree = new Scanner(System.in)) {
	
			System.out.println("Veuillez entrer le nombre de tuple maximal jusqu'à laquelle csvGenerateur va chercher.");
			System.out.println("val possibles : 10,20,30,40,50,60,70,80,90,100,110.");
			int nbTupleCSV = entree.nextInt();
			
			System.out.println("Veuillez entrer le nombre de réseaux souhaité par CSP");
			System.out.println("val possibles : 3,10,20 et 50");
			int nbResCSV = entree.nextInt();
			
			csvGenerateur(nbTupleCSV,nbResCSV);
			System.out.println("Fichier CSV généré avec succès.");
		}*/
		
		int nbTuplesCSV = 250;
		
		//Valeurs de tabRes possibles :
		int [] tabRes = {15};
		
		//int [] tabContraintes = {50,100,150};
		int [] tabContraintes = {50,100,150};
		
		genToutCSV(tabContraintes,nbTuplesCSV,tabRes);
	}
	
	
	
	
}