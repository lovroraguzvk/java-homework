package hr.fer.oprpp1.hw04.db;

import java.util.Arrays;
import java.util.Locale;

/**
 * Lexer for the query
 */
public class QueryLexer {

    private final String query;
    private int currentIndex = 0;
    private Token token;

    private static final String[] ATTRIBUTES = new String[]{
            "firstname",
            "lastname",
            "jmbag"
    };

    private static final String[] OPERATORS = new String[]{
            "=",
            ">",
            "<",
            ">=",
            "<=",
            "LIKE"
    };

    public QueryLexer(String query) {
        if(query == null) throw new NullPointerException();
        if(query.length() == 0) token = new Token(TokenType.EOF, null);

        this.query = query;
    }

    /**
     * Returns next token
     * @return next token
     * @throws LexerException exception in the lexer
     */
    public Token nextToken() {
        if(currentIndex == query.length()) {
            if(token.getType() != TokenType.EOF) return token = new Token(TokenType.EOF, null);
            return token;
        }

        for(;currentIndex < query.length(); currentIndex++) {
            if(query.charAt(currentIndex) == ' ' || query.charAt(currentIndex) == '\n' || query.charAt(currentIndex) == '\t') continue;

            if(query.charAt(currentIndex) == '=') {
                currentIndex++;
                return token = new Token(TokenType.OPERATOR, "=");
            }

            if(query.charAt(currentIndex) == '>'){
                currentIndex++;

                if(currentIndex == query.length())
                    return token = new Token(TokenType.OPERATOR, ">");

                if(query.charAt(currentIndex) == '=') {
                    currentIndex++;
                    return token = new Token(TokenType.OPERATOR, ">=");
                }
                return token = new Token(TokenType.OPERATOR, ">");
            }

            if(query.charAt(currentIndex) == '<') {
                currentIndex++;

                if(currentIndex == query.length())
                    return token = new Token(TokenType.OPERATOR, "<");

                if(query.charAt(currentIndex) == '=') {
                    currentIndex++;
                    return token = new Token(TokenType.OPERATOR, "<=");
                }
                return token = new Token(TokenType.OPERATOR, "<");
            }

            if(query.charAt(currentIndex) == '"') {
                currentIndex++;
                int startIndex = currentIndex;

                while(query.charAt(currentIndex) != '"') {
                    if(currentIndex == query.length() - 1) throw new LexerException("String literal not closed.");

                    currentIndex++;
                }

                int endIndex = currentIndex;
                currentIndex++;

                return token = new Token(TokenType.LITERAL, String.copyValueOf(query.toCharArray(), startIndex, endIndex - startIndex));
            }

            if(Character.isLetter(query.charAt(currentIndex))) {
                int startIndex = currentIndex;

                while(currentIndex != query.length() &&
                        !(query.charAt(currentIndex) == ' ' || query.charAt(currentIndex) == '\n'
                        || query.charAt(currentIndex) == '\t' || query.charAt(currentIndex) == '='
                        || query.charAt(currentIndex) == '"' || query.charAt(currentIndex) == '<'
                        || query.charAt(currentIndex) == '>')) {
                    currentIndex++;
                }

                String value = String.copyValueOf(query.toCharArray(), startIndex, currentIndex - startIndex);

                if(value.equals("LIKE")) {
                    return token = new Token(TokenType.OPERATOR, value);
                }

                if(Arrays.asList(ATTRIBUTES).contains(value.toLowerCase(Locale.ROOT)))
                    return token = new Token(TokenType.ATTRIBUTE, value);

                return token = new Token(TokenType.WORD, value);
            }

            throw new LexerException("Unexpected character at position " + currentIndex + ".");
        }

        return null;
    }

    /**
     * Returns current token
     * @return current token
     */
    public Token getToken() {
        return token;
    }

    public boolean hasNextToken() {
        return token.getType() != TokenType.EOF;
    }
}
