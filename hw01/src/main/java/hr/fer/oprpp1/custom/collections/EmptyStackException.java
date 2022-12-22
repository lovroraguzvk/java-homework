package hr.fer.oprpp1.custom.collections;

/**
 * Runtime exception that occurs when user tries to pop an element from an empty stack
 */
public class EmptyStackException extends RuntimeException {

    /**
     * Default constructor
     */
    EmptyStackException() {
        super();
    }
}
