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
import java.util.function.BiConsumer;

/**
 * Class that defines the date command
 */
public class DateCommand implements ShellCommand {

    private static final String COMMAND_NAME = "date";
    private static final List<String> DESCRIPTION = Arrays.stream("""
            Shows the current date
            """.split("\n")).toList();

    /**
     * Executes the date
     *
     * @param env the environment in which the command works
     * @param arguments takes no arguments
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" +");
        if(args.length > 1) {
            env.writeln("Incorrect number of arguments.");
            return ShellStatus.CONTINUE;
        }

        env.writeln(String.valueOf(java.time.LocalDate.now()));

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
