package hr.fer.oprpp1.custom.scripting.nodes;

public class DocumentNode extends Node {

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < numberOfChildren(); i++) {
            Node child = getChild(i);
            text.append(child.toString());
        }

        return text.toString();
    }

    @Override
    public boolean equals(Object o) {
        return childrenEqual((Node) o);
    }
}
