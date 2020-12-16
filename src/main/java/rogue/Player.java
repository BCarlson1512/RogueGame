package rogue;
import java.awt.Point;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * The player character.
 */
public class Player implements Serializable {

    private String name;
    private Point xyLoc;
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private static final long serialVersionUID = -859269374416101014L;
    /**
     * Default constructor.
     */
    public Player() {
        Point defaultXY = new Point(1, 1);
        setXyLocation(defaultXY);
        this.name = "default";
        inventory = new ArrayList<>();
    }

    /**
     * overloaded constructor for our player.
     * @param n (String) our player's name
     */
    public Player(String n) {
        Point defaultXY = new Point(1, 1);
        setName(n);
        setXyLocation(defaultXY);
        this.xyLoc = defaultXY;
        inventory = new ArrayList<>();
    }

    /**
     * gets the player's inventory.
     * @return (ArrayList) inventory of the player
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    /**
     * Sets the inventory of the player.
     * @param newInventory (ArrayList) the inventory of the player
     */
    public void setInventory(ArrayList<Item> newInventory) {
        this.inventory = newInventory;
    }

    /**
     * Adds a new non-null item to the player's inventory.
     * @param newItem (Item) the item being added to the player's inventory
     */
    public void addItem(Item newItem) {
        if (newItem != null) {
            inventory.add(newItem);
        }
    }

    /**
     * get the name of our player.
     * @return (String) player's name
     */
    public String getName() {
        return name;
    }

    /**
     * set the name of our player.
     * @param newName (String) our player's name
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * get the (x,y) coordinates of our player.
     * @return (x,y) location of our player
     */
    public Point getXyLocation() {
        return xyLoc;
    }

    /**
     * set a new (x,y) location for our player.
     * @param newXyLocation is the new (x,y) coordinates for player
     */
    public void setXyLocation(Point newXyLocation) {
        xyLoc = newXyLocation;
    }

    /**
     * get the current room of our player.
     * @return (Room) current room our player is in
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * set the current room of our player.
     * @param newRoom (Room) is our current room
     */
    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }
    /**
     * This method generates a string representation of the players inv.
     * @return (String) string representation of the inventory
     */
    public ArrayList<String> displayInventory() {
        ArrayList<String> inventoryDisplay = new ArrayList<>();
        int i  = 0;
        inventoryDisplay.add("| Inventory Slot | Name | Type | \n");
        for (Item it: inventory) {
            inventoryDisplay.add("| " + i + " | " + formatString(it));
            i++;
        }
        return inventoryDisplay;
    }

    private String formatString(Item i) {
        String inv  = " ";
        inv = i.getName() + " | " + i.getType() + " |\n";
        return inv;
    }
}
