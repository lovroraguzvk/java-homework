package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.scripting.elems.Element;

import java.util.Objects;

/**
 * Smallest coherent part of a script language
 */
public class Node {

    private ArrayIndexedCollection collection;

    /**
     * Adds a child to the node
     * @param child a Node to be added as a child
     */
    public void addChildNode(Node child) {
        if(collection == null) collection = new ArrayIndexedCollection();

        collection.add(child);
    }

    /**
     * @return number of children
     */
    public int numberOfChildren() {
        return this.collection.size();
    }

    /**
     * @param index index at which the child is at
     * @return child Node
     * @throws IndexOutOfBoundsException if index out of bounds
     */
    public Node getChild(int index) {
        if(index >= collection.size() || index < 0) throw new IndexOutOfBoundsException();
        return (Node) collection.get(index);
    }

    /**
     * Prints a tree of all Nodes that are children recursively in a formatted fashion
     * @return
     */
    public String nodeTree() {
        return nodeTreeRecursive(1);
    }

    private String nodeTreeRecursive(int m) {
        StringBuilder sBuilder = new StringBuilder(this.toStringBasic());
        sBuilder.append("\n");
        if(collection != null) {
            for (int i = 0; i < numberOfChildren(); i++) {
                sBuilder.append("----".repeat(Math.max(0, m)));
                sBuilder.append(getChild(i).nodeTreeRecursive(m+1));
            }
        }
        return sBuilder.toString();
    }

    private String toStringBasic() {
        String s = this.getClass().getName();
        return s.substring(s.lastIndexOf('.') + 1);
    }



    protected boolean childrenEqual(Node other) {
        ElementsGetter getter = collection.createElementsGetter();
        ElementsGetter otherGetter = other.collection.createElementsGetter();

        while(getter.hasNextElement()) {
            Object e1 = getter.getNextElement();
            Object e2 = otherGetter.getNextElement();
            if(!e1.equals(e2)) return false;
        }

        return !otherGetter.hasNextElement();
    }
}
