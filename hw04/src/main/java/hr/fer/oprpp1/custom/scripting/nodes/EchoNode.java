package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

import java.util.Arrays;

/**
 * Echo node with a variable amount of variables
 */
public class EchoNode extends Node{

    private final Element[] elements;

    /**
     * Constructor of echo node
     * @param elements elements of echo-node
     */
    public EchoNode(Element... elements) {
        this.elements = elements;
    }

    /**
     * @return elements of echo-node
     */
    public Element[] getElements() {
        return elements;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder("{$= ");

        for(Element element : elements) {
            text.append(element.asText()).append(" ");
        }

        text.append("$}");
        return text.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EchoNode echoNode = (EchoNode) o;
        return Arrays.equals(elements, echoNode.elements);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }
}
