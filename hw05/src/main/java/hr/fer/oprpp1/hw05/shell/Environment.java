package hr.fer.oprpp1.hw05.shell;

import hr.fer.oprpp1.hw05.shell.commands.ShellCommand;

import java.util.SortedMap;

public interface Environment {

    /**
     * Reads a line in the environment
     *
     * @return line that is read
     * @throws ShellIOException if problem with reading
     */
    String readLine() throws ShellIOException;

    /**
     * Writes a text to the environment
     *
     * @param text text that is written to the environment
     * @throws ShellIOException
     */
    void write(String text) throws ShellIOException;
    void writeln(String text) throws ShellIOException;
    SortedMap<String, ShellCommand> commands();
    Character getMorelinesSymbol();
    void setMorelinesSymbol(Character symbol);
    Character getPromptSymbol();
    void setPromptSymbol(Character symbol);
    Character getMultilineSymbol();
    void setMultilineSymbol(Character symbol);
}
