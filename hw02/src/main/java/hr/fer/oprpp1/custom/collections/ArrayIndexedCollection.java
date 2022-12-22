package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Collection that uses an array as a storage method
 */
public class ArrayIndexedCollection implements List {

    /**
     * Number of elements in collection
     */
    private int size = 0;
    /**
     * Array containing collection elements
     */
    private Object[] elements;
    private long modificationCount = 0;

    /**
     * Default constructor that sets the initial capacity of the array to 16
     */
    public ArrayIndexedCollection() {
        this.elements = new Object[16];
    }

    /**
     * Constructor that allows for custom initial array size
     * @param initialCapacity Initial capacity of the array
     * @throws IllegalArgumentException if initial Capacity is less then 1
     */
    public ArrayIndexedCollection(int initialCapacity) {
        this(new ArrayIndexedCollection(), initialCapacity);
    }

    /**
     * Constructor that adds all elements of another collection
     * @param collection Source collection
     * @throws NullPointerException if specified collection is null
     */
    public ArrayIndexedCollection(Collection collection) {
        this(collection, collection.size() == 0 ? 16 : collection.size());
    }

    /**
     * Constructor that allows for custom initial array size
     * Also adds all elements of another collection
     * @param collection Source collection
     * @param initialCapacity Initial capacity of the array
     * @throws IllegalArgumentException if initial Capacity is less then 1
     * @throws NullPointerException if specified collection is null
     */
    public ArrayIndexedCollection(Collection collection, int initialCapacity) {
        if (initialCapacity < 1) throw new IllegalArgumentException();
        if (collection == null) throw new NullPointerException();

        this.elements = new Object[initialCapacity];

        this.addAll(collection);
    }

    /**
     * @return size of collection
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Adds an object to the end of the collection
     * @param value Object to be added to collection
     * @throws NullPointerException If value is null
     */
    @Override
    public void add(Object value) {
        modificationCount++;
        if(value == null) throw new NullPointerException();

        this.size++;
        if(size <= elements.length) {
            elements[size - 1] = value;
            return;
        }

        int elementsLength = elements.length;
        Object[] newElements = new Object[elementsLength * 2];
        System.arraycopy(this.elements, 0, newElements, 0, elementsLength);

        this.elements = newElements;
        this.elements[elementsLength] = value;
    }

    /**
     * @param value Object value that is being searched in the collection
     * @return true if the collection contains the value, otherwise false
     */
    @Override
    public boolean contains(Object value) {
        for(Object element : elements) {
            if(element != null && element.equals(value)) return true;
        }
        return false;
    }

    /**
     * Removes a specified value from the collection
     * @param value Object
     * @return true if successfully removed, otherwise false
     */
    @Override
    public boolean remove(Object value) {
        modificationCount++;
        for(int i = 0; i < size; i++) {
            if(elements[i].equals(value)) {
                remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Converts the collection to an array
     * @return array of objects
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        System.arraycopy(elements, 0, array, 0, size);
        return array;
    }

    /**
     * Clears the entire collection
     */
    @Override
    public void clear() {
        modificationCount++;
        for(int i = 0; i < size; i++) {
            this.elements[i] = null;
        }

        this.size = 0;
    }

    /**
     * Returns an element at the specified index
     * @param index Index of the element to return
     * @return Element at the specified index
     * @throws IndexOutOfBoundsException If index is out of bounds of the collection
     */
    public Object get(int index) {
        if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException();

        return this.elements[index];
    }

    /**
     * Inserts the value at the specified position
     * @param value Value to be inserted
     * @param position Position at which the value is inserted
     * @throws IndexOutOfBoundsException If the specified position is out of bounds of the collection
     * @throws NullPointerException If value is null
     */
    public void insert(Object value, int position) {
        if(size != elements.length) modificationCount++;
        if(position < 0 || position > this.size) throw new IndexOutOfBoundsException();
        if(value == null) throw new NullPointerException();

        int origSize = size;
        if(size == elements.length) {
            add(elements[elements.length - 1]);
        }

        System.arraycopy(elements, position, elements, position + 1, size - 1 - position);

        elements[position] = value;
        if(origSize == size) size++;
    }

    /**
     * Returns the first index at which the value is stored in the collection
     * @param value Target value
     * @return Index of the first target value, if no target value in collection returns -1
     */
    public int indexOf(Object value) {
        for(int i = 0; i < size; i++) {
            if(elements[i].equals(value)) return i;
        }

        return -1;
    }

    /**
     * Removes an element from the target index making the one before it and after it 'neighbours'
     * @param index target index
     * @throws IndexOutOfBoundsException if index out of bounds of the collection
     */
    public void remove(int index) {
        if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException();

        elements[index] = null;

        System.arraycopy(elements, index + 1, elements, index, size - 1 - index);

        size--;
    }

    @Override
    public ElementsGetter createElementsGetter() {
        return new ArrayElementsGetter(this);
    }

    private static class ArrayElementsGetter implements ElementsGetter {

        private int currentIndex;
        private final ArrayIndexedCollection collection;
        private final long savedModificationCount;

        public ArrayElementsGetter(ArrayIndexedCollection collection) {
            this.collection = collection;
            this.currentIndex = 0;
            this.savedModificationCount = collection.modificationCount;
        }

        @Override
        public boolean hasNextElement() {
            if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException();
            return currentIndex != collection.size;
        }

        @Override
        public Object getNextElement() {
            if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException();
            if(!hasNextElement()) throw new NoSuchElementException();
            return collection.elements[currentIndex++];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayIndexedCollection that = (ArrayIndexedCollection) o;
        return Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }
}
