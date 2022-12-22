package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Element with content of type integer
 */
public class ElementConstantInteger extends Element {

    private final int value;


    /**
     * Constructor that initializes
     * @param value integer value
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * @return value of the element
     */
    public int getValue() {
        return value;
    }

    @Override
    public String asText() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementConstantInteger that = (ElementConstantInteger) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
