package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Element that contains a function variable
 */
public class ElementFunction extends Element {

    private final String name;


    /**
     * Constructor that initializes the ElementFunction
     * @param name function name
     */
    public ElementFunction(String name) {
        if(name == null) throw new NullPointerException();
        this.name = name;
    }

    /**
     * @return name of function
     */
    public String getName() {
        return name;
    }

    @Override
    public String asText() {
        return "@" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementFunction that = (ElementFunction) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
