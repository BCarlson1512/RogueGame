package rogue;
import java.awt.Point;
import java.io.Serializable;
/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item implements Serializable {

    private int identificationNum;
    private String name;
    private String type;
    private String desc;
    private Point xyLocation;
    private Character displayChar;
    private Room curRoom;
    private static final long serialVersionUID = -3930938124504252527L;
    /**
     * Default constructor for item.
     */
    public Item() {
        this.identificationNum = -1;
    }

    /**
     * Constructor for a new item.
     * @param id the id of the item
     */
    public Item(int id) {
        setId(id);
    }

    /**
     * Return the identificationNum of our item.
     * @return integer identificationNum
     */
    public int getId() {
        return identificationNum;
    }

    /**
     * set the id of our item.
     * @param id (int) new id of the item
     */
    public void setId(int id) {
        identificationNum = id;
    }

    /**
     * get the name of our item.
     * @return (String) the name of our item
     */
    public String getName() {
        return name;
    }

    /**
     * set the name of our item.
     * @param n (String) the name of our item
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * get the type of our item.
     * @return (String) our item's type
     */
    public String getType() {
        return type;
    }

    /**
     * set a type for our item between the numbers 1 and 3.
     * @param t (String) determines what kind of item this is
     */
    public void setType(String t) {
        this.type = t;
    }
    /**
     * Return our display character for items.
     * @return (Character) character that will be displayed for items
     */
    public Character getDisplayCharacter() {
        return displayChar;
    }

    /**
     * set a new character for our item's display.
     * @param newDisplayCharacter (Character) the character that will show up for items when the room is printed
     */
    public void setDisplayCharacter(Character newDisplayCharacter) {
        displayChar = newDisplayCharacter;
    }

    /**
     * get our items description.
     * @return (String) description of our item
     */
    public String getDescription() {
        return desc;
    }

    /**
     * Set the descirption of our item.
     * @param newDescription (String) is the item's description
     */
    public void setDescription(String newDescription) {
        desc = newDescription;
    }

    /**
     * get the x,y coordinates of our item.
     * @return a point (x,y) location of our item
     */
    public Point getXyLocation() {
        return xyLocation;
    }

    /**
     * set a new point location for our item.
     * @param newXyLocation (x,y) coordinates for our items
     */
    public void setXyLocation(Point newXyLocation) {
        xyLocation = newXyLocation;
    }

    /**
     * get the room the item belongs to.
     * @return Room object of our current room
     */
    public Room getCurrentRoom() {
        return curRoom;
    }

    /**
     * set the new room our items belong to.
     * @param newCurrentRoom is our new current room
     */
    public void setCurrentRoom(Room newCurrentRoom) {
        this.curRoom = newCurrentRoom;
    }
}
