package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter {

    boolean hasNextElement();

    Object getNextElement();

    default void processRemaining(Processor P) {
        while(this.hasNextElement()) {
            P.process(this.getNextElement());
        }
    }
}
