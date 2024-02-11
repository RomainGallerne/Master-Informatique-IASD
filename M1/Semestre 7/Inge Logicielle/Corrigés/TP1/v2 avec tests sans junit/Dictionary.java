
package Dico;

/**L'interface Dictionary d�crit la structure commune � tous les types de dictionnaires.<BR>
 *Ses m�thodes sont les op�rations de base disponibles pour la manipulation d'un dictionnaire.*/

public interface Dictionary {

    /**Cette m�thode recherche un �l�ment du dictionnaire
     *@param key cl� de l'�l�ment � rechercher
     *@return �l�ment ou null si la cl� est inconnue du receveur*/
    public Object get(Object key);

    /**Cette m�thode ins�re un nouveau couple cl�-valeur dans le receveur, si la cl� est inconnue dans le receveur
     *@param key cl� de l'�l�ment � ins�rer
     *@param value valeur de l'�l�ment � ins�rer
     *@return receveur de la m�thode*/
    public Object put(Object key, Object value);

    /**Cette m�thode indique si le receveur est vide
     *@return vrai si le receveur est vide, faux sinon*/
    public boolean isEmpty();

    /**Cette m�thode indique si une cl� est connue dans le receveur
     *@param key cl� � recherche
     *@return vrai si la cl� a �t� trouv�e, faux sinon*/
    public boolean containsKey(Object key);
}

