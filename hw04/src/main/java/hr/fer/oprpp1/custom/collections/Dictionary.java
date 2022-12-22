package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

public class Dictionary<K, V> {

    /**
     * Internal storage for the dictionary
     */
    private final ArrayIndexedCollection<Couple<K, V>> collection;

    public static class Couple<K1, V1> {

        private final K1 key;
        private V1 value;

        public Couple(K1 key, V1 value) {
            if(key == null) throw new NullPointerException();

            this.key = key;
            this.value = value;
        }

        public K1 getKey() {
            return key;
        }

        public V1 getValue() {
            return value;
        }

        public void setValue(V1 value) {
            this.value = value;
        }
    }

    /**
     * Default constructor
     */
    public Dictionary() {
        this.collection = new ArrayIndexedCollection<>();
    }

    /**
     * @return true if empty, false if not empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @return number of entries in dictionary
     */
    public int size() {
        return collection.size();
    }

    /**
     * Clears the dictionary of all its entries
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Maps the specified key to the specified
     * value in this dictionary. The key can't be null.
     *
     * If this dictionary already contains an entry for the specified
     * key, the value already in this dictionary for that
     * key is returned, after modifying the entry to contain the
     * new element. If this dictionary does not already have an entry
     * for the specified key, an entry is created for the
     * specified key and value, and null is returned.
     *
     * @param      key     the hashtable key.
     * @param      value   the value.
     * @return     the previous value to which the key was mapped
     *             in this dictionary, or null if the key did not
     *             have a previous mapping.
     * @throws     NullPointerException  if the key is null
     */
    public V put(K key, V value) {
        if(key == null) throw new NullPointerException();

        Couple<K,V> couple;

        if((couple = this.containsKey(key)) != null) {
            V oldValue = couple.getValue();
            couple.setValue(value);
            return oldValue;
        }

        collection.add(new Couple<>(key, value));

        return null;
    }

    /**
     * Returns the value to which the key is mapped in this dictionary.
     *
     * If this dictionary contains an entry for the specified key,
     * the associated value is returned; otherwise, null is returned.
     *
     * @return  the value to which the key is mapped in this dictionary.
     * @param   key   a key in this dictionary.
     *          null if the key is not mapped to any value in
     *          this dictionary.
     * @throws    NullPointerException if the key is null.
     */
    public V get(Object key) {
        if(key == null) throw new NullPointerException();

        Couple<K,V> couple;

        if((couple = this.containsKey(key)) != null) {
            return couple.getValue();
        }

        return null;
    }

    /**
     * Removes the key (and its corresponding
     * value) from this dictionary. This method does nothing
     * if the key is not in this dictionary.
     *
     * @param   key   the key that needs to be removed.
     * @return  the value to which the key had been mapped in this
     *          dictionary, or null if the key did not have a
     *          mapping.
     * @throws    NullPointerException if key is null.
     */
    public V remove(K key) {
        Couple<K,V> couple;

        if((couple = this.containsKey(key)) != null) {
            collection.remove(couple);
            return couple.getValue();
        }

        return null;
    }

    private Couple<K, V> containsKey(Object key) {
        ElementsGetter<Couple<K, V>> getter = collection.createElementsGetter();
        Couple<K, V> currentElement;

        while(getter.hasNextElement()) {
            if((currentElement = getter.getNextElement()).getKey() == key) {
                return currentElement;
            }
        }

        return null;
    }

}
