
package Dico;

/**La classe FastDictionary sp�cialise la classe AbstractDictionary<BR>
 *Cette classe est une sp�cialisation du framework d�fini pas l'interface Dictionary et la classe AbstractDictionary<BR>
 *Dans ce type de dictionnaire, les couples cl�-valeur sont rang�s et retrouv�s � l'aide d'une technique de hachage*/

public class FastDictionary extends AbstractDictionary {
    
    /**Ce constructeur d�finit un objet FastDictionary avec une taille de dictionnaire de 10 par d�faut*/
    public FastDictionary() {
	tailleTab = 10;	
	tabCle = new Object[tailleTab];
	tabValeur = new Object[tailleTab];
    }

    
    /**Ce constructeur d�finit un objet FastDictionary avec une taille de dictionaire pass�e en param�tre
     *@param t taille du dictionnaire*/
    public FastDictionary(int t) {
	tailleTab  = t;
	tabCle = new Object[tailleTab];
	tabValeur = new Object[tailleTab];
    }

    
    /**Cette m�thode renvoie le nombre de couples cl�-valeur contenus dans le dictionnaire<BR>
     *Cette m�thode est une red�finition de la m�thode size() de la classe AbstractDictionary qui supposait les couples tass�s � gauche dans le dictionnaire. Un dictionnaire de type FastDictionary doit �tre parcouru en entier car il contient des trous
     *@return Nombre d'�l�ments contenus dans le dictionnaire*/
    protected int size() {	
	int cpt = 0;
	for (int i = 0 ; i < tailleTab ; i++) {
	    if (tabCle[i] != null)
		cpt++;	
	}			
	return cpt;
    }


    /**Cette m�thode renvoie l'index auquel est rang�e la cl� pass�e en param�tre dans le receveur<BR>
     *Cette m�thode est une red�finition de la m�thode indexOf de la classe AbstractDictionary qui effectuait une recherche s�quentielle. Pour un dictionnaire de type FastDictionary, l'index correspondant � la cl� pass�e en param�tre est retrouv� � l'aide de la m�thode de hachage
     *@param key cl� recherch�e dans le dictionnaire
     *@return index de la cl� si elle a �t� trouv�e, sinon -1*/
    protected int indexOf(Object key) {
	if (tailleTab == 0)
	    return -1;
	int index = key.hashCode() % tailleTab;
	if (index < 0)
	    index = (-1) * index;
	int i = index;
	if (tabCle[index] != key) {
	    do {
		i++; 
		if (i == tailleTab)
		    i =  0;
	    } while( (i != index) && (tabCle[i] != key));
	  
	    if (i == index)
		return -1;
	    else
		return i;
	}
	else
	    return index;
    } 


    /**Cette m�thode renvoie l'index auquel sera rang� un nouveau couple cl�-valeur dans le dictionnaire<BR>
     *Cet index est calcul� � l'aide d'une technique de hachage : la m�thode hashCode() est appliqu�e � la cl� modulo la taille du dictionnaire<BR>
     *Si un couple cl�-valeur est d�j� rang� dans le dictionnaire � cette position, le dictionnaire est parcouru de mani�re s�quentielle jusqu'� obtenir l'index d'une case vide<BR>  
     *Cette m�thode ne doit �tre appel�e que dans le cas ou le dictionnaire ne contient pas d�j� cette cl�<BR>
     *Si le dictionnaire est aux 3/4 plein, la taille du dictionnaire est augment�e de 25% par rapport � sa taille initiale, de mani�re � �viter au maximum les conflits de hachage
     *@param key cl� de l'�l�ment � ins�rer
     *@return index du nouvel �l�ment dans le receveur*/
    protected int newIndexOf(Object key) {
	if (mustGrow())
	    grow();

	int index = key.hashCode() % tailleTab;
	if (index < 0)
	    index = (-1) * index;
	
	while (tabCle[index] != null) {
	    index++;
	    if (index == tailleTab)
		index =  0;
	}

	return index;
    }

   
    /**Cette m�thode indique si le dictionnaire doit �tre agrandi ou non
     *@return vrai si le receveur est aux 3/4 plein, faux sinon*/ 
    protected boolean mustGrow() {
	if (size() + 1 > (tailleTab * 3) / 4) 
	    return true;       
	else
	    return false;
    }

    
    /**Cette m�thode permet d'augmenter la taille du ditionnaire de 25%<BR>
     *Les cl�s et les valeurs sont r�ins�r�es dans les nouveaux tableaux en utilisant une technique de hachage<BR>
     *L'index d'un couple est calcul� en appliquant la m�thode hashCode() � la cl� modulo la taille du dictionnaire<BR>
     *Si un couple cl�-valeur est d�j� rang� dans le dictionnaire � cette position, le dictionnaire est parcouru de mani�re s�quentielle jusqu'� obtenir l'index d'une case vide*/
    protected void grow() {
	int fin = tailleTab;
	if (tailleTab % 4 == 0)
	    tailleTab = tailleTab + tailleTab / 4;
	else
	    tailleTab = tailleTab + tailleTab / 4 + 1;
	if (tailleTab == 0)
	    tailleTab = 2;
  
	Object[] newTabCle = new Object[tailleTab];
	Object[] newTabValeur = new Object[tailleTab];	    

	for (int i = 0 ; i < fin ; i++) {
	    if (tabCle[i] != null) {
		int index = tabCle[i].hashCode() % tailleTab;
		if (index < 0)
		    index = (-1) * index;

		while (newTabCle[index] != null) {
		    index++;
		    if (index == tailleTab)
			index =  0;
		}

		newTabCle[index] = tabCle[i];
		newTabValeur[index] = tabValeur[i];
	    }
	}
	
	tabCle = newTabCle;
	tabValeur = newTabValeur;	
    }



