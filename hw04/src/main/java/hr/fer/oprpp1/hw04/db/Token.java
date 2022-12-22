package hr.fer.oprpp1.hw04.db;

public class Token {
    private TokenType type;
    private String value;

    public Token(TokenType type, String value) {
        if(type == null) throw new IllegalArgumentException("Token type can not be null.");
        this.type = type;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }

}
