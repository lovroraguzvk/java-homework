package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

/**
 * Class that defines the exit command
 */
public class ExitCommand implements ShellCommand {

    private static final String COMMAND_NAME = "exit";
    private static final List<String> DESCRIPTION = Arrays.stream("""
            Exits the shell.
            Takes no arguments.
            """.split("\n")).toList();
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if(arguments.length() != 0) {
            env.writeln("Incorrect number of arguments.");
            return ShellStatus.CONTINUE;
        }

        return ShellStatus.TERMINATE;
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
