package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

import java.util.Objects;

/**
 * A Node that represents a For-loop
 */
public class ForLoopNode extends Node {

    private final ElementVariable variable;
    private final Element startExpression;
    private final Element endExpression;
    private final Element stepExpression;

    /**
     * Constructor for the for-loop Node
     * Increments with 1
     * @param variable variable to be incremented
     * @param startExpression initial value of variable
     * @param endExpression end value of variable
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
        this(variable, startExpression, endExpression, null);
    }

    /**
     * Constructor for the for-loop Node
     * Increments with the stepExpression amount
     * @param variable variable to be incremented
     * @param startExpression initial value of variable
     * @param endExpression end value of variable
     * @param stepExpression increment amount
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
        if(variable == null || startExpression == null || endExpression == null) throw new NullPointerException();
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * @return ElementVariable of the for-loop Node
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * @return starting amount of variable
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * @return end amount of variable
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * @return step amount of increment
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(String.format("{$ FOR %s %s %s ",
                variable.asText(), startExpression.asText(), endExpression.asText()));

        if(stepExpression != null) text.append(stepExpression.asText()).append(" ");
        text.append("$}");

        for(int i = 0; i < numberOfChildren(); i++) {
            Node child = getChild(i);
            text.append(child.toString());
        }

        text.append("{$ END $}");

        return text.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForLoopNode that = (ForLoopNode) o;

        return childrenEqual(that) && variable.equals(that.variable)
                && startExpression.equals(that.startExpression)
                && endExpression.equals(that.endExpression)
                && Objects.equals(stepExpression, that.stepExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), variable, startExpression, endExpression, stepExpression);
    }
}
