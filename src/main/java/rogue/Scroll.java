package rogue;

public class Scroll extends Magic {
    /**
     * Default constructor.
     */
    public Scroll() {
        super();
        setType("Scroll");
    }
    /**
     * Basic constructor using id.
     * @param id (INT) the id number of the scroll
     */
    public Scroll(int id) {
        super(id);
        setType("Scroll");
    }
}
