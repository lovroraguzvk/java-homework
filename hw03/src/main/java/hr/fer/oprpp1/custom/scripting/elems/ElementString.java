package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Element with content of type String
 */
public class ElementString extends Element {

    private final String value;

    /**
     * Constructor that initializes
     * @param value String value
     */
    public ElementString(String value) {
        if (value == null) throw new NullPointerException();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String asText() {
        return '"' + value + '"';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementString that = (ElementString) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
