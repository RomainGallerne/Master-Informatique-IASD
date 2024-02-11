import java.io.*;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Expe {
	
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
	
	    private static double[] tauxSol(String f,int nbRes) throws Exception{
		double nbSat = 0.0;
		double timeOut = 0.0;
		String temps = "10s";
		
		BufferedReader readFile = new BufferedReader(new FileReader(f));
		for(int nb=1 ; nb<=nbRes; nb++) {
			//Lecture du réseau nb
			Model model=lireReseau(readFile);
			if(model==null) {
				System.out.println("Problème de lecture de fichier !\n");
				return null;
			}
			//Recherche des solutions du modèles nb
			model.getSolver().limitTime(temps);
			if (model.getSolver().solve()){nbSat+=1.0;} 
			else if (model.getSolver().isStopCriterionMet()){
				timeOut += 1.0;
				System.out.println("Le solveur n'a pu trouver aucune solution en moins de "+temps+".");
			} else {
				//System.out.println("Le solveur a tout exploré et n'a trouvé aucune solution.");
			}
		}
		double[] resultats = {(nbSat/(double)nbRes)*100.0,timeOut};
		return resultats;
	}
	
	private static void csvGenerateur(int nbContraintes,int nbTuples,int nbRes) throws Exception {
		ThreadMXBean thread = ManagementFactory.getThreadMXBean();
		
		//Initialisation du fichier CSV
	    File fCSV = new File("benchmark/densite-"+Math.round((double)nbContraintes*100.0/782.0)+"/CSV"+nbTuples+"tuples"+nbRes+"res.csv");
	    BufferedWriter bwCSV = new BufferedWriter(new FileWriter(fCSV));
	    bwCSV.write("NbTuples,TauxResSatis,TpsExecMoyen(ms),TimeOuts");
	    bwCSV.newLine();
	    
	    //Remplissage du fichier CSV
	    int t=100;
	    String f;
	    long tauxSolMoyen,nbTimeOutTotal;
	    double[] retourTauxSol;
	    double[] Tps = new double[5];
	    long debutCPU,debutUser;
	    double maxTps,minTps,moyenTps,sum;
	    while(t<=nbTuples) {
	    	tauxSolMoyen=0;
	    	nbTimeOutTotal=0;
	    	f = "CSP/nbContraintes-"+nbContraintes+"/csp-40-25-"+nbContraintes+"-"+t+"-"+nbRes+".txt";
	    	for(int i=0;i<5;i++) {
	    		debutCPU=thread.getCurrentThreadCpuTime();
	    		debutUser= thread.getCurrentThreadUserTime();
	    		
	    		retourTauxSol = tauxSol(f,nbRes);
	    		
	    		Tps[i] = (thread.getCurrentThreadCpuTime()-debutCPU)-(thread.getCurrentThreadUserTime()-debutUser);
	    		tauxSolMoyen += retourTauxSol[0];
	    		nbTimeOutTotal += retourTauxSol[1];
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
			System.out.println("-> Génération des CSV de densité "+Math.round((double)nbContr*100.0/782.0));
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
			System.out.println(f + " -> " + nbSat + " réseaux satisfaits soit "+tauxSol(f,nbRes)[0]+"%.");
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
		
		int nbTuplesCSV = 400;
		
		//Valeurs de tabRes possibles :
		int [] tabRes = {15};
		
		//Valeurs de densité : 13%, 26%, 38%
		int [] tabContraintes = {100,200,300};
		
		genToutCSV(tabContraintes,nbTuplesCSV,tabRes);
	}
	
	
	
	
}