    /*Test d'insertion d'un �l�ment dans le dictionnaire
      1) CRITERES

      PRECONDITIONS:
      -dictionnaire de taille 0 (T0) / dictionnaire de taille 1 (T1) / dictionnaire de taille quelconque (TN) 
      -0 �l�ment dans le dictionnaire (D0) / 1 �l�ment dans le dictionnaire (D1) / n*3/4 �l�ments dans le dictionnaire (DN3/4)
      -�l�ment dans dictionnaire (ED) / �l�ment non dans dictionnaire (END)
      POSTCONDITION:
      -�l�ment ins�r� (EI) / �l�ment non ins�r� (ENI)

      2) CLASSES D'EQUIVALENCE

      POTENTIELLES : (T0 T1 TN) * (D0 D1 DN3/4) * (ED END) * (EI ENI)
      EFFECTIVES : T0*D0*END*EI ; 
                   T1*D0*END*EI ; T1*D1*ED*ENI ; T1*D1*END*EI ;
		   TN*D0*END*EI ; TN*D1*ED*ENI ; TN*D1*END*EI ; TN*DN3/4*ED*ENI ; TN*DN3/4*END*EI 
    */

    /*Test de recherche d'un �l�ment dans le dictionnaire
      1) CRITERES
      
      PRECONDITIONS: 
      -dictionnaire de taille 0 (T0) / dictionnaire de taille 1 (T1) / dictionnaire de taille quelconque (TN) 
      -0 �l�ment dans le dictionnaire (D0) / 1 �l�ment dans le dictionnaire (D1) / n*3/4 �l�ments dans le dictionnaire (DN3/4)
       POSTCONDITION:
      -�l�ment trouv� (ET) / �l�ment non trouv� (ENT)

      2) CLASSES D'EQUIVALENCE

      POTENTIELLES : (T0 T1 TN) * (D0 D1 DN3/4) * (ET ENT)
      EFFECTIVES : T0*D0*ENT ;
                   T1*D0*ENT ; T1*D1*ET ; T1*D1*ENT ;
		   TN*D0*ENT ; TN*D1*ENT ; TN*D1*ET ; TN*DN3/4*ENT ; TN*DN3/4*ET
    */
 
