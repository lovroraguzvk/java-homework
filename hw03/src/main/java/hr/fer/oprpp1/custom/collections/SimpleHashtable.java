package hr.fer.oprpp1.custom.collections;

import java.util.*;

/**
 * A table with entries that have a key-value pair
 *
 * Handles overflow with hashing the key part of an entry
 * and linking them together in lists where the entries in the
 * table act as root entries of the list
 *
 * @param <K> Parametrized value of the key part of an entry
 * @param <V> Parametrized value of the value part of an entry
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

    /**
     * Internal structure that the table uses for storage of root entries
     */
    private TableEntry<K,V>[] table;
    /**
     * Number of entries in the table
     */
    private int size;
    private long modificationCount = 0;


    /**
     * An entry with a key-value pair and a
     * pointer to the next entry in a list
     *
     * The key value can't be null
     *
     * @param <K> Parametrized value of the key part of an entry
     * @param <V>Parametrized value of the value part of an entry
     */
    public static class TableEntry<K, V> {

        private final K key;
        private V value;
        private TableEntry<K, V> next;

        /**
         * @param key the key of the entry
         * @param value the value of the entry
         * @param next pointer to the next entry in the list
         * @throws NullPointerException if key is null
         */
        public TableEntry(K key, V value, TableEntry<K, V> next) {
            if(key == null) throw new NullPointerException();

            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * @param o the TableEntry from which to copy its contents
         */
        public TableEntry(TableEntry<K, V> o) {
            this.key = o.key;
            this.value = o.value;
        }

        /**
         * Getter for the key
         *
         * @return the key of the entry
         */
        public K getKey() {
            return key;
        }

        /**
         * Getter for the value
         *
         * @return the value of the entry
         */
        public V getValue() {


            return value;
        }

        /**
         * Setter for the value
         *
         * @param value the value of the entry
         */
        public void setValue(V value) {
            this.value = value;
        }


    }

    /**
     * Default constructor that sets the initial capacity of the table at 16
     */
    public SimpleHashtable() {
        this(16);
    }

    /**
     * @param capacity the initial capacity of the table
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int capacity) {
        if(capacity < 1) throw new IllegalArgumentException();

        capacity = (int) Math.pow(Math.ceil(Math.log(capacity)/Math.log(2)), 2);
        if(capacity == 1) capacity = 2;

        table = (TableEntry<K,V>[]) new TableEntry[capacity];
        size = 0;
    }

    /**
     * Maps the specified key to the specified
     * value in this hashtable. The key can't be null. <p>
     *
     * @param      key     the hashtable key
     * @param      value   the value
     * @throws     NullPointerException  if the key is null
     */
    public void put(K key, V value) {
        if(key == null) throw new NullPointerException();

        if((double) size / table.length >= 0.75) table = grow();

        for(TableEntry<K,V> element : table) {
            for(TableEntry<K,V> current = element; current != null; current = current.next) {
                if (current.key.equals(key)) {
                    current.setValue(value);
                    return;
                }
            }
        }
        modificationCount++;
        TableEntry<K, V> entry = new TableEntry<>(key, value, null);

        if(table[Math.abs(key.hashCode()) % table.length] != null) {
            TableEntry<K,V> current;
            for(current = table[Math.abs(key.hashCode()) % table.length]; current.next != null; current = current.next);
            current.next = entry;
        }
        else table[Math.abs(key.hashCode()) % table.length] = entry;


        size++;
    }

    /**
     * Retrieves value of an entry with a specified key.
     *
     * @param      key     the hashtable key
     * @return     V       value of the key-value pair or null if not found
     */
    public V get(Object key) {
        for(TableEntry<K,V> element : table) {
            for(TableEntry<K,V> current = element; current != null; current = current.next) {
                if (current.key.equals(key)) {
                    return current.getValue();
                }
            }
        }

        return null;
    }

    /**
     * @return number of entries in hashtable
     */
    public int size() {
        return size;
    }


    private boolean containsKey(Object key) {
        for(TableEntry<K,V> element : table) {
            for(TableEntry<K,V> current = element; current != null; current = current.next) {
                if(element.key.equals(key)) return true;
            }
        }

        return false;
    }

    private boolean containsValue(Object value) {
        for(TableEntry<K,V> element : table) {
            for(TableEntry<K,V> current = element; current != null; current = current.next) {
                if(current.value.equals(value)) return true;
            }
        }

        return false;
    }

    /**
     * Removes the key (and its corresponding value) from this
     * hashtable. This method does nothing if the key is not in the hashtable.
     *
     * @param   key   the key that needs to be removed
     * @return  the value to which the key had been mapped in this hashtable,
     *          or null if the key did not have a mapping
     * @throws  NullPointerException  if the key is null
     */
    public V remove(Object key) {
        if(key == null) throw new NullPointerException();

        int index = 0;
        V returnValue = null;

        for(TableEntry<K,V> element : table) {
            TableEntry<K,V> prev = null;
            for(TableEntry<K,V> current = element; current != null; current = current.next) {
                if(current.key.equals(key)) {
                    if(prev == null) {
                        table[index] = null;
                    } else {
                        prev.next = current.next;
                    }
                    returnValue = current.value;
                    modificationCount++;
                    size--;
                }
                prev = current;
            }
            index++;
        }

        return returnValue;
    }


    /**
     * @return true if empty, false if not empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns formatted string in the format of "[key1=value1, key2=value2]"
     *
     * @return formatted string of the table with its contents
     */
    @Override
    public String toString() {
        if(isEmpty()) return "[]";

        StringBuilder returnVal = new StringBuilder("[");


        for(TableEntry<K,V> element : table) {
            for(TableEntry<K,V> current = element; current != null; current = current.next) {
                if(current == table[0]) returnVal.append(String.format("%s=%s", current.key.toString(), current.value.toString()));
                else returnVal.append(String.format(", %s=%s", current.key, current.value.toString()));
            }
        }

        /*
        Iterator<TableEntry<K,V>> iter = this.iterator();
        TableEntry<K,V> current = iter.next();


        while(iter.hasNext()) {
            current = iter.next();
        }
         */

        return returnVal.append("]").toString();
    }

    private TableEntry<K,V>[] toArray() {
        return table;
    }

    @SuppressWarnings("unchecked")
    private TableEntry<K,V>[] grow() {
        int oldCapacity = table.length;
        int newCapacity = table.length * 2;

        TableEntry<K,V>[] newTable = (TableEntry<K, V>[]) new TableEntry[newCapacity];

        for(TableEntry<K,V> element : table) {
            for (TableEntry<K, V> current = element; current != null; current = current.next) {
                int index = Math.abs(current.key.hashCode()) % newTable.length;

                if(newTable[index] != null) {
                    TableEntry<K,V> current1;
                    for(current1 = newTable[index]; current1.next != null; current1 = current1.next);
                    current1.next = current;
                }
                else newTable[index] = new TableEntry<>(current);
            }
        }

        return newTable;
    }

    @SuppressWarnings("unchecked")
    private <K1,V1> TableEntry<K1,V1>[] toArray(TableEntry<K1,V1>[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (TableEntry<K1,V1>[]) Arrays.copyOf(table, table.length, a.getClass());
        System.arraycopy(table, 0, a, 0, table.length);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    /**
     * Clears the table of its contents
     */
    public void clear() {
        size = 0;
        modificationCount++;
        Arrays.fill(table, null);
    }

    /**
     * @return iterator of the table
     */
    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

        int currentIndex = 0;
        TableEntry<K,V> currentNode = table[0];
        boolean isFirst = true;
        private long savedModificationCount = modificationCount;

        public IteratorImpl() {
            while(currentNode == null) {
                if (currentIndex == table.length) break;

                currentIndex++;
                currentNode = table[currentIndex];
            }
        }

        @Override
        public boolean hasNext() {
            if (savedModificationCount != modificationCount) throw new ConcurrentModificationException();

            if (currentNode == null) return false;

            if (isFirst) {
                return true;
            }

            int tmpIndex = currentIndex;
            TableEntry<K,V> tmpNode = currentNode;

            tmpNode = tmpNode.next;

            // vertical movement on table
            while(tmpNode == null) {
                if(tmpIndex == table.length - 1) {
                    return false;
                }

                tmpIndex++;
                tmpNode = table[currentIndex];
            }

            return true;
        }

        @Override
        public TableEntry<K, V> next() {
            if(!hasNext()) throw new NoSuchElementException();

            if(isFirst) {
                isFirst = false;
                return currentNode;
            }

            // horizontal movement on table
            if(currentNode != null) currentNode = currentNode.next;

            // vertical movement on table
            while(currentNode == null) {
                currentIndex++;
                currentNode = table[currentIndex];
            }

            return currentNode;
        }

        @Override
        public void remove() {
            if(savedModificationCount != modificationCount) throw new ConcurrentModificationException();
            if(!containsKey(currentNode.key)) throw new IllegalStateException();

            SimpleHashtable.this.remove(currentNode.key);
            savedModificationCount++;
        }
    }

}
