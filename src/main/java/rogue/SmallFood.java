package rogue;

public class SmallFood extends Food implements Tossable {
    /**
     * default constructor.
     */
    public SmallFood() {
        super();
    }
    /**
     * Constructor given the id.
     * @param id (int) the item's id
     */
    public SmallFood(int id) {
        super(id);
    }
    /**
     * Method for tossable items.
     * @return (String) message indicating the item has been tossed
     */
    public String toss() {
        return "You tossed " + getName() + "...\n";
    }
}
