package hr.fer.oprpp1.hw02.prob1;

import java.util.Arrays;

public class Lexer {

    private final char[] data; // ulazni tekst
    private Token token; // trenutni token
    private int currentIndex; // indeks prvog neobrađenog znaka
    private LexerState state = LexerState.BASIC;

    private final String[] emptyChars = new String[] {
            " ", "\n", "\r", "\t"
    };

    private final String[] utf8chars = new String[] {
            "ć", "č", "š", "đ", "ž", "Ć", "Č", "Š", "Đ", "Ž"
    };



    // konstruktor prima ulazni tekst koji se tokenizira
    public Lexer(String text) {
        if(text == null) throw new NullPointerException();
        data = text.toCharArray();
        currentIndex = 0;
    }

    // generira i vraća sljedeći token
    // baca LexerException ako dođe do pogreške
    public Token nextToken() {
        if(token != null && token.getType() == TokenType.EOF) throw new LexerException();

        TokenType tokenType = TokenType.EOF;
        StringBuilder tokenValue = new StringBuilder();
        int charCount = 0;
        boolean isEscape = false;

        for(; currentIndex <= data.length; currentIndex++) {

            if(currentIndex == data.length) {
                if(isEscape) throw new LexerException("Can not end with a single '\\' character.");

                if(tokenType == TokenType.NUMBER)
                    return token = new Token(tokenType, Long.parseLong(String.valueOf(tokenValue)));

                if(tokenType == TokenType.WORD)
                    return token = new Token(tokenType, tokenValue.toString());

                return token = new Token(TokenType.EOF, null);
            }

            char currentChar = data[currentIndex];

            if(currentChar == '#') {
                if(state == LexerState.BASIC) setState(LexerState.EXTENDED);
                if(state == LexerState.EXTENDED) setState(LexerState.BASIC);
            }

            if(currentChar == '\\') {
                if(state == LexerState.EXTENDED) {
                    if(isEscape) throw new LexerException("Can not end with a single '\\' character.");
                    isEscape = false;
                } else {
                    isEscape = !isEscape;
                    if (isEscape) continue;
                }
            }

            if(isEmptyChar()) {
                if(tokenType != TokenType.EOF) {
                    currentIndex++;

                    if(tokenType == TokenType.NUMBER)
                        return token = new Token(tokenType, Long.parseLong(String.valueOf(tokenValue)));

                    return token = new Token(tokenType, tokenValue.toString());
                }
            }

            else if(Character.isLetter(currentChar) || currentChar == '\\' || isNumber() && isEscape
                    || (isNumber() || isSymbol()) && state == LexerState.EXTENDED) {

                if(Character.isLetter(currentChar) && isEscape && state == LexerState.BASIC) throw new LexerException();

                isEscape = false;

                if(tokenType != TokenType.EOF && tokenType != TokenType.WORD)
                    return token = new Token(tokenType, Long.parseLong(String.valueOf(tokenValue)));

                if(charCount == 0) tokenType = TokenType.WORD;
                charCount++;
                tokenValue.append(currentChar);
            }

            else if(isNumber()) {
                if(tokenType != TokenType.EOF && tokenType != TokenType.NUMBER)
                    return token = new Token(tokenType, String.valueOf(tokenValue));

                if(charCount == 0) tokenType = TokenType.NUMBER;
                charCount++;
                tokenValue.append(currentChar);

                try {
                    Long.parseLong(String.valueOf(tokenValue));
                } catch (NumberFormatException e) {
                    throw new LexerException("Long number is too big");
                }
            }

            else {                                  //ako nisu niti jedno od drugih onda je simbol
                if(tokenType != TokenType.EOF) {
                    if(tokenType == TokenType.NUMBER)
                        return token = new Token(tokenType, Long.parseLong(String.valueOf(tokenValue)));

                    return token = new Token(tokenType, tokenValue.toString());
                }

                currentIndex++;
                return token = new Token(TokenType.SYMBOL, currentChar);
            }
        }

        return token = new Token(TokenType.EOF, null);
    }

    // vraća zadnji generirani token; može se pozivati
    // više puta; ne pokreće generiranje sljedećeg tokena
    public Token getToken() {
        return token;
    }

    public void setState(LexerState state) {
        if(state == null) throw new NullPointerException();

        this.state = state;
    }

    private boolean isLetter() {
        boolean returnValue = data[currentIndex] >= 'a' && data[currentIndex] <= 'z' ||
                data[currentIndex] >= 'A' && data[currentIndex] <= 'Z';

        returnValue = returnValue || Arrays.stream(utf8chars).anyMatch((c) -> data[currentIndex] == c.toCharArray()[0]);

        return returnValue;
    }

    private boolean isNumber() {
        return data[currentIndex] >= '0' && data[currentIndex] <= '9';
    }

    private boolean isEmptyChar() {
        return Arrays.stream(emptyChars).anyMatch((c) -> data[currentIndex] == c.toCharArray()[0]);
    }

    private boolean isSymbol() {
        return !isEmptyChar() && !isNumber() && !isLetter();
    }

}
