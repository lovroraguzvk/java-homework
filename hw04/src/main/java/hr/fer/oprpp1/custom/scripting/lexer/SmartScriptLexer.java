package hr.fer.oprpp1.custom.scripting.lexer;

import java.util.Arrays;
import java.util.Locale;

/**
 * Lexer that lexes using rules that coincides with the Smart Script rules
 */
public class SmartScriptLexer {

    private final char[] data; // ulazni tekst
    private Token token; // trenutni token
    private int currentIndex; // indeks prvog neobrađenog znaka
    private LexerState state = LexerState.TEXT;

    private final String[] emptyChars = new String[] {
            " ", "\n", "\r", "\t"
    };

    private final String[] keywords = new String[] {
            "FOR", "END"
    };

    private final String[] operators = new String[] {
            "+", "-", "*", "/", "^"
    };


    /**
     * Standard constructor that takes text and initializes lexer
     * @param text text to be parsed
     */
    public SmartScriptLexer(String text) {
        if(text == null) throw new NullPointerException();
        data = text.toCharArray();
        currentIndex = 0;
    }

    /**
     * Produces a token with a TokenType and a set value
     * @return next token in line
     * @throws SmartScriptLexerException if Smart Script incorrectly used
     */
    public Token nextToken() {
        if(token != null && token.getType() == TokenType.EOF) throw new SmartScriptLexerException("No new tokens");
        if(data.length == 0) return token = new Token(TokenType.EOF, null);

        TokenType tokenType = TokenType.EOF;
        StringBuilder tokenValue = new StringBuilder();
        int charCount = 0;
        boolean isEscape = false;

        for(; currentIndex <= data.length; currentIndex++) {

            if(currentIndex == data.length) {
                if(state != LexerState.TEXT) throw new SmartScriptLexerException("String or tag not closed");
                if(tokenType == TokenType.TEXT) return new Token(tokenType, tokenValue.toString());
                currentIndex++;
                return token = new Token(TokenType.EOF, null);
            }

            char currentChar = data[currentIndex];


            if(state == LexerState.TEXT){
                if(currentChar == '\\') {
                    isEscape = !isEscape;
                    if(!isEscape) tokenValue.append(currentChar);
                    continue;
                }

                if (currentChar == '{' && !isEscape && data[currentIndex + 1] == '$') {
                    currentIndex++;
                    setState(LexerState.TAG);

                    if(tokenValue.length() == 0) {
                        tokenValue.append("\r");
                        tokenType = TokenType.TEXT;
                    }

                    if(tokenType != TokenType.EOF) {
                        currentIndex++;
                        return token = new Token(tokenType, tokenValue.toString());
                    }
                    continue;
                }

                if (currentChar == '{' && isEscape) {
                    tokenValue.append(currentChar);
                    isEscape = false;
                    continue;
                }

                if (isEscape) throw new SmartScriptLexerException();
                tokenType = TokenType.TEXT;
                tokenValue.append(currentChar);
            }

            else if (state == LexerState.TAG) {
                if(currentIndex + 1 == data.length) throw new SmartScriptLexerException("Tag not closed.");

                //skip empty spaces
                while(isEmptyChar(currentChar)) {
                    currentIndex++;
                    currentChar = data[currentIndex];
                }

                //check if end of tag
                if(currentChar == '$') {
                    if(data[currentIndex + 1] == '}') {
                        currentIndex++;
                        setState(LexerState.TEXT); // GREŠKA -> AKO JE KRAJ TAGA I IMA PRAZNIH CHAROVA VRAĆA EOF
                        continue;
                    }
                }

                //check if start of string
                if(currentChar == '"') {
                    setState(LexerState.STRING);
                    continue;
                }

                //check if = sign
                if(currentChar == '=') {
                    currentIndex++;
                    return token = new Token(TokenType.EQUALS, String.valueOf(currentChar));
                }

                //check if function
                if(currentChar == '@' && currentIndex + 1 != data.length && Character.isLetter(data[currentIndex + 1])) {
                    tokenType = TokenType.FUNCTION;
                    currentIndex++;
                    currentChar = data[currentIndex];
                }

                //check if operator
                boolean isMinus = false;
                char finalCurrentChar = currentChar;
                if(Arrays.stream(operators).anyMatch((e) -> e.equals(String.valueOf(finalCurrentChar)))) {
                    if(currentIndex + 1 != data.length && isEmptyChar(data[currentIndex+1])) {
                        currentIndex++;
                        return token = new Token(TokenType.OPERATOR, currentChar);
                    } else if (data[currentIndex] == '-' && currentIndex+1 != data.length && isNumberChar(data[currentIndex+1])) {
                        tokenType = TokenType.PARAM;
                        currentIndex++;
                        currentChar = data[currentIndex];
                        isMinus = true;
                    } else {
                        throw new SmartScriptLexerException("Invalid parameter");
                    }
                }

                if(currentChar == '_') throw new SmartScriptLexerException("Variable or function cannot start with " + currentChar);

                if(isNumberChar(currentChar)) {
                    if(tokenType != TokenType.EOF && tokenType != TokenType.PARAM) throw new SmartScriptLexerException("Function cannot start with " + currentChar);
                    tokenType = TokenType.PARAM;
                } else if (Character.isLetter(currentChar) && tokenType != TokenType.FUNCTION) {
                    tokenType = TokenType.VAR;
                } else if (tokenType != TokenType.FUNCTION) {
                    throw new SmartScriptLexerException("Invalid parameter");
                }

                int startIndex = currentIndex;
                currentIndex++;

                boolean isDotted = false;

                while (currentIndex < data.length && !isEmptyChar(data[currentIndex])) {
                    if(data[currentIndex] == '$' && currentIndex + 1 != data.length && data[currentIndex+1] == '}') {
                        setState(LexerState.TEXT);
                        currentIndex--;
                        break;
                    }

                    if(data[currentIndex] == '"' || data[currentIndex] == '-') {
                        break;
                    }

                    if(tokenType == TokenType.PARAM || tokenType == TokenType.DOUBLE || tokenType == TokenType.INTEGER) {

                        if(currentIndex + 1 != data.length && data[currentIndex] == '.' && !isNumberChar(data[currentIndex+1])) {
                            throw new SmartScriptLexerException("Invalid number");
                        }

                        else if(data[currentIndex] == '.' && !isDotted) {
                            isDotted = true;
                            tokenType = TokenType.DOUBLE;
                        }

                        else if(data[currentIndex] == '.' && isDotted) {
                            throw new SmartScriptLexerException("Invalid number");
                        }

                        else if(!isNumberChar(data[currentIndex])) {
                            if(!isDotted) tokenType = TokenType.INTEGER;
                            break;
                        }

                    } else {

                        if (!Character.isLetter(data[currentIndex]) && !isNumberChar(data[currentIndex])) {
                            throw new SmartScriptLexerException("Invalid parameter or function name");
                        } else if (data[currentIndex] == '"') {
                            setState(LexerState.STRING);
                            currentIndex--;
                            break;
                        }
                    }
                    currentIndex++;
                }

                if(isMinus) startIndex--;

                int endIndex = currentIndex;

                if(state == LexerState.TEXT) {
                    currentIndex += 3;
                    endIndex++;
                }

                String value = new String(data, startIndex, endIndex - startIndex);

                if(Arrays.stream(keywords).anyMatch((e) -> e.equals(value.toUpperCase(Locale.ROOT))) && tokenType != TokenType.FUNCTION) {
                    return token = new Token(TokenType.KEYWORD, value.toUpperCase());
                }

                return token = new Token(tokenType, value);

            }

            else if (state == LexerState.STRING) {
                if(currentChar == '\\') {
                    isEscape = !isEscape;
                    if(!isEscape) tokenValue.append(currentChar);
                    continue;
                }

                if(isEscape) {
                    if(currentChar == 'r') tokenValue.append('\r');
                    else if(currentChar == 'n') tokenValue.append('\n');
                    else if(currentChar == 't') tokenValue.append('\t');
                    else if(currentChar == '"') tokenValue.append('"');
                    else throw new SmartScriptLexerException("Invalid string");
                    isEscape = false;
                    continue;
                }

                if(currentChar == '"') {
                    setState(LexerState.TAG);
                    currentIndex++;
                    return token = new Token(TokenType.STRING, tokenValue.toString());
                }

                tokenValue.append(currentChar);
            }

        }
        return token = new Token(TokenType.EOF, null);
    }

    /**
     * @return current token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Sets state of Lexer
     * @param state state of Lexer
     */
    public void setState(LexerState state) {
        this.state = state;
    }

    private boolean isEmptyChar(char ch) {
        return Arrays.stream(emptyChars).anyMatch((c) -> ch == c.toCharArray()[0]);
    }

    private boolean isNumberChar(char ch) {
        return ch >= '0' && ch <= '9';
    }
}
