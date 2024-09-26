/**
 * This class represents a room in the game. Each room has a description, exits leading to neighboring rooms,
 * and the items that can be found in it. It allows the player to move and interaction with the environment.
 *
 * @author Lehem Temesgen
 * @version 03/14/2024
 */

import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.

    // the items in this room
    private ArrayList<Item> items;
    
    // static ArrayList of Rooms
    protected static ArrayList<Room> allRooms = new ArrayList<Room>();
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open courtyard".
     * 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        allRooms.add(this); // add the room to the list of rooms
    }
    
    /**
     * Add an item to the room, best to check that it's not null.
     * 
     * @param item The item to add to the room
     */
    public void addItem(Item item) 
    {
        if (item!=null) { // not required, but good practice
            items.add(item);
        }
    }
     
    /**
     * Removes an item from the room.
     *
     * @param item The item to remove from the room
     * @return true if the item was successfully removed, false otherwise
     */
    public void removeItem(Item item) {
        if (item!=null){    
            items.remove(item);
        }        
    }
    
    /**
     * Returns the ArrayList of all rooms.
     *
     * @return The ArrayList of all rooms
     */
    public static ArrayList<Room> getAllRooms() {
        return allRooms;
    }
    
    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit
     * @param neighbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbour) 
    {
        exits.put(direction, neighbour);
    }

    /**
     * Makes sure the item is in the room.
     * 
     * @param itemSearched the name of the item to be searched
     * @return the item if it's in the room, null otherwise
     */
    public Item itemInRoom(String itemSearched){
        for(Item item: items){
            if(item.getName().equalsIgnoreCase(itemSearched)){
                return item;
            }
        }
        return null;
    }
    
    /**
     * Returns a short description of the room, i.e. the one that
     * was defined in the constructor
     * 
     * @return The short description of the room
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items: 
     *        a chair weighing 5 kgs.
     *        a table weighing 10 kgs.
     *     
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString()
            + "\nItems:" + getItems();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction
     * @return The room in the given direction
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Return a String representing the items in the room, one per line.
     * 
     * @return A String of the items, one per line
     */
    public String getItems() 
    {
        // let's use a StringBuilder (not required)
        StringBuilder s = new StringBuilder();
        for (Item i : items) {
            s.append("\n    " + i.getDescription());
        }
        return s.toString(); 
    }
    
}

