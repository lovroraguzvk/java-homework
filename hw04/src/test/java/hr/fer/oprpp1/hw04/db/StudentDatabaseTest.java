package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDatabaseTest {

    private static final String PATH_OF_DATABASE = "F:\\Fakultet\\5_semestar\\hw04-0036536032\\database.txt";

    private static class FilterAlwaysTrue implements IFilter {

        @Override
        public boolean accepts(StudentRecord record) {
            return true;
        }
    }

    private static class FilterAlwaysFalse implements IFilter {

        @Override
        public boolean accepts(StudentRecord record) {
            return false;
        }
    }

    @Test
    public void testForJMBAG() {
        List<String> lines = null;
        try {
             lines = Files.readAllLines(Path.of(PATH_OF_DATABASE));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        List<StudentRecord> records = new ArrayList<>();

        for(String line : lines) {
            String[] elems = line.split("\t");
            records.add(new StudentRecord(elems[0], elems[1], elems[2], elems[3]));
        }

        StudentDatabase database = new StudentDatabase(records);

        assertEquals("Mateja", database.forJMBAG("0000000013").getFirstName());
        assertNull(database.forJMBAG("0000010013"));

        assertEquals(records, database.filter(new FilterAlwaysTrue()));
        assertEquals(new ArrayList<>(), database.filter(new FilterAlwaysFalse()));
    }
}