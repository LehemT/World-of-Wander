/**
 * This subclass creates a special room that transports the player into a
 * random room upon exiting, adding an element of surprise to the game.
 *
 * @author Lehem Temesgen
 * @version 03/14/2024
 */

import java.util.ArrayList;
import java.util.Random;

public class TransporterRoom extends Room {
    private Random randRoom;
    
    /**
     * Constructs a transporter room.
     * 
     * @param description The description of the transporter room.
     */
    public TransporterRoom(String description) {
        super(description);
        randRoom = new Random();
    }

    /**
    * Returns a random room, independent of the direction parameter.
    *
    * @param direction to be ignored.
    * @return A randomly selected room.
    */
    public Room getExit(String direction) {
        ArrayList<Room> allRooms = Room.getAllRooms();
        int randomIndex = randRoom.nextInt(allRooms.size());
        return allRooms.get(randomIndex);
    }

}
