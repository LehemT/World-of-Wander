import java.util.Stack;
/**
 *  "World of Wander" is a fun text-based adventure game where users can explore different
 *  rooms and interact with the environment.
 *
 *  This class serves as the entry point for the "World of Wander" application.
 *  It initializes the game, sets up the rooms and items, and manages the main
 *  game loop allowing players to explore and interact with the environment.
 *  
 * @author Lehem Temesgen
 * @version 03/14/2024
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> previousRoomStack;
    // store the item the user is interacting with
    private Item playerItem;
    // track the user's number of pickups
    private int numPickup;
    // track the room to teleport to
    private Room destinationRoom;    
    
    /**
     * Create the game and initialise its internal map, as well
     * as the previous room and previous room stack.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRoom = null;
        previousRoomStack = new Stack<Room>();
        playerItem = null;
        numPickup = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
        Item chair1, chair2, chair3, chair4, bar, computer1, 
        computer2, computer3, tree1, tree2, cookie1, cookie2, 
        cookie3, cookie4, cookie5;
        Beamer beamer1, beamer2;
        TransporterRoom transporterRoom;
        
        // create some items
        chair1 = new Item("a wooden chair",5.0,"Chair");
        chair2 = new Item("a wooden chair",5.0,"Chair");
        chair3 = new Item("a wooden chair",5.0,"Chair");
        chair4 = new Item("a wooden chair",5.0,"Chair");
        bar = new Item("a long bar with stools",95.67,"Bar");
        computer1 = new Item("a PC",10.0,"Computer");
        computer2 = new Item("a Mac",5.0,"Computer");
        computer3 = new Item("a PC",10.0,"Computer");
        tree1 = new Item("a fir tree",500.5,"Tree");
        tree2 = new Item("a fir tree",500.5,"Tree");
        cookie1 = new Item("a cookie",0.1,"Cookie");
        cookie2 = new Item("a cookie",0.1,"Cookie");
        cookie3 = new Item("a cookie",0.1,"Cookie");
        cookie4 = new Item("a cookie",0.1,"Cookie");
        cookie5 = new Item("a cookie",0.1,"Cookie");
        beamer1 = new Beamer("a beamer",1.0,"Beamer");
        beamer2 = new Beamer("a beamer",1.0,"Beamer");
       
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // Create transporter room
        transporterRoom = new TransporterRoom("in a mysterious transporter room");
    
        // put items in the rooms
        outside.addItem(tree1);
        outside.addItem(tree2);
        outside.addItem(cookie1); // add first cookie
        theatre.addItem(chair1);
        theatre.addItem(cookie2); // add second cookie
        theatre.addItem(beamer1); // add first beamer
        pub.addItem(bar);
        pub.addItem(cookie3); // add third cookie
        pub.addItem(beamer2); // add second beamer
        lab.addItem(chair2);
        lab.addItem(computer1);
        lab.addItem(chair3);
        lab.addItem(computer2);
        lab.addItem(cookie4); // add fourth cookie
        office.addItem(chair4);
        office.addItem(computer3);
        office.addItem(cookie5); // add fifth cookie 
        
        // initialise room exits
        outside.setExit("east", theatre); 
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);
        theatre.setExit("east", transporterRoom);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }
    
    /**
     *  Main play routine. Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            // Print the room desFcription whenever the player enters a room or uses the "look" command
            if (command.getCommandWord().equals("go") || command.getCommandWord().equals("look")) {
                roomDescription();
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    
    /**
     * Prints the description of a room including the items inside and the items being carried.
     */
    private void roomDescription(){
        System.out.println(currentRoom.getLongDescription());
        
        if (playerItem!=null){
            System.out.println("You are carrying: " + playerItem.getDescription());
        } else {
            System.out.println("You are not carrying anything.");
        }
    }
    
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Wander!");
        System.out.println("World of Wander is an adventure game where you can explore rooms and interact with the environment!.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        roomDescription();
    }

    /**
     * Given a command, processes the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("stackBack")) {
            stackBack(command);
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("charge")) {
            charge(command);
        }
        else if (commandWord.equals("fire")) {
            fire(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }
    
    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * If we go to a new room, update previous room and previous room stack.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
        } else {
            String direction = command.getSecondWord();
        
            if (currentRoom instanceof TransporterRoom) {
            // Get the next room randomly from the transporter room
            currentRoom = currentRoom.getExit(direction);
            } else {
                // Try to leave current room.
                Room nextRoom = currentRoom.getExit(direction);
                if (nextRoom == null) {
                    System.out.println("There is no door!");
                }
                else {
                    previousRoom = currentRoom; // store the previous room
                    previousRoomStack.push(currentRoom); // and add to previous room stack
                    currentRoom = nextRoom;
                }
            }
            roomDescription();            
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * "Look" was entered. Check the rest of the command to see
     * whether we really want to look.
     * 
     * @param command The command to be processed
     */
    private void look(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Look what?");
        }
        else {
            // output the long description of this room
            roomDescription();
        }
    }
    
    /** 
     * "Eat" was entered. Check the rest of the command to see
     * whether we really want to eat.
     * 
     * @param command The command to be processed
     */
    private void eat(Command command) 
    {        
        if(command.hasSecondWord()) {
            System.out.println("Eat what?");
        } else {
            String itemName = command.getSecondWord();
            if (playerItem == null) {
                System.out.println("You are not holding anything to eat.");
            } else if (playerItem.getName().equalsIgnoreCase("cookie")) {
                System.out.println("You have eaten the cookie and are no longer hungry.");
                playerItem = null; // Remove the eaten cookie from the player's inventory
                numPickup += 5;
            } else {
                System.out.println("You have no cookie to eat.");
            }
        }     
    }
    
    /** 
     * "Back" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     */
    private void back(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
        }
        else {
            // go back to the previous room, if possible
            if (previousRoom==null) {
                System.out.println("No room to go back to.");
            } else {
                // go back and swap previous and current rooms,
                // and put current room on previous room stack
                Room temp = currentRoom;
                currentRoom = previousRoom;
                previousRoom = temp;
                previousRoomStack.push(temp);
                // and print description
                roomDescription();
            }
        }
    }
    
    /** 
     * "StackBack" was entered. Check the rest of the command to see
     * whether we really want to stackBack.
     * 
     * @param command The command to be processed
     */
    private void stackBack(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("StackBack what?");
        }
        else {
            // step back one room in our stack of rooms history, if possible
            if (previousRoomStack.isEmpty()) {
                System.out.println("No room to go stack back to.");
            } else {
                // current room becomes previous room, and
                // current room is taken from the top of the stack
                previousRoom = currentRoom;
                currentRoom = previousRoomStack.pop();
                // and print description
                roomDescription();
            }
        }
    }
    
    /**
     * "Take" was entered. Check the rest of the command to see
     * whether we really want to pick up an item.
     * 
     * @param command The command to be processed
     */
    private void take(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
        } else {
            String itemName = command.getSecondWord();
            Item itemToTake = currentRoom.itemInRoom(itemName);
            if(itemName.equalsIgnoreCase("Cookie")){
                if(itemToTake != null){
                    System.out.println("You picked up a cookie.");
                    playerItem = itemToTake;
                    currentRoom.removeItem(itemToTake);
                } else {
                    System.out.println("There is no cookie in the room.");
                }
            } else if (numPickup > 0){
                if(itemToTake != null){
                    playerItem = itemToTake;
                    currentRoom.removeItem(itemToTake);
                    System.out.println("You picked up a " + itemName);
                    numPickup--;
                } else {
                    System.out.println("That item is not in the room.");
                }
            } else {
                System.out.println("You can't pick up items until you eat a cookie.");
            }
        }
    }
    
    /**
     * "Drop" was entered. Check the rest of the command to see
     * whether we really want to drop an item.
     *
     * @param command The command to be processed
     */
    private void drop(Command command) {
        if(playerItem != null){
            currentRoom.addItem(playerItem);
            System.out.println("You have dropped " + playerItem.getName() + ".");
            playerItem = null;
        } else {
            System.out.println("You have nothing to drop.");
        }
    }
    
    /**
     * "Charge" was entered. Check the rest of the command to see
     * whether we are carrying a beamer.
     *
     * @param command The command to be processed
     */
    private void charge(Command command) {
        if (playerItem instanceof Beamer) {
            Beamer beamer = (Beamer) playerItem;
            if (beamer.charge()){
                System.out.println("Beamer is now charged.");
                destinationRoom = currentRoom;
            } else {
                System.out.println("Beamer is already charged");
            }
        } else {
            System.out.println("You are not carrying a Beamer.");
        }
    }
    
    /**
     * "Fire" was entered. Check the rest of the command to see
     * whether we really want to drop an item.
     *
     * @param command The command to be processed
     */
    private void fire(Command command) {
        if (playerItem instanceof Beamer) {
            Beamer beamer = (Beamer) playerItem;
            if (beamer.isCharged()) { // Check if Beamer is charged before firing
                if (beamer.fire()) {
                    previousRoom = currentRoom; // Store the current room as previous room
                    previousRoomStack.push(currentRoom); // Push current room to stack
                    currentRoom = destinationRoom; // Teleport to the destination room
                    roomDescription();
                }
            } else {
                System.out.println("Beamer is not charged!");
            }
        } else {
            System.out.println("You must be carrying a beamer to fire it.");
        }
    }
}