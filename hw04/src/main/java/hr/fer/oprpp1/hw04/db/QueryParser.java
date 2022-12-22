package hr.fer.oprpp1.hw04.db;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Parse for the query
 */
public class QueryParser {

    private List<ConditionalExpression> queryElements = new ArrayList<>();

    /**
     * Takes the query that is immediately parsed in the constructor
     * @param query the query to be parsed
     */
    public QueryParser(String query) {
        QueryLexer lexer = new QueryLexer(query);

        Token[] lastTwoTokens = new Token[2];

        lexer.nextToken();
        while(lexer.hasNextToken()) {
            // System.out.printf("%s %s\n", lexer.getToken().getType(), lexer.getToken().getValue());

            switch(lexer.getToken().getType()) {
                case WORD -> {
                    switch(lexer.getToken().getValue().toLowerCase(Locale.ROOT)) {
                        case "query" -> {
                            if(!lexer.getToken().getValue().equals("query")) throw new ParserException("Unexpected token '" + lexer.getToken().getValue() + "'");

                            if(lastTwoTokens[0] != null) {
                                throw new ParserException("Unexpected token '" + lexer.getToken().getValue() + "', must only be at beginning");
                            }
                            lastTwoTokens[0] = lexer.getToken();

                        }
                        case "and" -> {
                            if(lastTwoTokens[0] == null) throw new ParserException("Query must start with keyword 'query'");

                            if(!(lastTwoTokens[0].getType() == TokenType.LITERAL && lastTwoTokens[1].getType() == TokenType.OPERATOR))
                                throw new ParserException("Unexpected token '" + lexer.getToken().getValue() + "'.");

                            lastTwoTokens[1] = lastTwoTokens[0];
                            lastTwoTokens[0] = lexer.getToken();
                        }
                        default -> throw new ParserException("Unexpected token '" + lexer.getToken().getValue() + "'");

                    }
                }
                case ATTRIBUTE -> {
                    if(lastTwoTokens[0] == null) throw new ParserException("Query must start with keyword 'query'");

                    if (!(lastTwoTokens[0].getValue().toLowerCase(Locale.ROOT).equals("and") ||
                            lastTwoTokens[0].getValue().equals("query"))) {
                        throw new ParserException("Unexpected token '" + lexer.getToken().getValue() + "'.");
                    }

                    lastTwoTokens[1] = lastTwoTokens[0];
                    lastTwoTokens[0] = lexer.getToken();
                }
                case OPERATOR -> {
                    if(lastTwoTokens[0] == null) throw new ParserException("Query must start with keyword 'query'");

                    if(lastTwoTokens[0].getType() != TokenType.ATTRIBUTE)
                        throw new ParserException("Unexpected token '" + lexer.getToken().getValue() + "'.");

                    lastTwoTokens[1] = lastTwoTokens[0];
                    lastTwoTokens[0] = lexer.getToken();
                }
                case LITERAL -> {
                    if(lastTwoTokens[0] == null) throw new ParserException("Query must start with keyword 'query'");

                    if(lastTwoTokens[0].getType() != TokenType.OPERATOR || lastTwoTokens[1].getType() != TokenType.ATTRIBUTE)
                        throw new ParserException("Unexpected token '" + lexer.getToken().getValue() + "'");

                    IFieldValueGetter fieldGetter = null;
                    IComparisonOperator operator = null;

                    switch(lastTwoTokens[1].getValue().toLowerCase(Locale.ROOT)) {
                        case "lastname" -> fieldGetter = FieldValueGetters.LAST_NAME;
                        case "firstname" -> fieldGetter = FieldValueGetters.FIRST_NAME;
                        case "jmbag" -> fieldGetter = FieldValueGetters.JMBAG;
                    }

                    switch(lastTwoTokens[0].getValue().toLowerCase(Locale.ROOT)) {
                        case "=" -> operator = ComparisonOperators.EQUALS;
                        case ">" -> operator = ComparisonOperators.GREATER;
                        case "<" -> operator = ComparisonOperators.LESS;
                        case "<=" -> operator = ComparisonOperators.LESS_OR_EQUALS;
                        case ">=" -> operator = ComparisonOperators.GREATER_OR_EQUALS;
                        case "like" -> operator = ComparisonOperators.LIKE;
                    }



                    queryElements.add(new ConditionalExpression(
                            fieldGetter,
                            lexer.getToken().getValue(),
                            operator
                    ));

                    lastTwoTokens[1] = lastTwoTokens[0];
                    lastTwoTokens[0] = lexer.getToken();
                }
            }

            lexer.nextToken();
        }
    }

    /**
     * @return true if is direct query, false if not
     */
    public boolean isDirectQuery() {
        return queryElements.size() == 1 && queryElements.get(0).getFieldGetter() == FieldValueGetters.JMBAG
                && queryElements.get(0).getComparisonOperator() == ComparisonOperators.EQUALS;
    }

    /**
     * @return queried JMBAG
     * @throws IllegalStateException if the query isn't a direct one
     */
    public String getQueriedJMBAG() {
        if(!isDirectQuery()) throw new IllegalStateException("Its not a direct query!");

        return queryElements.get(0).getStringLiteral();
    }

    /**
     * @return List of conditional expressions in the query
     */
    public List<ConditionalExpression> getQuery() {
        return queryElements;
    }
}
