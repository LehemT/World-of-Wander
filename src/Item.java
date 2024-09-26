/**
 * This class represents an item that can be placed in any of the rooms within the game. Each item has a
 * description, weight, and a name for identification. Players can interact with these items while exploring rooms.
 * 
 * @author Lehem Temesgen
 * @version 03/14/2024
 */
public class Item
{
    // description of the item
    private String description;
    
    // weight of the item in kilgrams 
    private double weight;
    
    // shorter name for an item
    private String name;

    /**
     * Constructor for objects of class Item.
     * 
     * @param description The description of the item
     * @param weight The weight of the item
     * @param name The name of the item
     */
    public Item(String description, double weight, String name)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
    }
       
    /**
     * Returns a description of the item, including its
     * name, description and weight.
     * 
     * @return  A description of the item
     */
    public String getDescription()
    {
        return name + ": " + description + " that weighs " + weight + "kg.";
    }
    
    /**
     * Gets the name of the item.
     * 
     * @return The name of the item
     */
    public String getName() {
        return name;
    }

}
