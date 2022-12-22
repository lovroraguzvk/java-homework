package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.hw02.prob1.Lexer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
public class SmartScriptLexerTest {


    //	@Disabled
    @Test
    public void testNotNull() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
    }

    //	@Disabled
    @Test
    public void testNullInput() {
        // must throw!
        assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
    }

    //	@Disabled
    @Test
    public void testEmpty() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
    }

    //	@Disabled
    @Test
    public void testGetReturnsLastNext() {
        // Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
        SmartScriptLexer lexer = new SmartScriptLexer("");

        Token token = lexer.nextToken();
        assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
        assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
    }

    @Test
    public void testRadAfterEOF() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        // will obtain EOF
        lexer.nextToken();
        // will throw!
        assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
    }

    @Test
    public void testContent() {
        // When input is only of spaces, tabs, newlines, etc...
        SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");

        assertEquals("   \r\n\t    ", lexer.nextToken().getValue(), "Input had no content. Lexer should generated only EOF token.");
    }

    @Test
    public void testEscapeSigns() {
        // When input is only of spaces, tabs, newlines, etc...
        SmartScriptLexer lexer = new SmartScriptLexer("\\\\{\\\\\\\\{ \\\\\\{$");

        assertEquals("\\{\\\\{ \\{$", lexer.nextToken().getValue());
    }

    @Test
    public void testEnterTagState() {
        // When input is only of spaces, tabs, newlines, etc...
        SmartScriptLexer lexer = new SmartScriptLexer("\\{{$");

        assertThrows(SmartScriptLexerException.class, lexer::nextToken);
    }

    @Test
    public void testInvalidEscape() {
        // When input is only of spaces, tabs, newlines, etc...
        SmartScriptLexer lexer = new SmartScriptLexer("Ovo se ruši {$= \\n $}\n");

        assertEquals("Ovo se ruši ", lexer.nextToken().getValue());
        assertEquals("=", lexer.nextToken().getValue());

        assertThrows(SmartScriptLexerException.class, lexer::nextToken);

        SmartScriptLexer lexer1 = new SmartScriptLexer("Ovo se ruši \\{$= n $}\n");

        assertEquals("Ovo se ruši {$= n $}\n", lexer1.nextToken().getValue());
    }

    @Test
    public void testEscapeInString() {
        // When input is only of spaces, tabs, newlines, etc...
        SmartScriptLexer lexer = new SmartScriptLexer("A tag follows {$= \"Joe \\r \\n \\t \\\"Long\\\" Smith\"$}.");

        assertEquals("A tag follows ", lexer.nextToken().getValue());
        assertEquals("=", lexer.nextToken().getValue());
        assertEquals("Joe \r \n \t \"Long\" Smith", lexer.nextToken().getValue());
    }

    @Test
    public void testNoEmptySpaces() {
        String text = """
                This is sample text.
                {$ FOR i "1"10"1" $}
                 This is {$= i-1.35bbb"1" $}-th time this message is generated.
                {$END$}
                """;

        // When input is only of spaces, tabs, newlines, etc...
        SmartScriptLexer lexer = new SmartScriptLexer(text);

        assertEquals("This is sample text.\n", lexer.nextToken().getValue());
        assertEquals("FOR", lexer.nextToken().getValue());
        assertEquals("i", lexer.nextToken().getValue());
        assertEquals("1", lexer.nextToken().getValue());
        assertEquals("10", lexer.nextToken().getValue());
        assertEquals("1", lexer.nextToken().getValue());
        assertEquals("\n This is ", lexer.nextToken().getValue());
        assertEquals("=", lexer.nextToken().getValue());
        assertEquals("i", lexer.nextToken().getValue());
        assertEquals("-1.35", lexer.nextToken().getValue());
        assertEquals("bbb", lexer.nextToken().getValue());
        assertEquals("1", lexer.nextToken().getValue());
        assertEquals("-th time this message is generated.\n", lexer.nextToken().getValue());
        assertEquals("END", lexer.nextToken().getValue());
        assertEquals("\n", lexer.nextToken().getValue());

        assertEquals(TokenType.EOF, lexer.nextToken().getType());
        assertEquals(TokenType.EOF, lexer.getToken().getType());
    }



    private void checkTokenStream(SmartScriptLexer lexer, Token[] correctData) {
        int counter = 0;
        for(Token expected : correctData) {
            Token actual = lexer.nextToken();
            String msg = "Checking token "+counter + ":";
            assertEquals(expected.getType(), actual.getType(), msg);
            assertEquals(expected.getValue(), actual.getValue(), msg);
            counter++;
        }
    }


    private String readExample(int n) {
        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
            if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
            byte[] data = is.readAllBytes();
            return new String(data, StandardCharsets.UTF_8);
        } catch(IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }
    }

}