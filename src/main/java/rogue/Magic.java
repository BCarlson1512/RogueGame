package rogue;

public class Magic extends Item {
    /**
     * Default constructor.
     */
    public Magic() {
        super();
        setType("Magic");
    }
    /**
     * Constructor for magic item, with id.
     * @param id
     */
    public Magic(int id) {
        super(id);
        setType("Magic");
    }
}
