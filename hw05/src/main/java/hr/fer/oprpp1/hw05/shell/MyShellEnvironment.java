package hr.fer.oprpp1.hw05.shell;

import hr.fer.oprpp1.hw05.shell.commands.ShellCommand;

import java.util.Scanner;
import java.util.SortedMap;

/**
 * Shell implementation using the Java "System.out" environment
 */
public class MyShellEnvironment implements Environment {
    Scanner sc = new Scanner(System.in);
    private Character morelinesSymbol = '\\';
    private Character promptSymbol = '>';
    private Character multilineSymbol = '|';
    private final SortedMap<String, ShellCommand> commands;

    public MyShellEnvironment(SortedMap<String, ShellCommand> commands) {
        System.out.println("Welcome to MyShell v 1.0");

        this.commands = commands;
    }

    /**
     * Reads a line in the environment
     *
     * @return line that is read
     * @throws ShellIOException if problem with reading
     */
    @Override
    public String readLine() throws ShellIOException {
        System.out.printf("%c ", promptSymbol);

        String line = sc.nextLine().trim();

        while(line.endsWith(morelinesSymbol.toString())) {
            System.out.printf("%c ", multilineSymbol);
            line = line.substring(0, line.length() - 1).concat(sc.nextLine().trim());
        }

        return line;
    }

    /**
     * Writes a line to the environment without a newline
     *
     * @param text text to be written to the environment
     * @throws ShellIOException if problem with reading
     */
    @Override
    public void write(String text) throws ShellIOException {
        System.out.print(text);
    }

    /**
     * Writes a line to the environment with a newline
     *
     * @param text text to be written to the environment
     * @throws ShellIOException if problem with reading
     */
    @Override
    public void writeln(String text) throws ShellIOException {
        System.out.println(text);
    }

    /**
     * @return all commands in the environment
     */
    @Override
    public SortedMap<String, ShellCommand> commands() {
        return commands;
    }

    @Override
    public Character getMorelinesSymbol() {
        return morelinesSymbol;
    }

    @Override
    public void setMorelinesSymbol(Character symbol) {
        morelinesSymbol = symbol;
    }

    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    @Override
    public void setPromptSymbol(Character symbol) {
        promptSymbol = symbol;
    }

    @Override
    public Character getMultilineSymbol() {
        return multilineSymbol;
    }

    @Override
    public void setMultilineSymbol(Character symbol) {
        multilineSymbol = symbol;
    }
}
