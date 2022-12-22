package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldValueGettersTest {

    @Test
    public void testVariants() {
        StudentRecord record = new StudentRecord("0000000013", "Raguž", "Lovro", "3");

        assertEquals("Lovro", FieldValueGetters.FIRST_NAME.get(record));
        assertEquals("Raguž", FieldValueGetters.LAST_NAME.get(record));
        assertEquals("0000000013", FieldValueGetters.JMBAG.get(record));
    }

}