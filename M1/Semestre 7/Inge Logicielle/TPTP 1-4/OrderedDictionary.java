import java.sql.Array;

public class OrderedDictionary extends AbstractDictionary {
    public OrderedDictionary(){
        this.cles = new Object[0];
        this.valeurs = new Object[0];
    }

    @Override
    protected int indexOf(Object key) {
        if(containsKey(key)) {
            for (int i = 0; i < size(); i++) {
                if (cles[i].equals(key)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    protected int newIndexOf(Object key) {
        grow();
        return size()-1;
    }

    @Override
    protected void grow() {
        Object[] newCles = new Object[size()+1];
        Object[] newValeurs = new Object[size()+1];
        for (int i=0;i<size();i++){
            newCles[i] = cles[i];
            newValeurs[i] = valeurs[i];
        }
        cles = newCles;
        valeurs = newValeurs;
    }
}
