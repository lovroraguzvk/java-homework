package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QueryLexerTest {

    @Test
    public void testLexer() {
        QueryLexer lexer = new QueryLexer("query jmbag=\"001020\" and>= >");

        assertEquals(TokenType.WORD ,lexer.nextToken().getType());
        assertEquals(TokenType.ATTRIBUTE ,lexer.nextToken().getType());
        assertEquals(TokenType.OPERATOR ,lexer.nextToken().getType());
        assertEquals(TokenType.LITERAL ,lexer.nextToken().getType());
        assertEquals(TokenType.WORD ,lexer.nextToken().getType());
        assertEquals(TokenType.OPERATOR ,lexer.nextToken().getType());
        assertEquals(TokenType.OPERATOR ,lexer.nextToken().getType());
        assertEquals(TokenType.EOF ,lexer.nextToken().getType());
    }

    @Test
    public void testStringNotClosed() {
        QueryLexer lexer = new QueryLexer("query jmbag=\"001020 and>= >");

        assertEquals(TokenType.WORD ,lexer.nextToken().getType());
        assertEquals(TokenType.ATTRIBUTE ,lexer.nextToken().getType());
        assertEquals(TokenType.OPERATOR ,lexer.nextToken().getType());
        assertThrows(LexerException.class , lexer::nextToken);
    }

    @Test
    public void testUnknownCharacter() {
        QueryLexer lexer = new QueryLexer("query jmbag= (\"001020\" and>= >");

        assertEquals(TokenType.WORD ,lexer.nextToken().getType());
        assertEquals(TokenType.ATTRIBUTE ,lexer.nextToken().getType());
        assertEquals(TokenType.OPERATOR ,lexer.nextToken().getType());
        assertThrows(LexerException.class , lexer::nextToken);
    }



}