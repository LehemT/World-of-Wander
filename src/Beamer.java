
/**
 * This class represents a specialized item in the game that can be charged and used
 * to teleport the player to the room where it was initially charged. The beamer can
 * be charged once per use and must be fired to initiate the teleportation process.
 *
 * @author Lehem Temesgen
 * @version 03/14/2024
 */
public class Beamer extends Item {
    private boolean charged;
    
    /**
     * Create a new Beamer item.
     *
     * @param description The description of the beamer.
     * @param weight The weight of the beamer.
     * @param name The name of the beamer
     */
    public Beamer(String description, double weight, String name) {
        super(description, weight, name);
        charged = false;
    }
    
    /**
     * Charges the beamer.
     *
     * @return True if the beamer is successfully charged, false otherwise.
     */
    public boolean charge() {
        if (!charged) {
            charged = true;
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Checks if the Beamer is charged.
     *
     * @return True if the Beamer is charged, false otherwise.
     */
    public boolean isCharged() {
        return charged;
    } 
    
    /**
     * Fires the beamer.
     * 
     * @return true if the beamer is fired, false if its not charged.
     */
    public boolean fire() {
        if (charged) {
            charged = false; // Reset charged status after firing
            return true; // succesfully fired and teleported
        } else {
            return false; // not charged
        }
    }
}
