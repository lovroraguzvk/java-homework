package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Element with content of a variable
 */
public class ElementVariable extends Element {

    private final String name;

    /**
     * Constructor that initializes
     * @param name name of variable
     */
    public ElementVariable(String name) {
        if(name == null) throw new NullPointerException();
        this.name = name;
    }

    /**
     * @return name of variable
     */
    public String getName() {
        return name;
    }

    @Override
    public String asText() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementVariable that = (ElementVariable) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
