package hr.fer.oprpp1.custom.collections;

/**
 * A collection of objects
 */
public interface Collection {

    /**
     * Method for determining if the collection is empty
     * @return True if empty, false if not empty
     */
    default boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * @return size of collection
     */
    int size();

    /**
     * Adds an object to the collection
     * @param value object to be added to collection
     */
    void add(Object value);

    /**
     * Examines if the collection contains the specified value
     * @param value Object value that is being searched in the collection
     * @return true if the collection contains the value, otherwise false
     */
    boolean contains(Object value);

    /**
     * Removes a specified value from the collection
     * @param value Object
     * @return true if successfully removed, otherwise false
     */
    boolean remove(Object value);

    /**
     * Converts the collection to an array
     * @return array of objects
     */
    Object[] toArray();

    /**
     * Iterates over the whole collection and processes the values using the processor object
     * @param processor Processor
     */
    default void forEach(Processor processor) {
        ElementsGetter getter = this.createElementsGetter();

        while(getter.hasNextElement()) {
            processor.process(getter.getNextElement());
        }
    }

    /**
     * Adds all values of the source collection to this collection
     * @param other Source collection
     */
    default void addAll(Collection other) {
        class LocalProcessor implements Processor {

            public void process(Object value) {
                add(value);
            }
        }

        other.forEach(new LocalProcessor());
    }

    ElementsGetter createElementsGetter();

    default void addAllSatisfying(Collection col, Tester tester) {
        ElementsGetter otherGetter = col.createElementsGetter();

        while(otherGetter.hasNextElement()) {
            Object obj = otherGetter.getNextElement();
            if(tester.test(obj)) {
                this.add(obj);
            }
        }
    }

    /**
     * Clears the entire collection
     */
    void clear();
}
