package hr.fer.oprpp1.hw05.shell;
;
import hr.fer.oprpp1.hw05.shell.commands.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class MyShell {

    public static void main(String[] args) {
        SortedMap<String, ShellCommand> commands = new TreeMap<>();

        commands.put("exit", new ExitCommand());
        commands.put("cat", new CatCommand());
        commands.put("charsets", new CharsetsCommand());
        commands.put("copy", new CopyCommand());
        commands.put("hexdump", new HexdumpCommand());
        commands.put("ls", new ListFilesCommand());
        commands.put("mkdir", new MkdirCommand());
        commands.put("symbol", new SymbolChangeCommand());
        commands.put("tree", new TreeCommand());
        commands.put("help", new HelpCommand());
        commands.put("date", new DateCommand());

        Environment myShell = new MyShellEnvironment(commands);
        try {
            String input = myShell.readLine().trim();

            while (true) {
                String[] inputSplit = input.split(" +", 2);
                // System.out.println(Arrays.toString(inputSplit));

                if (inputSplit.length == 0) {
                    myShell.writeln("Input can't be empty;");
                }

                String commandName = inputSplit[0];
                String arguments = "";

                if (inputSplit.length == 2) {
                    arguments = inputSplit[1];
                }

                try{
                    ShellCommand command;
                    if ((command = myShell.commands().get(commandName)) != null) {
                        if (command.executeCommand(myShell, arguments) == ShellStatus.TERMINATE) {
                            myShell.writeln("Goodbye :)");
                            break;
                        }
                    } else {
                        myShell.writeln(String.format("Command '%s' not found.", commandName));
                    }
                } catch (Exception e) {
                    System.out.println("Please specify the directory");
                }

                input = myShell.readLine().trim();

            }
        } catch (ShellIOException e) {
            myShell.commands().get("exit").executeCommand(myShell, "");
        }
    }
}
