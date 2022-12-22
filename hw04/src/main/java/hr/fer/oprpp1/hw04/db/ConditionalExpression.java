package hr.fer.oprpp1.hw04.db;

/**
 * Record class that serves as a wrapper for a whole conditional expression
 */
public class ConditionalExpression {

    private final IFieldValueGetter fieldGetter;
    private final String stringLiteral;
    private final IComparisonOperator comparisonOperator;

    public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
        this.fieldGetter = fieldGetter;
        this.stringLiteral = stringLiteral;
        this.comparisonOperator = comparisonOperator;
    }

    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }

    public String getStringLiteral() {
        return stringLiteral;
    }

    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }
}
