package rogue;

public class Clothing extends Item implements Wearable {
    /**
     * Default constructor.
     */
    public Clothing() {
        super();
    }
    /**
     * Constructor given an id number.
     * @param id
     */
    public Clothing(int id) {
        super(id);
    }
    /**
     * Default wear method.
     * @return (String) message indicating the item has been worn
     */
    public String wear() {
        return "You wore " + getName() + " ...";
    }
}
