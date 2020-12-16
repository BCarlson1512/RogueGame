package rogue;

public class Food extends Item implements Edible {
    /**
     * Default constructor.
     */
    public Food() {
        super();
    }
    /**
     * Constructor given item id number.
     * @param id (int) the item's id
     */
    public Food(int id) {
        super(id);
    }
    /**
     * Basic eat method.
     * @return (String) message indicating the item was eaten
     */
    public String eat() {
        return "you ate " + getName() + " ...\n";
    }
}