    protected int testBoiteBlanche() {
	int retour = 0;

	//TEST N�1:
	//Insertion : T0*D0*END*EI 
	//Recherche : T0*D0*ENT
	System.out.println("\nTEST N�1:"); 
	System.out.println("Recherche : Essai de recherche d'un �l�ment dans un dictionnaire vide \nde taille 0");
	System.out.println("Insertion : Essai d'insertion d'un �l�ment dans un dictionnaire vide \nde taille 0");
	FastDictionary dico = new FastDictionary(0);
	if (dico.get("Platon") != null) {
	    System.out.println("Erreur : L'�l�ment n'aurait pas du �tre trouv�");
	    System.out.println("TEST 1 Recherche ....................................................[FAILED]"); 
	    retour = -1;	
	}
	else
	    System.out.println("TEST 1 Recherche ....................................................[OK]");    
	dico.put("Platon", "Philosophe grec");
	if (!dico.containsKey("Platon")) {
	    System.out.println("Erreur : L'�l�ment n'a pas �t� ins�r�");
	    System.out.println("TEST 1 Insertion ....................................................[FAILED]");
	    retour = -1;
	}
	else {
	    if (dico.tailleTab != 2) {
		System.out.println("Erreur : La taille du dictionnaire est incorrecte");
		System.out.println("TEST 1 Insertion ....................................................[FAILED]");
		retour = -1;
	    }
	    else
		System.out.println("TEST 1 Insertion ....................................................[OK]");	
	}


	//TEST N�2:
	//Insertion : T1*D0*END*EI  
	//Recherche : T1*D0*ENT
	System.out.println("\nTEST N�2:"); 
	System.out.println("Insertion : Essai d'insertion d'un �l�ment dans un dictionnaire vide \nde taille 1");
	System.out.println("Recherche : Essai de recherche d'un �l�ment dans un dictionnaire vide \nde taille 1");
	dico = new FastDictionary(1);
	if (dico.get("Platon") != null) {
	    System.out.println("Erreur : L'�l�ment n'aurait pas du �tre trouv�");
	    System.out.println("TEST 2 Recherche ....................................................[FAILED]"); 
	    retour = -1;	
	}
	else
	    System.out.println("TEST 2 Recherche ....................................................[OK]");   
	dico.put("Platon", "Philosophe grec");
	if (!dico.containsKey("Platon")) {
	    System.out.println("Erreur : L'�l�ment n'a pas �t� ins�r�");
	    System.out.println("TEST 2 Insertion ....................................................[FAILED]");
	    retour = -1;
	}
	else {
	    boolean drapo = true; 
	    if (dico.tailleTab != 2) {
		System.out.println("Erreur : La taille du dictionnaire est incorrecte");
		System.out.println("TEST 2 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }
	    if (dico.size() != 1) {
		System.out.println("Erreur : Le dictionnaire devrait contenir un seul �l�ment");
		System.out.println("TEST 2 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }
	    if (drapo)
		System.out.println("TEST 2 Insertion ....................................................[OK]");	
	}

		
		
	//TEST N�3:
	//Insertion : T1*D1*ED*ENI
	//Recherche : T1*D1*ET
	System.out.println("\nTEST N�3:"); 
	System.out.println("Insertion : Essai d'insertion d'un �l�ment dans un dictionnaire \nde taille 1\nLe dictionnaire contient 1 �l�ment, l'�l�ment � ins�rer est connu");
	System.out.println("Recherche : Essai de recherche d'un �l�ment dans un dictionnaire \nde taille 1\nLe dictionnaire contient 1 �l�ment\nL'�l�ment se trouve en position 0");
	if (dico.get("Platon") == null) {
	    System.out.println("Erreur : L'�l�ment aurait du �tre trouv�");
	    System.out.println("TEST 3 Recherche ....................................................[FAILED]"); 
	    retour = -1;	
	}
	else
	    System.out.println("TEST 3 Recherche ....................................................[OK]");   
	if (!dico.containsKey("Platon")) {
	    System.out.println("Erreur : L'�l�ment devrait se trouver dans le ditionnaire");
	    System.out.println("TEST 3 ..............................................................[FAILED]");
	    retour = -1;
	}
	else
	    System.out.println("TEST 3 Insertion ....................................................[OK]");	


	//TEST N�4:
	//Insertion : T1*D1*END*EI
	//Recherche : T1*D1*ENT
	System.out.println("\nTEST N�4:"); 
	System.out.println("Essai d'insertion d'un �l�ment dans un dictionnaire de taille 1\nLe dictionnaire contient 1 �l�ment, l'�l�ment � ins�rer est inconnu");
	System.out.println("Essai de recherche d'un �l�ment dans un dictionnaire de taille 1\nLe dictionnaire contient 1 �l�ment\nL'�l�ment ne se trouve pas dans le dictionnaire");
	if (dico.get("Euclide") != null) {
	    System.out.println("Erreur : L'�l�ment n'aurait pas du �tre trouv�");
	    System.out.println("TEST 4 Recherche ....................................................[FAILED]"); 
	    retour = -1;	
	}
	else
	    System.out.println("TEST 4 Recherche ....................................................[OK]");   
	dico.put("Euclide", "Math�maticien grec");
	if (!dico.containsKey("Euclide")) {
	    System.out.println("Erreur : L'�l�ment n'a pas �t� ins�r�");
	    System.out.println("TEST 4 Insertion ....................................................[FAILED]");
	    retour = -1;
	}
	else {
	    boolean drapo = true;
	    if (dico.tailleTab != 3) {
		System.out.println("Erreur : La taille du dictionnaire est incorrecte");
		System.out.println("TEST 4 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }
	    if (dico.size() != 2) {
		System.out.println("Erreur : Le nombre d'�l�ments est incorrect");
		System.out.println("TEST 4 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }
	    if (drapo)
		System.out.println("TEST 4 Insertion ....................................................[OK]");	
	}


	//TEST N�5:
	//Insertion : TN*D0*END*EI
	//Recherche : TN*D0*ENT
	System.out.println("\nTEST N�5:"); 
	System.out.println("Insertion : Essai d'insertion d'un �l�ment dans un dictionnaire vide \nde taille n");
	System.out.println("Recherche : Essai de recherche d'un �l�ment dans un dictionnaire vide \nde taille n");
	dico = new FastDictionary(4);
	if (dico.get("Platon") != null) {
	    System.out.println("Erreur : L'�l�ment n'aurait pas du �tre trouv�");
	    System.out.println("TEST 5 Recherche ....................................................[FAILED]"); 
	    retour = -1;	
	}
	else
	    System.out.println("TEST 5 Recherche ....................................................[OK]");   
	dico.put("Platon", "Philosophe grec");
	if (!dico.containsKey("Platon")) {
	    System.out.println("Erreur : L'�l�ment n'a pas �t� ins�r�");
	    System.out.println("TEST 5 Insertion ....................................................[FAILED]");
	    retour = -1;
	}
	else {
	    boolean drapo = true;
	    if (dico.tailleTab != 4) {
		System.out.println("Erreur : La taille du dictionnaire est incorrecte");
		System.out.println("TEST 5 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }
	    if (dico.size() != 1) {
		System.out.println("Erreur : Le nombre d'�l�ments est incorrect");
		System.out.println("TEST 5 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }
	    if (drapo)
		System.out.println("TEST 5 Insertion ....................................................[OK]");	
	}


	//TEST N�6:
	//Insertion : TN*D1*ED*ENI
	//Recherche : TN*D1*ET 
	System.out.println("\nTEST N�6:"); 
	System.out.println("Insertion : Essai d'insertion d'un �l�ment dans un dictionnaire \nde taille n\nLe dictionnaire contient 1 �l�ment, l'�l�ment � ins�rer est connu");
	System.out.println("Recherche : Essai de recherche d'un �l�ment dans un dictionnaire \nde taille n\nLe dictionnaire contient 1 �l�ment\nL'�l�ment recherch� est connu");
	if (dico.get("Platon") == null) {
	    System.out.println("Erreur : L'�l�ment aurait du �tre trouv�");
	    System.out.println("TEST 6 Recherche ....................................................[FAILED]"); 
	    retour = -1;	
	}
	else
	    System.out.println("TEST 6 Recherche ....................................................[OK]");   

	if (!dico.containsKey("Platon")) {
	    System.out.println("Erreur : L'�l�ment devrait se trouver dans le dictionnaire");
	    System.out.println("TEST 6 Insertion ....................................................[FAILED]");
	    retour = -1;
	}
	else
	    System.out.println("TEST 6 Insertion ....................................................[OK]");	
    

	//TEST N�7:
	//Insertion : TN*D1*END*EI
	//Recherche : TN*D1*ENT 
	System.out.println("\nTEST N�7:"); 
	System.out.println("Insertion : Essai d'insertion d'un �l�ment dans un dictionnaire \nde taille n\nLe dictionnaire contient 1 �l�ment, l'�l�ment � ins�rer est inconnu");
	System.out.println("Recherche : Essai de recherche d'un �l�ment dans un dictionnaire \nde taille n\nLe dictionnaire contient 1 �l�ment");
	if (dico.get("Euclide") != null) {
	    System.out.println("Erreur : L'�l�ment n'aurait pas du �tre trouv�");
	    System.out.println("TEST 7 Recherche ....................................................[FAILED]"); 
	    retour = -1;	
	}
	else
	    System.out.println("TEST 7 Recherche ....................................................[OK]"); 
	dico.put("Euclide", "Math�maticien grec");  
	if (!dico.containsKey("Euclide")) {
	    System.out.println("Erreur : L'�l�ment n'a pas �t� ins�r�");
	    System.out.println("TEST 7 Insertion ....................................................[FAILED]");
	    retour = -1;
	}
	else {
	    boolean drapo = true;
	    if (dico.tailleTab != 4) {
		System.out.println("Erreur : La taille du dictionnaire est incorrecte");
		System.out.println("TEST 7 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }
	    if (dico.size() != 2) {
		System.out.println("Erreur : Le nombre d'�l�ments est incorrect");
		System.out.println("TEST 7 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }
	    if (drapo)
		System.out.println("TEST 7 Insertion ....................................................[OK]");	
	}

	
	//TEST N�8:
	//Insertion : TN*DN4/3*ED*ENI 
	//Recherche : TN*DN4/3*ET
	System.out.println("\nTEST N�8:"); 
	System.out.println("Insertion : Essai d'insertion d'un �l�ment dans un dictionnaire \nde taille n\nLe dictionnaire contient n �l�ments, l'�l�ment � ins�rer est connu");
	System.out.println("Recherche : Essai de recherche d'un �l�ment dans un dictionnaire \nde taille n\nLe dictionnaire contient n �l�ments\nL'�l�ment recherch� est connu");	
	dico.put("Aristote", "Philosophe grec");
	dico.put("Archim�de", "Savant grec");
	if (dico.get("Archim�de") == null) {
	    System.out.println("Erreur : L'�l�ment aurait du �tre trouv�");
	    System.out.println("TEST 8 Recherche ....................................................[FAILED]"); 
	    retour = -1;	
	}
	if (!dico.containsKey("Aristote")) {
	    System.out.println("Erreur : L'�l�ment devrait se trouver dans le dictionnaire");
	    System.out.println("TEST 8 Insertion ....................................................[FAILED]");
	    retour = -1;
	}
	else {
	    boolean drapo = true;
	    if (dico.tailleTab != 5) {	
		System.out.println("Erreur : La taille du dictionnaire est incorrecte");
		System.out.println("TEST 8 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }
	    if (dico.size() != 4) {
		System.out.println("Erreur : Le nombre d'�l�ments est incorrect");
		System.out.println("TEST 8 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }
	    if (drapo)
		System.out.println("TEST 8 Insertion ....................................................[OK]");	
	}


	//TEST N�9:
	//Insertion : TN*DN*END*EI
	//Recherche : TN*DN*ENT
	System.out.println("\nTEST N�9:"); 
	System.out.println("Insertion : Essai d'insertion d'un �l�ment dans un dictionnaire \nde taille n\nLe dictionnaire contient n �l�ments, l'�l�ment � ins�rer est inconnu");
	System.out.println("Recherche : Essai de recherche d'un �l�ment dans un dictionnaire \nde taille n\nLe dictionnaire contient n �l�ments");
	if (dico.get("Thal�s") != null) {
	    System.out.println("Erreur : L'�l�ment n'aurait pas du �tre trouv�");
	    System.out.println("TEST 9 Recherche ....................................................[FAILED]"); 
	    retour = -1;	
	}
	else
	    System.out.println("TEST 9 Recherche ....................................................[OK]"); 
	dico.put("Thal�s", "Math�maticien grec");
	if (!dico.containsKey("Thal�s")) {
	    System.out.println("Erreur : L'�l�ment n'a pas �t� ins�r�");
	    System.out.println("TEST 9 Insertion ....................................................[FAILED]");
	    retour = -1;
	}
	else {
	    boolean drapo = true;
	    if (dico.tailleTab != 7) {
		System.out.println("Erreur : La taille du dictionnaire est incorrecte");
		System.out.println("TEST 9 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }
	    if (dico.size() != 5) {
		System.out.println("Erreur : Le nombre d'�l�ments est incorrect");
		System.out.println("TEST 9 Insertion ....................................................[FAILED]");
		retour = -1;
		drapo = false;
	    }	    
	    if (drapo)
		System.out.println("TEST 9 Insertion ....................................................[OK]");	
	}
	
	return retour;    
    }

}


    
