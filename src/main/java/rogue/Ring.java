package rogue;

public class Ring extends Item implements Wearable, Tossable {
    /**
     * Default constructor.
     */
    public Ring() {
        super();
    }
    /**
     * Constructor given the item id number.
     * @param id (int) the id of the item
     */
    public Ring(int id) {
        super(id);
    }
    /**
     * toss method.
     * @return (String) message indicating the item was tossed
     */
    public String toss() {
        return "You tossed the ring...";
    }
    /**
     * wear method.
     * @return (String) message indicating that the item was worn
     */
    public String wear() {
        return "You put on the ring...";
    }
}
