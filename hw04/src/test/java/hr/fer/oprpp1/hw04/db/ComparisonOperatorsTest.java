package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComparisonOperatorsTest {

    @Test
    public void testVariants() {
        assertTrue(ComparisonOperators.EQUALS.satisfied("Matej", "Matej"));
        assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Not Matej", "Matej"));
        assertTrue(ComparisonOperators.GREATER.satisfied("Matej2", "Matej1"));
        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Matej", "Matej"));
        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Matej2", "Matej1"));
        assertTrue(ComparisonOperators.LESS.satisfied("Matej1", "Matej2"));
        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Matej", "Matej"));
        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Matej1", "Matej2"));
        assertTrue(ComparisonOperators.LIKE.satisfied("Matej", "Ma*"));
        assertTrue(ComparisonOperators.LIKE.satisfied("Matej", "Mat*ej"));
        assertTrue(ComparisonOperators.LIKE.satisfied("Matej", "*ej"));
    }

}