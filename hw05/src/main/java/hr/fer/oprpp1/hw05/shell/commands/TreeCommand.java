package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Class that defines the tree command
 */
public class TreeCommand implements ShellCommand {

    private static final String COMMAND_NAME = "tree";
    private static final List<String> DESCRIPTION = Arrays.stream("""
            Prints all files and folders recursively in the specified directory.
            First argument: the directory.
            """.split("\n")).toList();

    /**
     * Executes the tree command over the specified directory
     *
     * @param env the environment in which the command works
     * @param arguments takes one argument, and that is the directory
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

        try {
            Files.walkFileTree(Path.of(arguments), new FileVisitor<Path>(){
                int depth = 0;
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if(dir.equals(Path.of(arguments))) return FileVisitResult.CONTINUE;
                    writeAtDepth(dir.toString());
                    depth++;
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    writeAtDepth(file.toFile().getName());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return null;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    depth--;
                    return FileVisitResult.CONTINUE;
                }

                private void writeAtDepth(String str) {
                    env.writeln("  ".repeat(depth) + str);
                }
            });
        } catch (IOException e) {
            env.writeln("Unable to read directory.");
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
