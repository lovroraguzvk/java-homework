package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Class that defines the charsets command which returns all available charsets
 */
public class CharsetsCommand implements ShellCommand {
    private static final String COMMAND_NAME = "charsets";
    private static final List<String> DESCRIPTION = Arrays.stream("""
            Returns list of available charsets.
            Takes no arguments.
            """.split("\n")).toList();

    /**
     * Executes the charsets command over the specified file
     *
     * @param env the environment in which the command works
     * @param arguments doesn't take any arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if(arguments.length() > 0) {
            env.write("Incorrect number of arguments.");
            return ShellStatus.CONTINUE;
        }

        Charset.availableCharsets().forEach((k,v) -> env.writeln(k));
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
