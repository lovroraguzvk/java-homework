package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Class that defines the ls command
 */
public class ListFilesCommand implements ShellCommand {
    private static final String COMMAND_NAME = "ls";
    private static final List<String> DESCRIPTION = Arrays.stream("""
            Prints all files in the specified directory.
            Also prints basic information about the files.
            First argument: the directory.
            """.split("\n")).toList();

    private static final String STRING_FORMAT = "%s %10d %s %s";

    /**
     * Executes the list files command over the specified directory
     *
     * @param env the environment in which the command works
     * @param arguments takes one argument, and that is the directory whose files it will list
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        File[] files = (new File(args[0])).listFiles();
        if (files == null) {
            env.writeln("Filename cant be empty");
            return ShellStatus.CONTINUE;
        }

        for(File file : files) {
            BasicFileAttributeView faView = Files.getFileAttributeView(
                    file.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
            );
            BasicFileAttributes attributes;
            try {
                attributes = faView.readAttributes();
            } catch (IOException e) {
                env.writeln("Couldn't read file " + file.getName());
                return ShellStatus.CONTINUE;
            }
            FileTime fileTime = attributes.creationTime();
            String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

            String attrs = file.isDirectory() ? "d" : "-";
            attrs = Files.isReadable(file.toPath()) ? attrs.concat("r") : attrs.concat("-");
            attrs = Files.isWritable(file.toPath()) ? attrs.concat("w") : attrs.concat("-");
            attrs = Files.isExecutable(file.toPath()) ? attrs.concat("x") : attrs.concat("-");


            try {
                env.writeln(String.format(STRING_FORMAT, attrs, Files.size(file.toPath()), formattedDateTime, file.getName()));
            } catch (IOException e) {
                env.writeln("Couldn't read '" + file.getName() + "'.");
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

    public static void main(String[] args) {
        System.out.println((Objects.requireNonNull(new File(".").listFiles())[1].getName()));
    }
}
