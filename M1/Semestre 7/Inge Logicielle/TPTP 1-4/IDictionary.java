public interface IDictionary {
    public Object get(Object key);
    public IDictionary put(Object key, Object value);
    public boolean isEmpty();
    public int size();
    public boolean containsKey(Object key);
}
