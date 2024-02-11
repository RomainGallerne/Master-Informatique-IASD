
package Dico;

/**La classe AbstractDictionary impl�mente l'interface Dictionary.<BR>
 *Elle impl�mente les m�thodes de l'interface Dictionary et introduit trois autres m�thodes.<BR>
 *La m�thode newIndexOf est abstraite car elle est sp�cifique aux �ventuelles sous-classes de AbstractDictionary.<BR>
 *Les m�thodes indexOf et size sont impl�ment�es par d�faut et pourront �tre red�finies au besoin dans les �ventuelles sous-classes de AbstractDictionary.*/

public abstract class AbstractDictionary implements Dictionary {

    /**Tableau contenant les cl�s du dictionnaire*/    
    protected Object[] tabCle;
    /**Tableau contenant les valeurs correpondant aux cl�s du dictionnaire*/
    protected Object[] tabValeur;
    /**Taille des tableaux des cl�s et des valeurs*/
    protected int tailleTab;


    /**Ce constructeur vide d�finit un objet AbstractDictionary.*/
    public AbstractDictionary() {}
    
    
    /**Cette m�thode renvoie le nombre de couples cl�-valeur contenus dans le dictionnaire<BR>
     *Elle parcours le tableau des cl�s jusqu'� trouver une case � NULL
     *@return Nombre d'�l�ments contenus dans le dictionnaire*/
    protected int size() {
	int cpt = 0;
	int i = 0;
	while ( (i < tailleTab) && (tabCle[i] != null) ) {
	    cpt++;	
	    i++;
	}			
	return cpt;
    }

   
    /**Cette m�thode renvoie l'index auquel est rang�e la cl� pass�e en param�tre dans le receveur<BR>
     *Elle effectue une recherche s�quentielle<BR>
     *@param key cl� recherch�e dans le dictionnaire
     *@return index de la cl� si elle a �t� trouv�e, sinon -1*/
    protected int indexOf(Object key) {
	int ind = 0;
	while ( (ind < tailleTab) && (tabCle[ind] != key) ) 
	    ind++;	
	if (ind == tailleTab)
	    return -1;
	else
	    return ind;
    }


    /**Cette m�thode renvoie l'index auquel sera rang� un nouveau couple cl�-valeur dans le dictionnaire<BR>
     *Cet index est calcul� en fonction de la cl� pass�e en param�tre<BR>
     *Cette m�thode ne doit �tre appel�e que dans le cas ou le dictionnaire ne contient pas d�j� cette cl�<BR>
     *S'il n'y a pas assez de place dans le dictionnaire pour l'insertion d'un nouvel �l�ment, newIndexOf remplace les tableaux des cl�s et des valeurs pas des tableaux plus grands
     *@param key cl� de l'�l�ment � ins�rer
     *@return index du nouvel �l�ment dans le receveur*/
    abstract protected int newIndexOf(Object key);	

   
    /**Cette m�thode recherche un �l�ment du dictionnaire
     *@param key cl� de l'�l�ment � rechercher
     *@return �l�ment ou null si la cl� est inconnue du receveur*/
    public Object get(Object key) {		
	int ind = indexOf(key);
	if (ind == -1)
	    return null;
	else
	    return tabValeur[ind]; 
    }


    /**Cette m�thode ins�re un nouveau couple cl�-valeur dans le receveur, si la cl� est inconnue dans le receveur
     *@param key cl� de l'�l�ment � ins�rer
     *@param value valeur de l'�l�ment � ins�rer
     *@return receveur de la m�thode*/
    public Object put(Object key, Object value) {	
	if (!containsKey(key)) {
	    int i = newIndexOf(key);
	    tabCle[i] = key;
	    tabValeur[i] = value;
	}				
	return this;
    }

    
    /**Cette m�thode indique si le receveur est vide
     *@return vrai si le receveur est vide, faux sinon*/
    public boolean isEmpty() {
	if (size() == 0)
	    return true;
	else 
	    return false;
    }

  
    /**Cette m�thode indique si une cl� est connue dans le receveur
     *@param key cl� � recherche
     *@return vrai si la cl� a �t� trouv�e, faux sinon*/
    public boolean containsKey(Object key) {
	for (int i = 0 ; i < tailleTab ; i++)
	    if (tabCle[i] == key)
		return true;
	return false;
    }	



    public void affiche() { 
	System.out.println("\n*************************************************************************");
	System.out.println("Nombre de d�finitions : " + size());
	System.out.println("Taille du dictionnaire : " + tailleTab);
	for (int i = 0 ; i < tailleTab ; i++)
	    System.out.println("Index " + i + " : (" + tabCle[i] + " ; " + tabValeur[i] + ")");	
	System.out.println("*************************************************************************");
    }



    /*Test d'insertion d'un �l�ment dans le dictionnaire
      1) CRITERES

      PRECONDITIONS:
      -dictionnaire vide (DV) / dictionnaire non vide (DNV) 
      -�l�ment dans dictionnaire (ED) / �l�ment non dans dictionnaire (END)
      POSTCONDITION:
      -�l�ment ins�r� (EI) / �l�ment non ins�r� (ENI)

      2) CLASSES D'EQUIVALENCE

      POTENTIELLES : (DV DNV) * (ED END) * (EI ENI) 
      EFFECTIVES : DV*END*EI ; DNV*END*EI ; DNV*ED*ENI 
    */

    /*Test de recherche d'un �l�ment dans le dictionnaire
      1) CRITERES
      
      PRECONDITIONS:
      -dictionnaire vide (DV) / dictionnaire plein (DNV)      
      POSTCONDITION:
      -�l�ment trouv� (ET) / �l�ment non trouv� (ENT)

      2) CLASSES D'EQUIVALENCE

      POTENTIELLES : (DV DV) * (ET ENT)  
      EFFECTIVES : DV*ENT ; DNV*ET ; DNV*ENT 
    */

