public class SortedDictionary extends OrderedDictionary {

	@Override
	protected int newIndexOf(Object key) {
		//Vérification du type de "key", doit être un comparable sinon -> plante
		Comparable compCles = (Comparable) key;
		int newindex=0;
		Object[] newCles = new Object[size() + 1];
		Object[] newValeurs = new Object[size() + 1];
		//Assignation de toutes les clés/valeurs pré-newIndex
		while (newindex<size() && compCles.compareTo(cles[newindex])>0) {
			newCles[newindex] = cles[newindex];
			newValeurs[newindex] = valeurs[newindex];
			newindex++;
		}
		//Assignation à null pour newIndex
		newCles[newindex] = null; newValeurs[newindex] = null;
		//Assignation de toutes les clés/valueurs post-newIndex
		if (newindex!=size()) {
			for (int i=newindex+1; i<newCles.length; i++) {
				newCles[i] = cles[i-1];newValeurs[i] = valeurs[i-1];
			}
		}
		//Assignation final des clés/valeurs
		cles = newCles;valeurs = newValeurs;
		//Return du nouvelle index
		return newindex;
	}

	@Override
	public Object get(Object key) {
		Comparable compCles = (Comparable) key;
		int index = size()/2;
		int compare;
		while(!((compare=compCles.compareTo((Comparable)cles[index]))!=0) && index!=index/2){
			if(compare>0){
				index += index/2;
			}
			else {
				index -= index/2;
			}
		}
		if(index>=size() || 0>index) {return -1;}
		else {return index;}
	}
}
