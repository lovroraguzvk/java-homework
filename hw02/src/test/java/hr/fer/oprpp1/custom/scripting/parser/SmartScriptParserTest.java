package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.lexer.LexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SmartScriptParserTest {

    @Test
    public void testParser() {
        // When input is only of spaces, tabs, newlines, etc...
        String text = """
                This is sample text.
                {$ FOR i "1"10"1" $}
                 This is {$= i $}-th time this message is generated.
                {$END$}
                {$FOR i "0" 10 2 $}
                {$FOR i "0" 10 2 $}
                 sin({$=i$}^2) = {$= i i * @sin "0.000" @decfmt $}
                {$END$} 
                 sin({$=i$}^2) = {$= i i * @sin "0.000" @decfmt $}
                {$END$}
                """;

        String expected = """
                DocumentNode
                ----TextNode
                ----ForLoopNode
                --------TextNode
                --------EchoNode
                --------TextNode
                ----TextNode
                ----ForLoopNode
                --------TextNode
                --------ForLoopNode
                ------------TextNode
                ------------EchoNode
                ------------TextNode
                ------------EchoNode
                ------------TextNode
                --------TextNode
                --------EchoNode
                --------TextNode
                --------EchoNode
                --------TextNode
                ----TextNode
                """;


        SmartScriptParser parser = new SmartScriptParser(text);
        DocumentNode document = parser.getDocumentNode();

        assertEquals(expected, parser.parseTree());

        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();

        assertEquals(document, document2);
    }

    @Test
    public void testTooManyForOpen() {
        String text = """
                {$ FOR i "1"10"1" $}
                {$ FOR i "1"10"1" $}
                {$end$}
                """;

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testTooFewForOpen() {
        String text = """
                {$ FOR i "1"10"1" $}
                {$ FOR i "1"10"1" $}
                {$end$}
                {$end$}
                {$end$}
                """;

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testKeywordOrEqualsNotFirst() {
        String text = """
                {$ i 2 10 3 $}
                {$end$}
                """;

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testTooManyArguments() {
        String text = """
                {$for i 1 10 2 3 $}
                {$end$}
                """;

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testTooFewArguments() {
        String text = """
                {$for i 1 $}
                {$end$}
                """;

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testInvalidArguments() {
        String text = """
                {$for i 1 bb 2$}
                {$end$}
                """;

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }


}