    protected int testBoiteNoire() {	
	int retour = 0;

	//TEST N�1:
	//Recherche : DV * ENT
	//Insertion : DV * END * EI
	System.out.println("\nTEST N�1:"); 
	System.out.println("Recherche : Essai de recherche d'un �l�ment dans un dictionnaire vide");
	System.out.println("Insertion : Essai d'insertion d'un �l�ment dans un dictionnaire vide");	
	if (this.isEmpty()) {
	    if (this.containsKey("Platon")) {
		System.out.println("Erreur : Le dictionnaire est vide et ne peut donc pas contenir l'�l�ment");
		System.out.println("TEST 1 ..............................................................[FAILED]");
		retour =  -1;
	    }
	    else {
		if (this.get("Platon") != null) {
		    System.out.println("Erreur : L'�l�ment n'aurait pas du �tre trouv�");
		    System.out.println("TEST 1 Recherche ....................................................[FAILED]"); 
		    retour = -1;	
		}		
		else
		    System.out.println("TEST 1 Recherche ....................................................[OK]"); 		    
		this.put("Platon", "Philosophe grec");
		if (this.containsKey("Platon")) 
		    System.out.println("TEST 1 Insertion ....................................................[OK]");	
		else {
		    System.out.println("Erreur : L'�l�ment n'a pas �t� ins�r�");
		    System.out.println("TEST 1 Insertion ....................................................[FAILED]"); 
		    retour = -1;	
		}
	    }
	}
	else {
	    System.out.println("Erreur : Le dictionnaire devrait �tre vide");
	    System.out.println("TEST 1 ..............................................................[FAILED]");  
	    retour = -1;	
	}

	//TEST N�2:
	//Recherche : DNV * ENT 
	//Insertion : DNV * END * EI
	System.out.println("\nTEST N�2 :");
	System.out.println("Recherche : Essai de recherche d'un �l�ment dans un dictionnaire non vide");
	System.out.println("Insertion : Essai d'insertion d'un �l�ment dans un dictionnaire non vide\nL'�l�ment est inconnu du dictionnaire");
	if (this.isEmpty()) {
	    System.out.println("Erreur : Le dictionnaire ne devrait pas �tre vide");
	    System.out.println("TEST 2 ..............................................................[FAILED]");  
	    retour = -1;	
	}
	else {
	    if (this.containsKey("Euclide")) {
		System.out.println("Erreur : Le dictionnaire ne devrait pas contenir l'�l�ment");
		System.out.println("TEST 2 ..............................................................[FAILED]");
		retour = -1;
	    }
	    else {
		if (this.get("Euclide") != null) {
		    System.out.println("Erreur : L'�l�ment n'aurait pas du �tre trouv�");
		    System.out.println("TEST 2 Recherche ....................................................[FAILED]"); 
		    retour = -1;	
		}
		else
		    System.out.println("TEST 2 Recherche ....................................................[OK]"); 	
		this.put("Euclide", "Math�maticien grec");
		if (this.containsKey("Euclide")) 
		    System.out.println("TEST 2 Insertion ....................................................[OK]");	
		else {
		    System.out.println("Erreur : L'�l�ment n'a pas �t� ins�r�");
		    System.out.println("TEST 2 Insertion ....................................................[FAILED]");	
		    retour = -1;
		}
	    }
	}

	
	//TEST N�3 : 
	//Recherche : DNV * ET
	//Insertion : DNV * ED * ENI
	System.out.println("\nTEST N�3 :");
	System.out.println("Recherche : Essai de recherche d'un �l�ment dans un dictionnaire non vide");
	System.out.println("Insertion : Essai d'insertion d'un �l�ment dans un dictionnaire non vide\nL'�l�ment est connu du dictionnaire");
	if (this.isEmpty()) {
	    System.out.println("Erreur : Le dictionnaire ne devrait pas �tre vide");
	    System.out.println("TEST 3 ..............................................................[FAILED]");  
	    retour = -1;	
	}
	else {
	    if (this.containsKey("Platon")) {
		if ((String)this.get("Platon") != "Philosophe grec") {
		    System.out.println("Erreur : L'�l�ment aurait du �tre trouv�");
		    System.out.println("TEST 3 Recherche ....................................................[FAILED]"); 
		    retour = -1;	
		}
		else
		    System.out.println("TEST 3 ..............................................................[OK]");	
	    }
	    else {
		System.out.println("Erreur : Le dictionnaire devrait contenir l'�l�ment");
		System.out.println("TEST 3 ..............................................................[FAILED]");	
		retour = -1;
	    }
	}
	    
	return retour;
    }	


    abstract protected int testBoiteBlanche();

    public void test() {
	System.out.println("\n*************************************************************************");
	System.out.println("TESTS BOITE NOIRE...");
	if (testBoiteNoire() == 0)
	    System.out.println("TESTS BOITE NOIRE ...................................................[OK]");
	else
	    System.out.println("TESTS BOITE NOIRE ...................................................[FAILED]");
	System.out.println("*************************************************************************");
		System.out.println("\n*************************************************************************");
	System.out.println("TESTS BOITE BLANCHE ...");
	if (testBoiteBlanche() == 0)
	    System.out.println("TESTS BOITE BLANCHE .................................................[OK]");
	else
	    System.out.println("TESTS BOITE BLANCHE .................................................[FAILED]");
	System.out.println("*************************************************************************");
    }
}
