package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Collection that uses double linked nodes as a way to keep track of elements
 */
public class LinkedListIndexedCollection implements List {

    private static class ListNode {
        ListNode prev;
        ListNode next;
        Object value;

        ListNode(Object value) {
            this.value = value;
        }
    }

    /**
     * Number of elements in collection
     */
    private int size = 0;
    /**
     * First in the series of linked nodes
     */
    private ListNode first = null;
    /**
     * Last in the series of linked nodes
     */
    private ListNode last = null;
    private long modificationCount = 0;

    /**
     * Default constructor that initializes the class fields
     */
    public LinkedListIndexedCollection() {

    }

    /**
     * Constructor that initializes and adds all elements of another collection
     */
    public LinkedListIndexedCollection(Collection collection) {
        if(collection == null) throw new NullPointerException();

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
        size++;

        ListNode newNode = new ListNode(value);

        if(first == null) {
            first = newNode;
            last = newNode;
            return;
        }

        if(first == last) {
            last = newNode;
            last.prev = first;
            first.next = last;
            return;
        }

        newNode.prev = last;
        last.next = newNode;
        last = newNode;
    }

    /**
     * @param value Object value that is being searched in the collection
     * @return true if the collection contains the value, otherwise false
     */
    @Override
    public boolean contains(Object value) {
        for(ListNode current = first; current != null; current = current.next) {
            if(current.value == value) return true;
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
        if(value == null) throw new NullPointerException();

        size--;

        for(ListNode current = first; current != null; current = current.next) {
            if(current.value == value) {
                if(current == first) {
                    first = first.next;
                    first.prev = null;
                    return true;
                }

                if(current == last) {
                    last = last.prev;
                    last.next = null;
                    return true;
                }

                current.next.prev = current.prev;
                current.prev.next = current.next;
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
        int i = 0;

        for(ListNode current = first; current != null; current = current.next) {
            array[i] = current.value;
            i++;
        }

        return array;
    }

    /**
     * Clears the entire collection
     */
    @Override
    public void clear() {
        modificationCount++;
        first = null;
        last = null;

        size = 0;
    }

    /**
     * Returns an element at the specified index
     * @param index Index of the element to return
     * @return Element at the specified index
     * @throws IndexOutOfBoundsException If index is out of bounds of the collection
     */
    public Object get(int index) {
        if(index < 0 || index >= size) throw new IndexOutOfBoundsException();

        ListNode current;
        if(index <= size / 2) {
            current = first;
            for(int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        else {
            current = last;
            for(int i = 0; i < size - 1 - index; i++) {
                current = current.prev;
            }
        }

        return current.value;
    }

    /**
     * Inserts the value at the specified position
     * @param value Value to be inserted
     * @param position Position at which the value is inserted
     * @throws IndexOutOfBoundsException If the specified position is out of bounds of the collection
     * @throws NullPointerException If value is null
     */
    public void insert(Object value, int position) {
        if(position < 0 || position > size) throw new IndexOutOfBoundsException();
        if(value == null) throw new NullPointerException();

        if(position == size) {
            add(value);
            return;
        }

        size++;

        ListNode newNode = new ListNode(value);
        ListNode current;

        if(position <= size / 2) {
            current = first;
            for(int i = 0; i < position; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for(int i = 0; i < size - 1 - position; i++) {
                current = current.prev;
            }
        }

        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
    }

    /**
     * Returns the first index at which the value is stored in the collection
     * @param value Target value
     * @return Index of the first target value, if no target value in collection returns -1
     */
    public int indexOf(Object value) {
        if(value == null) return -1;

        int i = 0;
        for(ListNode current = first; current != null; current = current.next) {
            if(current.value.equals(value)) return i;
            i++;
        }

        return -1;
    }

    /**
     * Removes an element from the target index making the one before it and after it 'neighbours'
     * @param index target index
     * @throws IndexOutOfBoundsException if index out of bounds of the collection
     */
    public void remove(int index) {
        if(index < 0 || index >= size) throw new IndexOutOfBoundsException();

        size--;

        int i = 0;
        for(ListNode current = first; current != null; current = current.next) {
            if(i == index) {
                if(current == first) {
                    first = first.next;
                    first.prev = null;
                    break;
                }

                if(current == last) {
                    last = last.prev;
                    last.next = null;
                    break;
                }

                current.next.prev = current.prev;
                current.prev.next = current.next;
                break;
            }
            i++;
        }
    }

    @Override
    public ElementsGetter createElementsGetter() {
        return new ListElementsGetter(this);
    }

    private static class ListElementsGetter implements ElementsGetter {

        private ListNode currentNode;
        LinkedListIndexedCollection collection;
        private final long savedModificationCount;

        public ListElementsGetter(LinkedListIndexedCollection collection) {
            this.collection = collection;
            currentNode = collection.first;
            this.savedModificationCount = collection.modificationCount;
        }

        @Override
        public boolean hasNextElement() {
            if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException();
            return currentNode != null;
        }

        @Override
        public Object getNextElement() {
            if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException();
            if(!hasNextElement()) throw new NoSuchElementException();
            ListNode tmpNode = currentNode;
            currentNode = currentNode.next;
            return tmpNode.value;
        }
    }

}
