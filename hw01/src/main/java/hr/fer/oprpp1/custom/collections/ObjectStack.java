package hr.fer.oprpp1.custom.collections;


public class ObjectStack {

    /**
     * Underlying collection structure of the stack
     */
    private ArrayIndexedCollection collection;

    /**
     * Default constructor
     */
    public ObjectStack() {
        collection = new ArrayIndexedCollection();
    }

    /**
     * @return True if empty, otherwise false
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * @return Size of stack
     */
    public int size() {
        return collection.size();
    }

    /**
     * Pushes the specified value on top of the stack
     * @param value Value to be added on top of stack
     */
    public void push(Object value) {
        collection.add(value);
    }

    /**
     * Returns the object that is on top of the stack and removes it from the stack
     * @return Object at top of stack
     */
    public Object pop() {
        if(this.isEmpty()) throw new EmptyStackException();

        Object object = collection.get(size() - 1);
        collection.remove(size() - 1);
        return object;
    }

    /**
     * Returns the object that is on top of the stack
     * @return Object at top of stack
     */
    public Object peek() {
        if(this.isEmpty()) throw new EmptyStackException();

        return collection.get(size() - 1);
    }

    /**
     * Clears entire stack
     */
    public void clear() {
        collection.clear();
    }

}
