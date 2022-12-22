package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

/**
 * Class that defines the hexdump command
 */
public class HexdumpCommand implements ShellCommand {

    private static final String COMMAND_NAME = "hexdump";
    private static final List<String> DESCRIPTION = Arrays.stream("""
            Outputs a hexdump of a file.
            Each line contains 10 bytes of data.
            First argument: name of file.
            """.split("\n")).toList();

    /**
     * Executes the hexdump command over the specified file
     *
     * @param env the environment in which the command works
     * @param arguments takes one argument, and that is the source file
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
        if(args.length != 1) {
            env.writeln("Incorrect number of arguments.");
            return ShellStatus.CONTINUE;
        }

        File file = new File(args[0]);

        if (!file.exists()) {
            env.writeln("File doesn't exist.");
            return ShellStatus.CONTINUE;
        }

        try (FileReader reader = new FileReader(file)){
            int counter = 0;
            StringBuilder builder = new StringBuilder();

            int r;
            while (true) {
                /*
                do {
                    r = reader.read();
                    System.out.printf("{%c, %d}", (char)r, r);
                } while(((char) r) == '\n');


                 */
                r = reader.read();
                if (r == -1) break;

                if (counter % 16 == 0) {
                    if(counter != 0){
                        env.write(" | ");
                        env.writeln(builder.toString());
                        builder = new StringBuilder();
                    }
                    env.write(String.format("%08x", counter));
                    env.write(":");
                }

                if(counter % 16 == 8) env.write("|");
                else env.write(" ");

                if (r >= 32 && r <= 127) {
                    builder.append((char) r);
                } else {
                    builder.append('.');
                }

                env.write(String.format("%02x" , r).toUpperCase());

                counter++;
            }

            if (counter != 0){
                if (builder.length() >= 8) {
                    env.write("   ".repeat(16 - builder.length()));
                    env.write(" | ");
                } else {
                    env.write("   ".repeat(8 - builder.length()));
                    env.write("|");
                    env.write("   ".repeat(8));
                    env.write("| ");
                }

                env.writeln(builder.toString());
            }

        } catch (Exception e) {
            env.writeln("Couldn't read file.");
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
