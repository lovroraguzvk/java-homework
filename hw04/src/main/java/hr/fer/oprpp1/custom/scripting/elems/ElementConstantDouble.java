package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Element with content of type double
 */
public class ElementConstantDouble extends Element {

    private final double value;


    /**
     * Constructor that initializes
     * @param value double value
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * @return value of the element
     */
    public double getValue() {
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
        ElementConstantDouble that = (ElementConstantDouble) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
