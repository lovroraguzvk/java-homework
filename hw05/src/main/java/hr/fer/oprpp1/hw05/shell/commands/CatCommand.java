package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Class that defines the cat command
 */
public class CatCommand implements ShellCommand{
    private static final String COMMAND_NAME = "cat";
    private static final List<String> DESCRIPTION = Arrays.stream("""
            Prints all contents of a specified file.
            First argument: name of file.
            Second argument (optional): charset encoding.
            """.split("\n")).toList();

    /**
     * Executes the cat command over the specified file
     *
     * @param env the environment in which the command works
     * @param arguments takes two arguments, first is the file, and the second is the encoded charset (optional)
     * @return ShellStatus
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" +(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        for (int i = 0; i < args.length; i++) {
            if(args[i].charAt(0) == '"' && args[i].charAt(args[i].length() - 1) == '"') {
                args[i] = args[i].substring(1, args[i].length() - 1);
            }
        }
        if(args.length != 1 && args.length != 2) {
            env.writeln("Incorrect number of arguments.");
            return ShellStatus.CONTINUE;
        }
        Charset ch = Charset.defaultCharset();
        try{
            if (args.length == 2) ch = Charset.forName(args[1]);
        }catch (IllegalArgumentException e) {
            env.writeln("Unsupported charset.");
            return ShellStatus.CONTINUE;
        }

        try (BufferedReader is = Files.newBufferedReader(Path.of(args[0]))) {
            String line;
            while ((line = is.readLine()) != null){
                env.writeln(line);
            }
        } catch (IOException e) {
            env.writeln("Couldn't read file.");
        } catch (InvalidPathException x) {
            env.writeln("Invalid path.");
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
