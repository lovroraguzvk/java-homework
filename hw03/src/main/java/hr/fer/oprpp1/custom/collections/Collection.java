package hr.fer.oprpp1.custom.collections;

/**
 * A collection of objects
 */
public interface Collection<E> {

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
    void add(E value);

    /**
     * Examines if the collection contains the specified value
     * @param value Object value that is being searched in the collection
     * @return true if the collection contains the value, otherwise false
     */
    boolean contains(E value);

    /**
     * Removes a specified value from the collection
     * @param value Object
     * @return true if successfully removed, otherwise false
     */
    boolean remove(E value);

    /**
     * Converts the collection to an array
     * @return array of objects
     */
    Object[] toArray();

    /**
     * Iterates over the whole collection and processes the values using the processor object
     * @param processor Processor
     */
    default void forEach(Processor<? super E> processor) {
        ElementsGetter<E> getter = this.createElementsGetter();

        while(getter.hasNextElement()) {
            processor.process(getter.getNextElement());
        }
    }

    /**
     * Adds all values of the source collection to this collection
     * @param other Source collection
     */
    default void addAll(Collection<? extends E> other) {
        class LocalProcessor implements Processor<E> {

            public void process(E value) {
                add(value);
            }
        }

        other.forEach(new LocalProcessor());
    }

    ElementsGetter<E> createElementsGetter();

    default void addAllSatisfying(Collection<? extends E> col, Tester<E> tester) {
        ElementsGetter<? extends E> otherGetter = col.createElementsGetter();

        while(otherGetter.hasNextElement()) {
            E obj = otherGetter.getNextElement();
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
