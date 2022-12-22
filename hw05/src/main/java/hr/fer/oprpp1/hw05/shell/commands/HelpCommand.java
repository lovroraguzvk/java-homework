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
 * Class that defines the help command
 */
public class HelpCommand implements ShellCommand {

    private static final String COMMAND_NAME = "help";
    private static final List<String> DESCRIPTION = Arrays.stream("""
            Prints all files and folders recursively in the specified directory.
            First argument: the directory.
            """.split("\n")).toList();

    /**
     * Executes the help command over the specified command
     *
     * @param env the environment in which the command works
     * @param arguments takes one or none arguments, if none returns all command names, if one returns the description of the specified command
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" +");
        if(args.length > 1) {
            env.writeln("Incorrect number of arguments.");
            return ShellStatus.CONTINUE;
        }

        if(arguments.length() == 0) {
            env.commands().forEach((k,v) -> env.writeln(v.getCommandName()));

            env.commands().forEach((s, shellCommand) -> System.out.println(s));
        }

        if(args.length == 1) {
            ShellCommand command;
            if ((command = env.commands().get(args[0])) != null) {
                command.getCommandDescription().forEach(env::writeln);
            } else {
                env.writeln("No command named " + arguments);
            }
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
