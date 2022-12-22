package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * A class takes a list of conditional expressions and then acts as a custom filter for whatever
 * student records
 */
public class QueryFilter implements IFilter{

    private final List<ConditionalExpression> expressions;

    public QueryFilter(List<ConditionalExpression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public boolean accepts(StudentRecord record) {
        for(ConditionalExpression expression : expressions) {
            if(!expression.getComparisonOperator().satisfied(expression.getFieldGetter().get(record), expression.getStringLiteral())) {
                return false;
            }
        }
        return true;
    }
}
