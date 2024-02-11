import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class AbstractDictionary implements IDictionary {
    protected Object[] cles;
    protected Object[] valeurs;

    protected abstract int indexOf(Object key);
    protected abstract int newIndexOf(Object key);
    protected abstract void grow();

    @Override
    public Object get(Object key) {
        if(containsKey(key)){
            return valeurs[indexOf(key)];
        } else {return -1;}
    }

    @Override
    public IDictionary put(Object key, Object value){
        if(!containsKey(key)){
            int newindex = newIndexOf(key);
            cles[newindex] = key;
            valeurs[newindex] = value;
        } else {
            int index = indexOf(key);
            valeurs[index] = value;
        }
        return this;
    }

    @Override
    public boolean isEmpty() {
        if (cles.length == 0){return true;}
        else {return false;}
    }

    @Override
    public int size() {
        return cles.length;
    }

    @Override
    public boolean containsKey(Object key) {
        for (Object k : cles){
            if (k!=null && k.equals(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AbstractDictionary{" +
                "cles=" + Arrays.toString(cles) +
                ", valeurs=" + Arrays.toString(valeurs) +
                '}';
    }
}
