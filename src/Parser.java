/**
 * This class is responsible for interpreting user commands as an 'Adventure' command.
 * It reads user input, breaks it down into a two word command, and returns
 * a Command object if the input is not one of the known commands.
 *
 * @author Lehem Temesgen
 * @version 03/14/2024
 */

import java.util.Scanner;

public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * Get the command from the user.
     * 
     * @return The next command from the user
     */
    public Command getCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                // note: we just ignore the rest of the input line.
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if(commands.isCommand(word1)) {
            return new Command(word1, word2);
        }
        else {
            return new Command(null, word2); 
        }
    }

    /**
     * Get a list of valid command words.
     * 
     * @return A String of the valid commands
     */
    public String getCommands()
    {
        return commands.getCommandList();
    }
}
