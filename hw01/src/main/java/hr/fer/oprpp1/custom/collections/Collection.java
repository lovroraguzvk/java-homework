package hr.fer.oprpp1.custom.collections;

/**
 * A collection of objects
 */
public class Collection {

    /**
     * Constructs an empty collection of objects
     */
    protected Collection() {

    }

    /**
     * Method for determining if the collection is empty
     * @return True if empty, false if not empty
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * @return size of collection
     */
    public int size() {
        return 0;
    }

    /**
     * Adds an object to the collection
     * @param value object to be added to collection
     */
    public void add(Object value) {

    }

    /**
     * Examines if the collection contains the specified value
     * @param value Object value that is being searched in the collection
     * @return true if the collection contains the value, otherwise false
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * Removes a specified value from the collection
     * @param value Object
     * @return true if successfully removed, otherwise false
     */
    public boolean remove(Object value) {
        return false;
    }

    /**
     * Converts the collection to an array
     * @return array of objects
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Iterates over the whole collection and processes the values using the processor object
     * @param processor Processor
     */
    public void forEach(Processor processor) {

    }

    /**
     * Adds all values of the source collection to this collection
     * @param other Source collection
     */
    public void addAll(Collection other) {
        class LocalProcessor extends Processor {

            public void process(Object value) {
                add(value);
            }
        }

        other.forEach(new LocalProcessor());
    }

    /**
     * Clears the entire collection
     */
    public void clear() {

    }
}
