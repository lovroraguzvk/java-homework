package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Smallest unit of a script language
 */
public class Token {
    private final TokenType type;
    private final Object value;

    /**
     * Standard constructor of token
     * @param type type of token
     * @param value value of token
     * @throws IllegalArgumentException if type is null
     */
    public Token(TokenType type, Object value) {
        if(type == null) throw new IllegalArgumentException("Token type can not be null.");
        this.type = type;
        this.value = value;
    }

    /**
     * @return value of token
     */
    public Object getValue() {
        return value;
    }

    /**
     * @return type of token
     */
    public TokenType getType() {
        return type;
    }
}
