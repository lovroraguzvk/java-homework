package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QueryParserTest {

    @Test
    public void testParser() {
        QueryParser parser = new QueryParser("query jmbag=\"000001\" and firstname=\"Lovro\"");


    }

}