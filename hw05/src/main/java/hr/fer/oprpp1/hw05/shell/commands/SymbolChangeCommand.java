package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Class that defines the symbol command
 */
public class SymbolChangeCommand implements ShellCommand {
    private static final String COMMAND_NAME = "symbol";
    private static final List<String> DESCRIPTION = Arrays.stream("""
            Able to change shell symbols.
            First argument: which symbol to change, if no second argument displays the current symbol.
                - options are: PROMPT - the prompt symbol
                               MULTILINE - the multiline symbol
                               MORELINES - the more lines symbol
            Second argument (optional): the symbol to change it to.
            """.split("\n")).toList();

    /**
     * Executes the symbol command
     *
     * @param env the environment in which the command works
     * @param arguments takes one or two arguments, if one it will print the specified symbol, if two it will set it
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.trim().split(" +");

        if(args.length == 1) {
            switch(arguments) {
                case "PROMPT" -> env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
                case "MORELINES" -> env.writeln("Symbol for PROMPT is '" + env.getMultilineSymbol() + "'");
                case "MULTILINE" -> env.writeln("Symbol for PROMPT is '" + env.getMorelinesSymbol() + "'");
                default -> env.writeln("Invalid argument for command symbol, options are PROMPT, MULTILINE or MORELINES");
            }
        }

        else if(args.length == 2) {
            if(arguments.split(" ")[1].length() != 1) {
                env.writeln("Invalid symbol.");
                return ShellStatus.CONTINUE;
            }
            char ch = arguments.split(" ")[1].toCharArray()[0];
            switch(arguments.split(" ")[0]) {
                case "PROMPT" -> {
                    env.write("Symbol for PROMPT changed from '" + env.getMultilineSymbol() + "' to '" + ch + "'\n");
                    env.setPromptSymbol(ch);
                }
                case "MORELINES" -> {
                    env.write("Symbol for MORELINES changed from '" + env.getMultilineSymbol() + "' to '" + ch + "'\n");
                    env.setMultilineSymbol(ch);
                }
                case "MULTILINE" -> {
                    env.write("Symbol for MULTILINE changed from '" + env.getMorelinesSymbol() + "' to '" + ch + "'\n");
                    env.setMorelinesSymbol(ch);
                }
                default -> env.writeln("Invalid argument for command symbol, options are PROMPT, MULTILINE or MORELINES.");
            }
        }

        else {
            env.writeln("Incorrect number of arguments.");
        }



        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
