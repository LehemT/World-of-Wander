/**
 * This class holds a set of valid command words that the player can use
 * in the game. It is used to recognise commands as they are typed in.
 * 
 * @author Lehem Temesgen
 * @version 03/14/2024
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "eat", "back", "stackBack", "take", "drop", "charge", "fire"
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords() {

    }

    /**
     * Check whether a given String is a valid command word. 
     * 
     * @param aString The String to check
     * @return true if it is valid, false otherwise
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Returns a String containing all valid commands.
     * 
     * @return a String of the valid commands
     */
    public String getCommandList() 
    {
        // let's use a StringBuilder (not required)
        StringBuilder s = new StringBuilder();
        for(String command: validCommands) {
            s.append(command + "  ");
        }
        String str = s.toString(); 
        return str.trim(); // removes spaces from beginning/end
    }
}
