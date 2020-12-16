package rogue;

public class Potion extends Magic implements Edible, Tossable {
    /**
     * Default constructor.
     */
    public Potion() {
        super();
    }
    /**
     * Constructor for potion given item id.
     * @param id (int) the item's id
     */
    public Potion(int id) {
        super(id);
    }
    /**
     * toss method.
     * @return (String) message indicating the item was tossed
     */
    public String toss() {
        return "You tossed the " + getName() + " ...";
    }
    /**
     * eat method.
     * @return (String) message indicating the item was eaten
     */
    public String eat() {
        return "You drank the " + getName() + " and feel better...";
    }
}
