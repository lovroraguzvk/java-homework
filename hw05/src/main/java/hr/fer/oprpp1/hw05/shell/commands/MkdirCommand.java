package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import jdk.jshell.spi.ExecutionControl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class that defines the mkdir command
 */
public class MkdirCommand implements ShellCommand {

    private static final String COMMAND_NAME = "mkdir";
    private static final List<String> DESCRIPTION = Arrays.stream("""
            Makes a new directory with the specified name.
            First argument: name of directory.
            """.split("\n")).toList();

    /**
     * Executes the mkdir command over the specified file
     *
     * @param env the environment in which the command works
     * @param arguments takes two arguments, first is the file, and the second is the encoded charset (optional)
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" +(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        if(args.length != 1) {
            env.writeln("Incorrect number of arguments.");
            return ShellStatus.CONTINUE;
        }
        for (int i = 0; i < args.length; i++) {
            if(args[i].charAt(0) == '"' && args[i].charAt(args[i].length() - 1) == '"') {
                args[i] = args[i].substring(1, args[i].length() - 1);
            }
        }

        File file = new File(args[0]);

        if (file.exists()) {
            env.writeln("Directory already exists.");
            return ShellStatus.CONTINUE;
        }

        if (file.mkdirs()) {
            env.writeln("Directory created successfully");
        } else {
            env.writeln("Directory not created.");
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
