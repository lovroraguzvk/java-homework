package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionalExpressionTest {

    @Test
    public void testVariants() {
        StudentRecord record = new StudentRecord("0000000013", "Raguž", "Lovro", "3");

        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.FIRST_NAME,
                "Lovr*",
                ComparisonOperators.LIKE
        );

        boolean recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record), // returns lastName from given record
                expr.getStringLiteral() // returns "Lovr*"
        );

        assertTrue(recordSatisfies);
    }

}