package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Objects;

/**
 * Node that contains text
 */
public class TextNode extends Node {

    private String text;

    /**
     * Constructor that initializes the TextNode
     * @param text text
     */
    public TextNode(String text) {
        this.text = text;
    }

    /**
     * @return content of the text-node
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextNode textNode = (TextNode) o;
        return Objects.equals(text, textNode.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }
}
