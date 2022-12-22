package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

import java.util.Locale;

/**
 * Parser that parses Smart Script text
 */
public class SmartScriptParser {

    private final SmartScriptLexer lexer;
    private final DocumentNode documentNode = new DocumentNode();

    /**
     * Standard constructor that takes text and immediately parses it
     * @param text Text to be parsed
     * @throws SmartScriptParserException if Smart Script incorrectly used
     */
    public SmartScriptParser(String text) {
        lexer = new SmartScriptLexer(text);
        parseText();
    }

    private void parseText() {
        Token token = lexer.nextToken();
        Node currentNode = documentNode;

        ObjectStack stack = new ObjectStack();
        stack.push(currentNode);

        while(token.getType() != TokenType.EOF) {

            if(token.getType() == TokenType.TEXT) {
                currentNode.addChildNode(new TextNode(String.valueOf(token.getValue())));
                token = lexer.nextToken();
                continue;
            }
            if(token.getType() == TokenType.EQUALS) {
                ArrayIndexedCollection collection = new ArrayIndexedCollection();
                token = lexer.nextToken();
                while(token.getType() != TokenType.TEXT) {
                    switch(token.getType()) {
                        case INTEGER -> collection.add(new ElementConstantInteger(Integer.parseInt(token.getValue().toString())));
                        case DOUBLE -> collection.add(new ElementConstantDouble(Double.parseDouble(token.getValue().toString())));
                        case STRING -> collection.add(new ElementString(String.valueOf(token.getValue())));
                        case VAR -> collection.add(new ElementVariable(String.valueOf(token.getValue())));
                        case OPERATOR -> collection.add(new ElementOperator(String.valueOf(token.getValue())));
                        case FUNCTION -> collection.add(new ElementFunction(String.valueOf(token.getValue())));
                        default -> throw new SmartScriptParserException("Unexpected token type: " + token.getType());
                    }
                    token = lexer.nextToken();
                }
                Element[] elements = new Element[collection.size()];
                Object[] array = collection.toArray();

                /*
                for(int i = 0; i < collection.size(); i++) {
                    System.out.println("In echo node");
                    System.out.println(array[i].getClass());
                }

                 */

                System.arraycopy(collection.toArray(), 0, elements, 0, collection.size());

                currentNode.addChildNode(new EchoNode(elements));
                continue;
            }
            if(token.getType() == TokenType.KEYWORD) {
                switch (String.valueOf(token.getValue())) {
                    case "FOR" -> {

                        String var;
                        token = lexer.nextToken();
                        if(token.getType() == TokenType.VAR) {
                            var = (String) token.getValue();
                        } else {
                            throw new SmartScriptParserException("First argument not a valid variable");
                        }

                        double parameter1;
                        token = lexer.nextToken();
                        if(token.getType() == TokenType.STRING || token.getType() == TokenType.PARAM ||
                            token.getType() == TokenType.INTEGER || token.getType() == TokenType.DOUBLE) {

                            parameter1 = Double.parseDouble(token.getValue().toString());

                        } else {
                            throw new SmartScriptParserException("First param not valid or non-existent");
                        }
                        Element element1;
                        if(token.getType() == TokenType.INTEGER) element1 = new ElementConstantInteger((int)parameter1);
                        else element1 = new ElementConstantDouble(parameter1);


                        double parameter2;
                        token = lexer.nextToken();
                        if(token.getType() == TokenType.STRING || token.getType() == TokenType.PARAM ||
                                token.getType() == TokenType.INTEGER || token.getType() == TokenType.DOUBLE) {

                            parameter2 = Double.parseDouble(token.getValue().toString());

                        } else {
                            throw new SmartScriptParserException("Second param not valid or non-existent");
                        }
                        Element element2;
                        if(token.getType() == TokenType.INTEGER) element2 = new ElementConstantInteger((int)parameter2);
                        else element2 = new ElementConstantDouble(parameter2);


                        token = lexer.nextToken();
                        if (lexer.getToken().getType() == TokenType.TEXT) {
                            ForLoopNode newForLoopNode = new ForLoopNode(new ElementVariable(var), element1, element2);
                            stack.push(currentNode);
                            currentNode.addChildNode(newForLoopNode);
                            currentNode = newForLoopNode;
                            continue;
                        }

                        double parameter3;
                        if(token.getType() == TokenType.STRING || token.getType() == TokenType.PARAM ||
                                token.getType() == TokenType.INTEGER || token.getType() == TokenType.DOUBLE) {

                            parameter3 = Double.parseDouble(token.getValue().toString());

                        } else {
                            throw new SmartScriptParserException("Third param not a valid argument");
                        }
                        Element element3;
                        if(token.getType() == TokenType.INTEGER) element3 = new ElementConstantInteger((int)parameter3);
                        else element3 = new ElementConstantDouble(parameter3);

                        token = lexer.nextToken();

                        if (lexer.getToken().getType() == TokenType.TEXT) {
                            ForLoopNode newForLoopNode = new ForLoopNode(new ElementVariable(var), element1, element2, element3);
                            stack.push(currentNode);
                            currentNode.addChildNode(newForLoopNode);
                            currentNode = newForLoopNode;
                            continue;
                        }

                        throw new SmartScriptParserException("Too many arguments");
                    }
                    case "END" -> {
                        if(stack.size() == 1) {
                            throw new SmartScriptParserException("For loop never opened");
                        }

                        currentNode = (Node)stack.pop();

                        token = lexer.nextToken();

                        if(token.getType() != TokenType.TEXT && token.getType() != TokenType.EOF) {
                            throw new SmartScriptParserException("Too many arguments in END node");
                        }
                    }
                }
            } else {
                throw new SmartScriptParserException("First element in tag must be keyword or equals sign");
            }
        }
        if (stack.size() != 1) throw new SmartScriptParserException("One or more For-loops not closed");
    }

    public String parseTree() {
        return documentNode.nodeTree();
    }


    public DocumentNode getDocumentNode() {
        return documentNode;
    }
}
