package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter<T> {

    boolean hasNextElement();

    T getNextElement();

    default void processRemaining(Processor<? super T> P) {
        while(this.hasNextElement()) {
            P.process(this.getNextElement());
        }
    }
}
