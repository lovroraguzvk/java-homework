package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class that defines the copy command
 */
public class CopyCommand implements ShellCommand {

    private static final String COMMAND_NAME = "copy";
    private static final List<String> DESCRIPTION = Arrays.stream("""
            Copies the source file to the destination file.
            First argument: the source file.
            Second argument: the destination file.
            """.split
            ("\n")).toList();

    /**
     * Executes the copy command over the specified file
     *
     * @param env the environment in which the command works
     * @param arguments takes two arguments, first is the source file, second is the destination file
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" +(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        if(args.length != 2) {
            env.writeln("Incorrect number of arguments.");
            return ShellStatus.CONTINUE;
        }
        for (int i = 0; i < args.length; i++) {
            if(args[i].charAt(0) == '"' && args[i].charAt(args[i].length() - 1) == '"') {
                args[i] = args[i].substring(1, args[i].length() - 1);
            }
        }

        File sourceFile = new File(args[0]);
        if (!sourceFile.exists()) {
            env.writeln("Source file doesn't exist.");
            return ShellStatus.CONTINUE;
        }

        File destinationFile = new File(args[1]);
        /*
        if (destinationFile.exists()) {
            env.write("Destination file already exists. Do you want to overwrite it? ([Y]/n) ");
            if(Objects.equals(env.readLine(), "n")) return ShellStatus.TERMINATE;
        } else {
            try {
                System.out.println(destinationFile.createNewFile());
            } catch (IOException e) {
                env.writeln("Couldn't create destination file.");
                return ShellStatus.TERMINATE;
            }
        }

         */

        try {
            Files.copy(sourceFile.toPath(), destinationFile.toPath());
        } catch (FileAlreadyExistsException e) {
            env.write("Destination file already exists. Do you want to overwrite it? ([Y]/n) ");
            if(Objects.equals(env.readLine(), "n")) {
                env.writeln("Terminating action.");
                return ShellStatus.CONTINUE;
            }
            try {
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                env.writeln("Copying failed.");
                return ShellStatus.CONTINUE;
            }
        } catch (IOException e){
            env.writeln("Copying failed.");
            return ShellStatus.CONTINUE;
        }
        env.writeln("Successfully copied.");

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
