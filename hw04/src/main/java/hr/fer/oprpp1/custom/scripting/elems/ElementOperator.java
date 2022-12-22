package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Element with content of an operator
 */
public class ElementOperator extends Element{

    private final String symbol;


    /**
     * Construrctor that initializes
     * @param symbol symbol of operator
     */
    public ElementOperator(String symbol) {
        if(symbol == null) throw new NullPointerException();
        this.symbol = symbol;
    }

    /**
     * @return symbol of operator
     */
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String asText() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementOperator that = (ElementOperator) o;
        return symbol.equals(that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
